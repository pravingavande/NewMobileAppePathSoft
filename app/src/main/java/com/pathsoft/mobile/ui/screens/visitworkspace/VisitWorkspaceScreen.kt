package com.pathsoft.mobile.ui.screens.visitworkspace

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pathsoft.mobile.presentation.viewmodel.AddTestViewModel
import com.pathsoft.mobile.presentation.viewmodel.DiscountViewModel
import com.pathsoft.mobile.presentation.viewmodel.VisitWorkspaceViewModel
import com.pathsoft.mobile.ui.components.AddTestModal
import com.pathsoft.mobile.ui.components.DiscountModal
import com.pathsoft.mobile.ui.theme.*
import com.pathsoft.mobile.ui.theme.Elevation
import com.pathsoft.mobile.ui.theme.Radius
import com.pathsoft.mobile.ui.theme.Spacing

@Composable
fun VisitWorkspaceScreen(
    visitId: Int,
    labCode: String,
    onNavigateBack: () -> Unit,
    viewModel: VisitWorkspaceViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Patient", "Tests", "Billing")
    
    LaunchedEffect(visitId, labCode) {
        viewModel.loadVisitWorkspace(visitId, labCode)
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(VISIT_WORKSPACE_START, VISIT_WORKSPACE_MID, VISIT_WORKSPACE_END)
                )
            )
    ) {
        // Top Navigation Bar
        TopAppBar(
            title = { Text("Visit #$visitId") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, "Back")
                }
            },
            actions = {
                if (uiState.visitWorkspace?.visit?.visitStatus != "Closed") {
                    IconButton(onClick = { viewModel.showAddTestModal() }) {
                        Icon(Icons.Default.Add, "Add Test")
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = BACKGROUND_WHITE.copy(alpha = 0.95f)
            )
        )
        
        // Tabs
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }
        
        // Content
        when (selectedTab) {
            0 -> PatientInfoPanel(uiState.visitWorkspace)
            1 -> TestDetailsPanel(
                uiState.visitWorkspace,
                onRemoveTest = { pvtId ->
                    // TODO: Implement remove test
                }
            )
            2 -> BillingPanel(
                uiState.visitWorkspace,
                onApplyDiscount = { viewModel.showDiscountModal() }
            )
        }
        
        // Modals
        if (uiState.showAddTestModal) {
            AddTestModal(
                labCode = labCode,
                existingTestIds = viewModel.getExistingTestIds(),
                onDismiss = { viewModel.hideAddTestModal() },
                onAddTests = { testIds, packageIds ->
                    viewModel.addTests(visitId, labCode, testIds, packageIds)
                }
            )
        }
        
        if (uiState.showDiscountModal && uiState.visitWorkspace != null) {
            DiscountModal(
                billing = uiState.visitWorkspace.billing,
                visitID = visitId,
                labCode = labCode,
                updatedBy = viewModel.getCurrentUser(),
                onDismiss = { viewModel.hideDiscountModal() },
                onDiscountApplied = {
                    viewModel.loadVisitWorkspace(visitId, labCode)
                }
            )
        }
        
        // Error Message
        uiState.errorMessage?.let { error ->
            LaunchedEffect(error) {
                // Show snackbar or error message
            }
        }
    }
}

@Composable
fun PatientInfoPanel(visitWorkspace: com.pathsoft.mobile.data.api.models.VisitWorkspaceData?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(Spacing.LARGE)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(Radius.LARGE),
            colors = CardDefaults.cardColors(containerColor = BACKGROUND_WHITE),
            elevation = CardDefaults.cardElevation(defaultElevation = Elevation.CARD)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.LARGE)
            ) {
                Text(
                    text = "Patient Information",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TEXT_DARK
                )
                Spacer(modifier = Modifier.height(Spacing.MEDIUM))
                
                visitWorkspace?.patient?.let { patient ->
                    InfoRow("Name", patient.patientName)
                    patient.mobileNo?.let { InfoRow("Mobile", it) }
                    patient.email?.let { InfoRow("Email", it) }
                    patient.address?.let { InfoRow("Address", it) }
                    patient.ageText?.let { InfoRow("Age", it) }
                    patient.gender?.let { InfoRow("Gender", it) }
                } ?: run {
                    Text(
                        text = "Loading patient information...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TEXT_MEDIUM
                    )
                }
            }
        }
    }
}

