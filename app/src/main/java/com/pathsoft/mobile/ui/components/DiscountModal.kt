package com.pathsoft.mobile.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pathsoft.mobile.data.api.models.Billing
import com.pathsoft.mobile.presentation.viewmodel.DiscountViewModel
import com.pathsoft.mobile.ui.theme.*
import com.pathsoft.mobile.ui.theme.Radius
import com.pathsoft.mobile.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscountModal(
    viewModel: DiscountViewModel = hiltViewModel(),
    billing: Billing,
    visitID: Int,
    labCode: String,
    updatedBy: String,
    onDismiss: () -> Unit,
    onDiscountApplied: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Set billing when modal opens
    LaunchedEffect(billing) {
        viewModel.setBilling(billing)
    }
    
    ModalBottomSheet(
        onDismissRequest = { /* Don't dismiss on outside click */ },
        modifier = Modifier.fillMaxHeight(0.85f),
        shape = RoundedCornerShape(topStart = Radius.LARGE, topEnd = Radius.LARGE)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(Spacing.LARGE)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Apply Discount",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TEXT_DARK
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, "Close")
                }
            }
            
            Spacer(modifier = Modifier.height(Spacing.MEDIUM))
            
            // Discount Category
            Text(
                text = "Discount Category",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                color = TEXT_DARK
            )
            Spacer(modifier = Modifier.height(Spacing.XS))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.SMALL)
            ) {
                FilterChip(
                    selected = uiState.discountCategory == "DOCTOR",
                    onClick = { viewModel.updateDiscountCategory("DOCTOR") },
                    label = { Text("Doctor") },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = uiState.discountCategory == "LAB",
                    onClick = { viewModel.updateDiscountCategory("LAB") },
                    label = { Text("Lab") },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = uiState.discountCategory == "WRITEOFF",
                    onClick = { viewModel.updateDiscountCategory("WRITEOFF") },
                    label = { Text("Write Off") },
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(Spacing.LARGE))
            
            // Discount Type
            Text(
                text = "Discount Type",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                color = TEXT_DARK
            )
            Spacer(modifier = Modifier.height(Spacing.XS))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.SMALL)
            ) {
                FilterChip(
                    selected = uiState.discountType == "FIXED",
                    onClick = { viewModel.updateDiscountType("FIXED") },
                    label = { Text("Fixed Amount") },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = uiState.discountType == "PERCENT",
                    onClick = { viewModel.updateDiscountType("PERCENT") },
                    label = { Text("Percentage") },
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(Spacing.LARGE))
            
            // Discount Value
            OutlinedTextField(
                value = uiState.discountValue,
                onValueChange = viewModel::updateDiscountValue,
                label = { Text("Discount Value *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                suffix = {
                    Text(
                        text = if (uiState.discountType == "PERCENT") "%" else "₹",
                        color = TEXT_MEDIUM
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PRIMARY_BLUE,
                    unfocusedBorderColor = BORDER_GRAY
                )
            )
            
            Spacer(modifier = Modifier.height(Spacing.MEDIUM))
            
            // Category-specific inputs
            if (uiState.discountCategory == "DOCTOR") {
                OutlinedTextField(
                    value = uiState.doctorDiscount,
                    onValueChange = viewModel::updateDoctorDiscount,
                    label = { Text("Doctor Discount (₹)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PRIMARY_BLUE,
                        unfocusedBorderColor = BORDER_GRAY
                    )
                )
                Spacer(modifier = Modifier.height(Spacing.MEDIUM))
            }
            
            if (uiState.discountCategory == "LAB") {
                OutlinedTextField(
                    value = uiState.labDiscount,
                    onValueChange = viewModel::updateLabDiscount,
                    label = { Text("Lab Discount (₹)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PRIMARY_BLUE,
                        unfocusedBorderColor = BORDER_GRAY
                    )
                )
                Spacer(modifier = Modifier.height(Spacing.MEDIUM))
            }
            
            if (uiState.discountCategory == "WRITEOFF") {
                OutlinedTextField(
                    value = uiState.writeOff,
                    onValueChange = viewModel::updateWriteOff,
                    label = { Text("Write Off Amount (₹)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PRIMARY_BLUE,
                        unfocusedBorderColor = BORDER_GRAY
                    )
                )
                Spacer(modifier = Modifier.height(Spacing.MEDIUM))
            }
            
            // Remarks
            OutlinedTextField(
                value = uiState.remarks,
                onValueChange = viewModel::updateRemarks,
                label = { Text("Remarks") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PRIMARY_BLUE,
                    unfocusedBorderColor = BORDER_GRAY
                )
            )
            
            Spacer(modifier = Modifier.height(Spacing.LARGE))
            
            // Billing Summary
            BillingSummary(
                grossAmount = billing.grossAmount,
                discountAmount = billing.discountAmount,
                netAmount = billing.netAmount,
                balanceAmount = billing.balanceAmount
            )
            
            Spacer(modifier = Modifier.height(Spacing.LARGE))
            
            // Error Message
            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage,
                    color = ERROR_RED,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = Spacing.MEDIUM)
                )
            }
            
            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.MEDIUM)
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }
                
                Button(
                    onClick = {
                        viewModel.applyDiscount(visitID, labCode, updatedBy) {
                            onDiscountApplied()
                            onDismiss()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !uiState.isLoading && uiState.discountValue.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PRIMARY_BLUE
                    )
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp,
                            color = BACKGROUND_WHITE
                        )
                    } else {
                        Text("Apply Discount")
                    }
                }
            }
        }
    }
}

@Composable
fun BillingSummary(
    grossAmount: Double,
    discountAmount: Double,
    netAmount: Double,
    balanceAmount: Double
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BACKGROUND_LIGHT_START, RoundedCornerShape(Radius.SMALL))
            .padding(Spacing.MEDIUM)
    ) {
        Text(
            text = "Billing Summary",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = TEXT_DARK
        )
        Spacer(modifier = Modifier.height(Spacing.SMALL))
        
        BillingRow("Gross Amount", grossAmount)
        BillingRow("Discount", -discountAmount, TEXT_MEDIUM)
        Divider(modifier = Modifier.padding(vertical = Spacing.XS))
        BillingRow("Net Amount", netAmount, PRIMARY_BLUE, FontWeight.Bold)
        BillingRow("Balance Amount", balanceAmount, TEXT_DARK, FontWeight.SemiBold)
    }
}

@Composable
fun BillingRow(
    label: String,
    amount: Double,
    textColor: androidx.compose.ui.graphics.Color = TEXT_DARK,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.XS),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor,
            fontWeight = fontWeight
        )
        Text(
            text = "₹${String.format("%.2f", amount)}",
            style = MaterialTheme.typography.bodyMedium,
            color = textColor,
            fontWeight = fontWeight
        )
    }
}

