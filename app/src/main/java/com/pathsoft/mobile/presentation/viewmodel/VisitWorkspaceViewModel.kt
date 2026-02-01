package com.pathsoft.mobile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pathsoft.mobile.data.api.models.VisitWorkspaceData
import com.pathsoft.mobile.data.local.AuthTokenManager
import com.pathsoft.mobile.data.repository.VisitWorkspaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class VisitWorkspaceUiState(
    val visitWorkspace: VisitWorkspaceData? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showAddTestModal: Boolean = false,
    val showDiscountModal: Boolean = false
)

@HiltViewModel
class VisitWorkspaceViewModel @Inject constructor(
    private val visitWorkspaceRepository: VisitWorkspaceRepository,
    private val authTokenManager: AuthTokenManager
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(VisitWorkspaceUiState())
    val uiState: StateFlow<VisitWorkspaceUiState> = _uiState.asStateFlow()
    
    fun loadVisitWorkspace(visitId: Int, labCode: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            
            visitWorkspaceRepository.getVisitWorkspace(visitId, labCode)
                .onSuccess { data ->
                    _uiState.value = _uiState.value.copy(
                        visitWorkspace = data,
                        isLoading = false
                    )
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Failed to load visit workspace"
                    )
                }
        }
    }
    
    fun showAddTestModal() {
        _uiState.value = _uiState.value.copy(showAddTestModal = true)
    }
    
    fun hideAddTestModal() {
        _uiState.value = _uiState.value.copy(showAddTestModal = false)
    }
    
    fun showDiscountModal() {
        _uiState.value = _uiState.value.copy(showDiscountModal = true)
    }
    
    fun hideDiscountModal() {
        _uiState.value = _uiState.value.copy(showDiscountModal = false)
    }
    
    fun addTests(visitId: Int, labCode: String, testIds: List<Int>, packageIds: List<Int>) {
        viewModelScope.launch {
            val currentUser = authTokenManager.getUserData()?.let {
                // Parse user data to get username
                // For now, using a placeholder
                "user"
            } ?: "user"
            
            val request = com.pathsoft.mobile.data.api.models.AddTestsRequest(
                visitID = visitId,
                labCode = labCode,
                testSIDs = testIds,
                packageIDs = if (packageIds.isEmpty()) null else packageIds,
                createdBy = currentUser
            )
            
            visitWorkspaceRepository.addTests(request)
                .onSuccess {
                    // Recalculate billing after adding tests
                    recalculateBilling(visitId, labCode)
                    // Reload visit workspace
                    loadVisitWorkspace(visitId, labCode)
                    hideAddTestModal()
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = exception.message ?: "Failed to add tests"
                    )
                }
        }
    }
    
    fun recalculateBilling(visitId: Int, labCode: String) {
        viewModelScope.launch {
            val request = com.pathsoft.mobile.data.api.models.RecalculateBillingRequest(
                VisitID = visitId,
                LabCode = labCode,
                DiscountType = null,
                DiscountValue = null,
                TestIds = null,
                UpdatedBy = null
            )
            
            visitWorkspaceRepository.recalculateBilling(request)
                .onSuccess {
                    loadVisitWorkspace(visitId, labCode)
                }
        }
    }
    
    fun getExistingTestIds(): Set<Int> {
        return _uiState.value.visitWorkspace?.tests?.map { it.sid }?.toSet() ?: emptySet()
    }
    
    fun getCurrentUser(): String {
        return authTokenManager.getUserData()?.let {
            // Parse user data to get username
            "user"
        } ?: "user"
    }
}