@Composable
fun TestDetailsPanel(
    visitWorkspace: com.pathsoft.mobile.data.api.models.VisitWorkspaceData?,
    onRemoveTest: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacing.LARGE),
        verticalArrangement = Arrangement.spacedBy(Spacing.MEDIUM)
    ) {
        if (visitWorkspace?.tests?.isEmpty() != false) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = BACKGROUND_WHITE)
                ) {
                    Text(
                        text = "No tests added",
                        modifier = Modifier.padding(Spacing.LARGE),
                        style = MaterialTheme.typography.bodyMedium,
                        color = TEXT_MEDIUM
                    )
                }
            }
        } else {
            items(visitWorkspace.tests) { test ->
                TestItemCard(
                    test = test,
                    onRemove = { onRemoveTest(test.pvtID) }
                )
            }
        }
    }
}

@Composable
fun TestItemCard(
    test: com.pathsoft.mobile.data.api.models.PatientTest,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Radius.MEDIUM),
        colors = CardDefaults.cardColors(containerColor = BACKGROUND_WHITE),
        elevation = CardDefaults.cardElevation(defaultElevation = Elevation.CARD)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.LARGE),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = test.sname,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = TEXT_DARK
                )
                Spacer(modifier = Modifier.height(Spacing.XS))
                Text(
                    text = "Rate: ₹${test.rate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TEXT_MEDIUM
                )
                test.discountAmount?.let { discount ->
                    if (discount > 0) {
                        Text(
                            text = "Discount: ₹$discount",
                            style = MaterialTheme.typography.bodySmall,
                            color = TEXT_MEDIUM
                        )
                    }
                }
                test.netAmount?.let { net ->
                    Text(
                        text = "Net: ₹$net",
                        style = MaterialTheme.typography.bodySmall,
                        color = PRIMARY_BLUE,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Delete, "Remove", tint = ERROR_RED)
            }
        }
    }
}

@Composable
fun BillingPanel(
    visitWorkspace: com.pathsoft.mobile.data.api.models.VisitWorkspaceData?,
    onApplyDiscount: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(Spacing.LARGE)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(Radius.LARGE),
            colors = CardDefaults.cardColors(containerColor = BACKGROUND_WHITE),
            elevation = CardDefaults.cardElevation(defaultElevation = Elevation.CARD)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.LARGE)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Billing Summary",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = TEXT_DARK
                    )
                    Button(
                        onClick = onApplyDiscount,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PRIMARY_BLUE
                        )
                    ) {
                        Text("Apply Discount")
                    }
                }
                
                Spacer(modifier = Modifier.height(Spacing.MEDIUM))
                
                visitWorkspace?.billing?.let { billing ->
                    BillingRow("Gross Amount", billing.grossAmount)
                    BillingRow("Discount", billing.discountAmount, TEXT_MEDIUM)
                    Divider(modifier = Modifier.padding(vertical = Spacing.SMALL))
                    BillingRow("Net Amount", billing.netAmount, PRIMARY_BLUE, FontWeight.Bold)
                    BillingRow("Paid Amount", billing.paidAmount, SUCCESS_GREEN, FontWeight.Medium)
                    BillingRow("Balance Amount", billing.balanceAmount, TEXT_DARK, FontWeight.SemiBold)
                } ?: run {
                    Text(
                        text = "Loading billing information...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TEXT_MEDIUM
                    )
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.XS),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TEXT_MEDIUM,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = TEXT_DARK
        )
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
            .padding(vertical = Spacing.SMALL),
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
