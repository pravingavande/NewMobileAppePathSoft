package com.pathsoft.mobile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pathsoft.mobile.data.api.models.PatientView
import com.pathsoft.mobile.data.local.AuthTokenManager
import com.pathsoft.mobile.data.repository.PatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PatientListUiState(
    val patients: List<PatientView> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = "",
    val currentPage: Int = 1,
    val totalCount: Int = 0,
    val hasMore: Boolean = true,
    val labCode: String = ""
)

@HiltViewModel
class PatientListViewModel @Inject constructor(
    private val patientRepository: PatientRepository,
    private val authTokenManager: AuthTokenManager
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(PatientListUiState())
    val uiState: StateFlow<PatientListUiState> = _uiState.asStateFlow()
    
    init {
        loadPatients()
    }
    
    fun loadPatients(refresh: Boolean = false) {
        viewModelScope.launch {
            val currentState = _uiState.value
            val page = if (refresh) 1 else currentState.currentPage
            
            _uiState.value = currentState.copy(isLoading = true, errorMessage = null)
            
            val labCode = authTokenManager.getLabCode() ?: ""
            if (labCode.isEmpty()) {
                _uiState.value = currentState.copy(
                    isLoading = false,
                    errorMessage = "Lab code not found"
                )
                return@launch
            }
            
            patientRepository.getPatientsList(
                labCode = labCode,
                search = if (currentState.searchQuery.isNotBlank()) currentState.searchQuery else null,
                page = page,
                pageSize = 10
            ).onSuccess { response ->
                _uiState.value = _uiState.value.copy(
                    patients = if (refresh) response.data else currentState.patients + response.data,
                    isLoading = false,
                    currentPage = page,
                    totalCount = response.totalCount,
                    hasMore = response.data.size == 10,
                    labCode = labCode
                )
            }.onFailure { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = exception.message ?: "Failed to load patients"
                )
            }
        }
    }
    
    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        loadPatients(refresh = true)
    }
    
    fun loadMore() {
        if (_uiState.value.hasMore && !_uiState.value.isLoading) {
            _uiState.value = _uiState.value.copy(currentPage = _uiState.value.currentPage + 1)
            loadPatients()
        }
    }
}

