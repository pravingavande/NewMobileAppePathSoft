package com.pathsoft.mobile.ui.screens.patient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pathsoft.mobile.data.api.models.PatientView
import com.pathsoft.mobile.presentation.viewmodel.PatientListViewModel
import com.pathsoft.mobile.ui.theme.*
import com.pathsoft.mobile.ui.theme.Elevation
import com.pathsoft.mobile.ui.theme.Radius
import com.pathsoft.mobile.ui.theme.Spacing

@Composable
fun PatientListScreen(
    viewModel: PatientListViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToVisitWorkspace: (Int, String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Patients") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = PRIMARY_BLUE
            )
        )
        
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { 
                searchQuery = it
                viewModel.updateSearchQuery(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.MEDIUM),
            placeholder = { Text("Search by name or mobile...") },
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
        
        // Patient List
        if (uiState.isLoading && uiState.patients.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.patients.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    text = "No patients found",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TEXT_MEDIUM
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(Spacing.MEDIUM),
                verticalArrangement = Arrangement.spacedBy(Spacing.SMALL)
            ) {
                items(uiState.patients) { patient ->
                    PatientCard(
                        patient = patient,
                        onClick = {
                            patient.visitID?.let { visitId ->
                                onNavigateToVisitWorkspace(visitId, uiState.labCode)
                            }
                        }
                    )
                }
                
                if (uiState.hasMore) {
                    item {
                        Button(
                            onClick = { viewModel.loadMore() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Spacing.MEDIUM),
                            enabled = !uiState.isLoading
                        ) {
                            if (uiState.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text("Load More")
                            }
                        }
                    }
                }
            }
        }
        
        if (uiState.errorMessage != null) {
            Snackbar(
                modifier = Modifier.padding(Spacing.MEDIUM)
            ) {
                Text(uiState.errorMessage)
            }
        }
    }
}

@Composable
fun PatientCard(
    patient: PatientView,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.SMALL, vertical = Spacing.XS),
        onClick = onClick,
        shape = RoundedCornerShape(Radius.MEDIUM),
        elevation = CardDefaults.cardElevation(defaultElevation = Elevation.CARD),
        colors = CardDefaults.cardColors(containerColor = BACKGROUND_WHITE)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.LARGE)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Text(
                    text = patient.patientName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = TEXT_DARK
                )
                patient.visitStatus?.let { status ->
                    StatusBadge(status = status)
                }
            }
            
            Spacer(modifier = Modifier.height(Spacing.SMALL))
            
            patient.mobileNo?.let { mobile ->
                Text(
                    text = "📞 $mobile",
                    style = MaterialTheme.typography.bodySmall,
                    color = TEXT_MEDIUM
                )
            }
            
            patient.visitDate?.let { date ->
                Text(
                    text = "📅 $date",
                    style = MaterialTheme.typography.bodySmall,
                    color = TEXT_MEDIUM
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                patient.totalAmount?.let { total ->
                    Text(
                        text = "Total: ₹$total",
                        style = MaterialTheme.typography.bodySmall,
                        color = TEXT_DARK,
                        fontWeight = FontWeight.Medium
                    )
                }
                patient.balanceAmount?.let { balance ->
                    Text(
                        text = "Balance: ₹$balance",
                        style = MaterialTheme.typography.bodySmall,
                        color = if (balance > 0) ERROR_RED else SUCCESS_GREEN,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val (backgroundColor, textColor) = when (status.uppercase()) {
        "PENDING" -> STATUS_WARNING to TEXT_DARK
        "COMPLETED" -> STATUS_GOOD to BACKGROUND_WHITE
        "CLOSED" -> TEXT_MUTED to BACKGROUND_WHITE
        else -> BORDER_LIGHT to TEXT_DARK
    }
    
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(Radius.PILL),
        modifier = Modifier.padding(start = Spacing.SMALL)
    ) {
        Text(
            text = status,
            modifier = Modifier.padding(horizontal = Spacing.SMALL, vertical = Spacing.XS),
            style = MaterialTheme.typography.labelSmall,
            color = textColor,
            fontWeight = FontWeight.Medium
        )
    }
}

