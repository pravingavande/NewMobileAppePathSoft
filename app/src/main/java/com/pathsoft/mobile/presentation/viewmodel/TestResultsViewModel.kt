package com.pathsoft.mobile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pathsoft.mobile.data.api.models.*
import com.pathsoft.mobile.data.repository.TestRepository
import com.pathsoft.mobile.data.repository.TestResultsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TestResultsUiState(
    val tests: List<TestWithParameters> = emptyList(),
    val parameterValues: Map<Int, String> = emptyMap(), // parameterID -> value
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val saveSuccess: Boolean = false
)

@HiltViewModel
class TestResultsViewModel @Inject constructor(
    private val testRepository: TestRepository,
    private val testResultsRepository: TestResultsRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(TestResultsUiState())
    val uiState: StateFlow<TestResultsUiState> = _uiState.asStateFlow()
    
    fun loadTestParameters(
        testIds: List<Int>,
        visitId: Int?,
        patientId: Int?,
        labCode: String
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            val sidList = testIds.joinToString(",")
            testRepository.getTestParameters(
                sidList = sidList,
                visitId = visitId,
                patientId = patientId,
                labCode = labCode
            ).onSuccess { response ->
                _uiState.value = _uiState.value.copy(
                    tests = response.tests,
                    isLoading = false
                )
            }.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = exception.message ?: "Failed to load test parameters"
                )
            }
        }
    }
    
    fun updateParameterValue(parameterId: Int, value: String) {
        val currentValues = _uiState.value.parameterValues.toMutableMap()
        currentValues[parameterId] = value
        _uiState.value = _uiState.value.copy(parameterValues = currentValues)
    }
    
    fun saveTestResults(
        labCode: String,
        patientID: Int,
        visitID: Int,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val state = _uiState.value
            _uiState.value = state.copy(isSaving = true, errorMessage = null)
            
            val testResults = state.tests.map { test ->
                TestResultSave(
                    sid = test.sid,
                    parameters = test.parameters.map { param ->
                        TestResultParameterSave(
                            parameterID = param.parameterID,
                            value = state.parameterValues[param.parameterID] ?: ""
                        )
                    }
                )
            }
            
            val request = SaveTestResultsRequest(
                labCode = labCode,
                patientID = patientID,
                visitID = visitID,
                tests = testResults
            )
            
            testResultsRepository.saveTestResults(request)
                .onSuccess {
                    _uiState.value = state.copy(
                        isSaving = false,
                        saveSuccess = true
                    )
                    onSuccess()
                }
                .onFailure { exception ->
                    _uiState.value = state.copy(
                        isSaving = false,
                        errorMessage = exception.message ?: "Failed to save test results"
                    )
                }
        }
    }
    
    fun isParameterAbnormal(parameter: TestParameter, value: String): Boolean {
        val numValue = value.toDoubleOrNull() ?: return false
        val min = parameter.normalRangeMin ?: return false
        val max = parameter.normalRangeMax ?: return false
        return numValue < min || numValue > max
    }
}

