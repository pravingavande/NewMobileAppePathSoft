package com.pathsoft.mobile.ui.screens.testresults

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pathsoft.mobile.data.api.models.TestParameter
import com.pathsoft.mobile.data.api.models.TestWithParameters
import com.pathsoft.mobile.presentation.viewmodel.TestResultsViewModel
import com.pathsoft.mobile.ui.theme.*
import com.pathsoft.mobile.ui.theme.Radius
import com.pathsoft.mobile.ui.theme.Spacing

@Composable
fun FillResultsScreen(
    viewModel: TestResultsViewModel = hiltViewModel(),
    testIds: List<Int>,
    visitId: Int,
    patientId: Int,
    labCode: String,
    onNavigateBack: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LaunchedEffect(Unit) {
        viewModel.loadTestParameters(testIds, visitId, patientId, labCode)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fill Test Results") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.saveTestResults(labCode, patientId, visitId) {
                                onSaveSuccess()
                            }
                        },
                        enabled = !uiState.isSaving
                    ) {
                        if (uiState.isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(Icons.Default.Save, "Save")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PRIMARY_BLUE
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.saveTestResults(labCode, patientId, visitId) {
                        onSaveSuccess()
                    }
                },
                modifier = Modifier.padding(Spacing.LARGE),
                containerColor = PRIMARY_BLUE
            ) {
                if (uiState.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp,
                        color = BACKGROUND_WHITE
                    )
                } else {
                    Icon(Icons.Default.Save, "Save Results")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(Spacing.LARGE)
        ) {
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.tests.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(
                        text = "No test parameters found",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TEXT_MEDIUM
                    )
                }
            } else {
                uiState.tests.forEach { test ->
                    TestSection(
                        test = test,
                        parameterValues = uiState.parameterValues,
                        onValueChange = { paramId, value ->
                            viewModel.updateParameterValue(paramId, value)
                        },
                        isAbnormal = { param ->
                            val value = uiState.parameterValues[param.parameterID] ?: ""
                            viewModel.isParameterAbnormal(param, value)
                        }
                    )
                    Spacer(modifier = Modifier.height(Spacing.LARGE))
                }
            }
            
            if (uiState.errorMessage != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = ERROR_RED.copy(alpha = 0.1f)
                    )
                ) {
                    Text(
                        text = uiState.errorMessage,
                        modifier = Modifier.padding(Spacing.MEDIUM),
                        color = ERROR_RED,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun TestSection(
    test: TestWithParameters,
    parameterValues: Map<Int, String>,
    onValueChange: (Int, String) -> Unit,
    isAbnormal: (TestParameter) -> Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Radius.LARGE),
        elevation = CardDefaults.cardElevation(defaultElevation = Elevation.CARD),
        colors = CardDefaults.cardColors(containerColor = BACKGROUND_WHITE)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.LARGE)
        ) {
            Text(
                text = test.testName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TEXT_DARK,
                modifier = Modifier.padding(bottom = Spacing.MEDIUM)
            )
            
            Divider(modifier = Modifier.padding(bottom = Spacing.MEDIUM))
            
            // Group by sections if available
            if (test.sections != null && test.sections.isNotEmpty()) {
                test.sections.forEach { section ->
                    Text(
                        text = section.sectionName,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = PRIMARY_BLUE,
                        modifier = Modifier.padding(
                            top = Spacing.MEDIUM,
                            bottom = Spacing.SMALL
                        )
                    )
                    section.parameters.forEach { param ->
                        ParameterInput(
                            parameter = param,
                            value = parameterValues[param.parameterID] ?: "",
                            onValueChange = { onValueChange(param.parameterID, it) },
                            isAbnormal = isAbnormal(param)
                        )
                    }
                }
            } else {
                // No sections, show all parameters
                test.parameters.forEach { param ->
                    ParameterInput(
                        parameter = param,
                        value = parameterValues[param.parameterID] ?: "",
                        onValueChange = { onValueChange(param.parameterID, it) },
                        isAbnormal = isAbnormal(param)
                    )
                }
            }
        }
    }
}

@Composable
fun ParameterInput(
    parameter: TestParameter,
    value: String,
    onValueChange: (String) -> Unit,
    isAbnormal: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.SMALL)
            .background(
                if (isAbnormal && value.isNotBlank()) {
                    ERROR_RED.copy(alpha = 0.1f)
                } else {
                    androidx.compose.ui.graphics.Color.Transparent
                },
                RoundedCornerShape(Radius.SMALL)
            )
            .padding(Spacing.SMALL)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(
                text = parameter.parameterName,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = if (isAbnormal && value.isNotBlank()) ERROR_RED else TEXT_DARK
            )
            
            if (parameter.normalRangeMin != null && parameter.normalRangeMax != null) {
                Text(
                    text = "Range: ${parameter.normalRangeMin} - ${parameter.normalRangeMax} ${parameter.unit ?: ""}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TEXT_MEDIUM
                )
            }
        }
        
        Spacer(modifier = Modifier.height(Spacing.XS))
        
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = {
                Text(
                    text = parameter.unit?.let { "Enter value ($it)" } ?: "Enter value",
                    style = MaterialTheme.typography.bodySmall
                )
            },
            suffix = {
                parameter.unit?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = TEXT_MEDIUM
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isAbnormal && value.isNotBlank()) ERROR_RED else PRIMARY_BLUE,
                unfocusedBorderColor = if (isAbnormal && value.isNotBlank()) ERROR_RED else BORDER_GRAY
            )
        )
        
        if (isAbnormal && value.isNotBlank()) {
            Text(
                text = "⚠ Value outside normal range",
                style = MaterialTheme.typography.bodySmall,
                color = ERROR_RED,
                modifier = Modifier.padding(top = Spacing.XS)
            )
        }
    }
}

