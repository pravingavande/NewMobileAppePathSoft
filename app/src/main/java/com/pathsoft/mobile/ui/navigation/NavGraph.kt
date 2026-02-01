package com.pathsoft.mobile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pathsoft.mobile.data.local.AuthTokenManager
import com.pathsoft.mobile.ui.screens.dashboard.DashboardScreen
import com.pathsoft.mobile.ui.screens.login.LoginScreen
import com.pathsoft.mobile.ui.screens.patient.PatientListScreen
import com.pathsoft.mobile.ui.screens.patient.PatientRegistrationScreen
import com.pathsoft.mobile.ui.screens.visitworkspace.VisitWorkspaceScreen
import javax.inject.Inject

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object PatientList : Screen("patient_list")
    object PatientRegistration : Screen("patient_registration")
    object VisitWorkspace : Screen("visit_workspace/{visitId}/{labCode}") {
        fun createRoute(visitId: Int, labCode: String) = "visit_workspace/$visitId/$labCode"
    }
}

@Composable
fun PathSoftNavigation(
    navController: NavHostController,
    authTokenManager: AuthTokenManager
) {
    val startDestination = if (authTokenManager.isLoggedIn()) {
        Screen.Dashboard.route
    } else {
        Screen.Login.route
    }
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToPatients = {
                    navController.navigate(Screen.PatientList.route)
                },
                onNavigateToPatientRegistration = {
                    navController.navigate(Screen.PatientRegistration.route)
                }
            )
        }
        
        composable(Screen.PatientList.route) {
            PatientListScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToVisitWorkspace = { visitId, labCode ->
                    navController.navigate(Screen.VisitWorkspace.createRoute(visitId, labCode))
                }
            )
        }
        
        composable(Screen.PatientRegistration.route) {
            PatientRegistrationScreen(
                onNavigateBack = { navController.popBackStack() },
                onRegistrationSuccess = { visitId, labCode ->
                    navController.navigate(Screen.VisitWorkspace.createRoute(visitId, labCode)) {
                        popUpTo(Screen.Dashboard.route)
                    }
                }
            )
        }
        
        composable(Screen.VisitWorkspace.route) { backStackEntry ->
            val visitId = backStackEntry.arguments?.getString("visitId")?.toIntOrNull() ?: 0
            val labCode = backStackEntry.arguments?.getString("labCode") ?: ""
            
            VisitWorkspaceScreen(
                visitId = visitId,
                labCode = labCode,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

