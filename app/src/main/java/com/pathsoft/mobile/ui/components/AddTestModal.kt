package com.pathsoft.mobile.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
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
import com.pathsoft.mobile.data.api.models.PackageResponse
import com.pathsoft.mobile.data.api.models.TestResponse
import com.pathsoft.mobile.presentation.viewmodel.AddTestViewModel
import com.pathsoft.mobile.ui.theme.*
import com.pathsoft.mobile.ui.theme.Radius
import com.pathsoft.mobile.ui.theme.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTestModal(
    viewModel: AddTestViewModel = hiltViewModel(),
    labCode: String,
    existingTestIds: Set<Int> = emptySet(),
    onDismiss: () -> Unit,
    onAddTests: (List<Int>, List<Int>) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Load data when modal opens
    LaunchedEffect(Unit) {
        viewModel.loadTestsAndPackages(labCode, existingTestIds)
    }
    
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxHeight(0.9f),
        shape = RoundedCornerShape(topStart = Radius.LARGE, topEnd = Radius.LARGE)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.LARGE)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Add Tests/Packages",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = TEXT_DARK
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, "Close")
                }
            }
            
            Spacer(modifier = Modifier.height(Spacing.MEDIUM))
            
            // Toggle Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.SMALL)
            ) {
                FilterChip(
                    selected = uiState.isTestMode,
                    onClick = { viewModel.toggleMode(true) },
                    label = { Text("Tests") },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = !uiState.isTestMode,
                    onClick = { viewModel.toggleMode(false) },
                    label = { Text("Packages") },
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(Spacing.MEDIUM))
            
            // Search Bar
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = viewModel::updateSearchQuery,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, "Search")
                },
                singleLine = true,
                shape = RoundedCornerShape(Radius.MEDIUM),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PRIMARY_BLUE,
                    unfocusedBorderColor = BORDER_GRAY
                )
            )
            
            Spacer(modifier = Modifier.height(Spacing.MEDIUM))
            
            // Selected Count
            if (uiState.selectedTestIds.isNotEmpty() || uiState.selectedPackageIds.isNotEmpty()) {
                Text(
                    text = "Selected: ${uiState.selectedTestIds.size + uiState.selectedPackageIds.size}",
                    style = MaterialTheme.typography.bodySmall,
                    color = PRIMARY_BLUE,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(Spacing.SMALL))
            }
            
            // List
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Spacing.XS)
                ) {
                    if (uiState.isTestMode) {
                        items(viewModel.getFilteredTests()) { test ->
                            TestSelectionItem(
                                test = test,
                                isSelected = uiState.selectedTestIds.contains(test.sid),
                                isAlreadyAdded = uiState.existingTestIds.contains(test.sid),
                                onToggle = { viewModel.toggleTestSelection(test.sid) }
                            )
                        }
                    } else {
                        items(viewModel.getFilteredPackages()) { packageItem ->
                            PackageSelectionItem(
                                packageItem = packageItem,
                                isSelected = uiState.selectedPackageIds.contains(packageItem.pid),
                                onToggle = { viewModel.togglePackageSelection(packageItem.pid) }
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(Spacing.MEDIUM))
            
            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.MEDIUM)
            ) {
                OutlinedButton(
                    onClick = {
                        viewModel.clearSelection()
                    },
                    modifier = Modifier.weight(1f),
                    enabled = uiState.selectedTestIds.isNotEmpty() || uiState.selectedPackageIds.isNotEmpty()
                ) {
                    Text("Clear")
                }
                
                Button(
                    onClick = {
                        onAddTests(
                            viewModel.getSelectedTestIds(),
                            viewModel.getSelectedPackageIds()
                        )
                        onDismiss()
                    },
                    modifier = Modifier.weight(1f),
                    enabled = uiState.selectedTestIds.isNotEmpty() || uiState.selectedPackageIds.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PRIMARY_BLUE
                    )
                ) {
                    Text("Add")
                }
            }
        }
    }
}

@Composable
fun TestSelectionItem(
    test: TestResponse,
    isSelected: Boolean,
    isAlreadyAdded: Boolean,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.XS),
        shape = RoundedCornerShape(Radius.SMALL),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                PRIMARY_BLUE.copy(alpha = 0.1f)
            } else {
                BACKGROUND_WHITE
            }
        ),
        onClick = { if (!isAlreadyAdded) onToggle() },
        enabled = !isAlreadyAdded
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.MEDIUM),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = { if (!isAlreadyAdded) onToggle() },
                enabled = !isAlreadyAdded
            )
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = test.sname,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = if (isAlreadyAdded) TEXT_MUTED else TEXT_DARK
                )
                Text(
                    text = "₹${test.rate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TEXT_MEDIUM
                )
            }
            
            if (isAlreadyAdded) {
                Surface(
                    color = WARNING_ORANGE.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(Radius.PILL)
                ) {
                    Text(
                        text = "Already Added",
                        modifier = Modifier.padding(
                            horizontal = Spacing.SMALL,
                            vertical = Spacing.XS
                        ),
                        style = MaterialTheme.typography.labelSmall,
                        color = WARNING_ORANGE,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun PackageSelectionItem(
    packageItem: PackageResponse,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.XS),
        shape = RoundedCornerShape(Radius.SMALL),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                PRIMARY_BLUE.copy(alpha = 0.1f)
            } else {
                BACKGROUND_WHITE
            }
        ),
        onClick = onToggle
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.MEDIUM),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onToggle() }
            )
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = packageItem.packageName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                packageItem.description?.let { desc ->
                    Text(
                        text = desc,
                        style = MaterialTheme.typography.bodySmall,
                        color = TEXT_MEDIUM
                    )
                }
                Text(
                    text = "₹${packageItem.Amount}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TEXT_MEDIUM
                )
            }
        }
    }
}

