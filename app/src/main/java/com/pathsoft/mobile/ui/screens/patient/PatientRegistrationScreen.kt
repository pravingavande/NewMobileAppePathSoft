package com.pathsoft.mobile.ui.screens.patient

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pathsoft.mobile.data.local.AuthTokenManager
import com.pathsoft.mobile.ui.theme.*
import com.pathsoft.mobile.ui.theme.Radius
import com.pathsoft.mobile.ui.theme.Spacing
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PatientRegistrationScreen(
    onNavigateBack: () -> Unit,
    onRegistrationSuccess: (Int, String) -> Unit
) {
    var patientName by remember { mutableStateOf("") }
    var mobileNo by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var ageYear by remember { mutableStateOf("") }
    var ageMonth by remember { mutableStateOf("") }
    var ageDay by remember { mutableStateOf("") }
    
    val context = androidx.compose.ui.platform.LocalContext.current
    val authTokenManager = AuthTokenManager(context)
    val labCode = authTokenManager.getLabCode() ?: ""
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = { Text("Register Patient") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = PRIMARY_BLUE
            )
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(Spacing.LARGE)
        ) {
            FormSection(title = "Patient Information") {
                OutlinedTextField(
                    value = patientName,
                    onValueChange = { patientName = it },
                    label = { Text("Patient Name *") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(Spacing.MEDIUM))
                
                OutlinedTextField(
                    value = mobileNo,
                    onValueChange = { mobileNo = it },
                    label = { Text("Mobile Number") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(Spacing.MEDIUM))
                
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                
                Spacer(modifier = Modifier.height(Spacing.MEDIUM))
                
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )
                
                Spacer(modifier = Modifier.height(Spacing.MEDIUM))
                
                // Gender dropdown
                var expanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = gender,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Gender") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listOf("Male", "Female", "Other").forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    gender = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(Spacing.MEDIUM))
                
                // Age fields
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.SMALL)
                ) {
                    OutlinedTextField(
                        value = ageYear,
                        onValueChange = { ageYear = it },
                        label = { Text("Years") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = ageMonth,
                        onValueChange = { ageMonth = it },
                        label = { Text("Months") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = ageDay,
                        onValueChange = { ageDay = it },
                        label = { Text("Days") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(Spacing.LARGE))
            
            Button(
                onClick = {
                    // TODO: Implement registration logic
                    // For now, navigate to a dummy visit workspace
                    onRegistrationSuccess(1, labCode)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PRIMARY_BLUE
                ),
                shape = RoundedCornerShape(Radius.MEDIUM)
            ) {
                Text(
                    text = "Register Patient",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun FormSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BACKGROUND_WHITE, RoundedCornerShape(Radius.LARGE))
            .padding(Spacing.LARGE)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = TEXT_DARK,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Spacing.MEDIUM)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(PRIMARY_BLUE, RoundedCornerShape(Radius.SMALL))
        )
        Spacer(modifier = Modifier.height(Spacing.MEDIUM))
        content()
    }
}

