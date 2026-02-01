package com.pathsoft.mobile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pathsoft.mobile.data.api.models.Billing
import com.pathsoft.mobile.data.repository.VisitWorkspaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DiscountUiState(
    val discountCategory: String = "LAB", // DOCTOR, LAB, WRITEOFF
    val discountType: String = "FIXED", // FIXED or PERCENT
    val discountValue: String = "",
    val doctorDiscount: String = "",
    val labDiscount: String = "",
    val writeOff: String = "",
    val remarks: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val billing: Billing? = null
)

@HiltViewModel
class DiscountViewModel @Inject constructor(
    private val visitWorkspaceRepository: VisitWorkspaceRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DiscountUiState())
    val uiState: StateFlow<DiscountUiState> = _uiState.asStateFlow()
    
    fun setBilling(billing: Billing) {
        _uiState.value = _uiState.value.copy(billing = billing)
    }
    
    fun updateDiscountCategory(category: String) {
        _uiState.value = _uiState.value.copy(discountCategory = category)
    }
    
    fun updateDiscountType(type: String) {
        _uiState.value = _uiState.value.copy(discountType = type)
    }
    
    fun updateDiscountValue(value: String) {
        _uiState.value = _uiState.value.copy(discountValue = value)
    }
    
    fun updateDoctorDiscount(value: String) {
        _uiState.value = _uiState.value.copy(doctorDiscount = value)
    }
    
    fun updateLabDiscount(value: String) {
        _uiState.value = _uiState.value.copy(labDiscount = value)
    }
    
    fun updateWriteOff(value: String) {
        _uiState.value = _uiState.value.copy(writeOff = value)
    }
    
    fun updateRemarks(remarks: String) {
        _uiState.value = _uiState.value.copy(remarks = remarks)
    }
    
    fun applyDiscount(
        visitID: Int,
        labCode: String,
        updatedBy: String,
        onSuccess: () -> Unit
    ) {
        val state = _uiState.value
        val discountValue = state.discountValue.toDoubleOrNull() ?: 0.0
        val doctorDiscount = state.doctorDiscount.toDoubleOrNull()
        val labDiscount = state.labDiscount.toDoubleOrNull()
        val writeOff = state.writeOff.toDoubleOrNull()
        
        if (discountValue <= 0) {
            _uiState.value = state.copy(errorMessage = "Discount value must be greater than 0")
            return
        }
        
        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, errorMessage = null)
            
            val request = com.pathsoft.mobile.data.api.models.ApplyDiscountRequest(
                visitID = visitID,
                labCode = labCode,
                discountType = state.discountType,
                discountValue = discountValue,
                discountCategory = state.discountCategory,
                doctorDiscount = doctorDiscount,
                labDiscount = labDiscount,
                writeOff = writeOff,
                remarks = if (state.remarks.isNotBlank()) state.remarks else null,
                updatedBy = updatedBy
            )
            
            visitWorkspaceRepository.applyDiscount(request)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    onSuccess()
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Failed to apply discount"
                    )
                }
        }
    }
    
    fun reset() {
        _uiState.value = DiscountUiState(billing = _uiState.value.billing)
    }
}

