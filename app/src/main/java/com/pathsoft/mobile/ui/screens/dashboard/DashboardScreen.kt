package com.pathsoft.mobile.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pathsoft.mobile.presentation.viewmodel.DashboardViewModel
import com.pathsoft.mobile.ui.theme.*
import com.pathsoft.mobile.ui.theme.Elevation
import com.pathsoft.mobile.ui.theme.Radius
import com.pathsoft.mobile.ui.theme.Spacing
import java.text.SimpleDateFormat
import java.util.*

data class KPICard(
    val title: String,
    val value: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onNavigateToPatients: () -> Unit,
    onNavigateToPatientRegistration: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val currentDate = dateFormat.format(Date())
    val currentTime = timeFormat.format(Date())
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(DASHBOARD_GRADIENT_START, DASHBOARD_GRADIENT_END)
                )
            )
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = "Dashboard",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = BACKGROUND_WHITE
                    )
                    Text(
                        text = "$currentDate • $currentTime",
                        style = MaterialTheme.typography.bodySmall,
                        color = BACKGROUND_WHITE.copy(alpha = 0.9f)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            )
        )
        
        // KPI Cards
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(Spacing.LARGE),
            horizontalArrangement = Arrangement.spacedBy(Spacing.MEDIUM),
            verticalArrangement = Arrangement.spacedBy(Spacing.MEDIUM)
        ) {
            items(getKPICards(uiState, onNavigateToPatients, onNavigateToPatientRegistration)) { kpi ->
                KPICard(
                    title = kpi.title,
                    value = kpi.value,
                    icon = kpi.icon,
                    onClick = kpi.onClick
                )
            }
        }
    }
}

@Composable
fun KPICard(
    title: String,
    value: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .aspectRatio(1f),
        onClick = onClick,
        shape = RoundedCornerShape(Radius.LARGE),
        elevation = CardDefaults.cardElevation(defaultElevation = Elevation.CARD),
        colors = CardDefaults.cardColors(containerColor = BACKGROUND_WHITE)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.LARGE),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall,
                    color = TEXT_MEDIUM,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(Spacing.SMALL))
                Text(
                    text = value,
                    style = MaterialTheme.typography.displayMedium,
                    color = TEXT_DARK,
                    fontWeight = FontWeight.Bold
                )
            }
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = PRIMARY_BLUE,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

private fun getKPICards(
    uiState: com.pathsoft.mobile.presentation.viewmodel.DashboardUiState,
    onNavigateToPatients: () -> Unit,
    onNavigateToPatientRegistration: () -> Unit
): List<KPICard> {
    return listOf(
        KPICard(
            title = "Pending Approvals",
            value = uiState.pendingApprovals.toString(),
            icon = Icons.Default.Pending,
            onClick = { /* Navigate to pending approvals */ }
        ),
        KPICard(
            title = "Completed",
            value = uiState.completed.toString(),
            icon = Icons.Default.CheckCircle,
            onClick = { /* Navigate to completed */ }
        ),
        KPICard(
            title = "Patients",
            value = "View All",
            icon = Icons.Default.People,
            onClick = onNavigateToPatients
        ),
        KPICard(
            title = "Register Patient",
            value = "New",
            icon = Icons.Default.PersonAdd,
            onClick = onNavigateToPatientRegistration
        )
    )
}

