package com.pathsoft.mobile.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pathsoft.mobile.presentation.viewmodel.LoginViewModel
import com.pathsoft.mobile.ui.theme.*
import com.pathsoft.mobile.ui.theme.Elevation
import com.pathsoft.mobile.ui.theme.Radius
import com.pathsoft.mobile.ui.theme.Spacing

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(PRIMARY_GRADIENT_START, PRIMARY_GRADIENT_END)
                )
            )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.XLARGE)
                .align(Alignment.Center),
            shape = RoundedCornerShape(Radius.LARGE),
            elevation = CardDefaults.cardElevation(defaultElevation = Elevation.MODAL),
            colors = CardDefaults.cardColors(containerColor = BACKGROUND_WHITE)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.XXLARGE),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "PathSoft",
                    style = MaterialTheme.typography.displayLarge,
                    color = PRIMARY_BLUE,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(Spacing.XXXLARGE))
                
                OutlinedTextField(
                    value = uiState.username,
                    onValueChange = viewModel::updateUsername,
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PRIMARY_BLUE,
                        unfocusedBorderColor = BORDER_GRAY
                    )
                )
                
                Spacer(modifier = Modifier.height(Spacing.LARGE))
                
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = viewModel::updatePassword,
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PRIMARY_BLUE,
                        unfocusedBorderColor = BORDER_GRAY
                    )
                )
                
                if (uiState.errorMessage != null) {
                    Spacer(modifier = Modifier.height(Spacing.MEDIUM))
                    Text(
                        text = uiState.errorMessage,
                        color = ERROR_RED,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                
                Spacer(modifier = Modifier.height(Spacing.XXLARGE))
                
                Button(
                    onClick = { viewModel.login(onLoginSuccess) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = !uiState.isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PRIMARY_BLUE
                    ),
                    shape = RoundedCornerShape(Radius.MEDIUM)
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = BACKGROUND_WHITE,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

