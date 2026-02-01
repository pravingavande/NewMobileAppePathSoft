package com.pathsoft.mobile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pathsoft.mobile.data.api.models.PackageResponse
import com.pathsoft.mobile.data.api.models.TestResponse
import com.pathsoft.mobile.data.repository.TestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddTestUiState(
    val isTestMode: Boolean = true, // true = Test, false = Package
    val tests: List<TestResponse> = emptyList(),
    val packages: List<PackageResponse> = emptyList(),
    val selectedTestIds: Set<Int> = emptySet(),
    val selectedPackageIds: Set<Int> = emptySet(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val existingTestIds: Set<Int> = emptySet() // Tests already added to visit
)

@HiltViewModel
class AddTestViewModel @Inject constructor(
    private val testRepository: TestRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AddTestUiState())
    val uiState: StateFlow<AddTestUiState> = _uiState.asStateFlow()
    
    fun loadTestsAndPackages(labCode: String, existingTestIds: Set<Int> = emptySet()) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null,
                existingTestIds = existingTestIds
            )
            
            // Load tests
            testRepository.getActiveTests(labCode).onSuccess { tests ->
                _uiState.value = _uiState.value.copy(tests = tests)
            }.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    errorMessage = exception.message ?: "Failed to load tests"
                )
            }
            
            // Load packages
            testRepository.getActivePackages(labCode).onSuccess { packages ->
                _uiState.value = _uiState.value.copy(packages = packages)
            }.onFailure { exception ->
                if (_uiState.value.errorMessage == null) {
                    _uiState.value = _uiState.value.copy(
                        errorMessage = exception.message ?: "Failed to load packages"
                    )
                }
            }
            
            _uiState.value = _uiState.value.copy(isLoading = false)
        }
    }
    
    fun toggleMode(isTestMode: Boolean) {
        _uiState.value = _uiState.value.copy(isTestMode = isTestMode)
    }
    
    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }
    
    fun toggleTestSelection(testId: Int) {
        val currentState = _uiState.value
        val newSelection = if (currentState.selectedTestIds.contains(testId)) {
            currentState.selectedTestIds - testId
        } else {
            currentState.selectedTestIds + testId
        }
        _uiState.value = currentState.copy(selectedTestIds = newSelection)
    }
    
    fun togglePackageSelection(packageId: Int) {
        val currentState = _uiState.value
        val newSelection = if (currentState.selectedPackageIds.contains(packageId)) {
            currentState.selectedPackageIds - packageId
        } else {
            currentState.selectedPackageIds + packageId
        }
        _uiState.value = currentState.copy(selectedPackageIds = newSelection)
    }
    
    fun clearSelection() {
        _uiState.value = _uiState.value.copy(
            selectedTestIds = emptySet(),
            selectedPackageIds = emptySet()
        )
    }
    
    fun getSelectedTestIds(): List<Int> {
        return _uiState.value.selectedTestIds.toList()
    }
    
    fun getSelectedPackageIds(): List<Int> {
        return _uiState.value.selectedPackageIds.toList()
    }
    
    fun getFilteredTests(): List<TestResponse> {
        val state = _uiState.value
        return if (state.searchQuery.isBlank()) {
            state.tests
        } else {
            state.tests.filter {
                it.sname.contains(state.searchQuery, ignoreCase = true)
            }
        }
    }
    
    fun getFilteredPackages(): List<PackageResponse> {
        val state = _uiState.value
        return if (state.searchQuery.isBlank()) {
            state.packages
        } else {
            state.packages.filter {
                it.packageName.contains(state.searchQuery, ignoreCase = true)
            }
        }
    }
}

