package com.pathsoft.mobile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pathsoft.mobile.data.local.AuthTokenManager
import com.pathsoft.mobile.ui.screens.dashboard.DashboardScreen
import com.pathsoft.mobile.ui.screens.login.LoginScreen
import com.pathsoft.mobile.ui.screens.patient.PatientListScreen
import com.pathsoft.mobile.ui.screens.patient.PatientRegistrationScreen
import com.pathsoft.mobile.ui.screens.testresults.FillResultsScreen
import com.pathsoft.mobile.ui.screens.visitworkspace.VisitWorkspaceScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object PatientList : Screen("patient_list")
    object PatientRegistration : Screen("patient_registration")
    object VisitWorkspace : Screen("visit_workspace/{visitId}/{labCode}") {
        fun createRoute(visitId: Int, labCode: String) = "visit_workspace/$visitId/$labCode"
    }
    object FillResults : Screen("fill_results/{testIds}/{visitId}/{patientId}/{labCode}") {
        fun createRoute(testIds: String, visitId: Int, patientId: Int, labCode: String) = 
            "fill_results/$testIds/$visitId/$patientId/$labCode"
    }
}

@Composable
fun PathSoftNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val authTokenManager = AuthTokenManager(context)
    
    val startDestination = if (authTokenManager.getToken() != null) {
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
        
        composable(Screen.FillResults.route) { backStackEntry ->
            val testIdsStr = backStackEntry.arguments?.getString("testIds") ?: ""
            val testIds = testIdsStr.split(",").mapNotNull { it.toIntOrNull() }
            val visitId = backStackEntry.arguments?.getString("visitId")?.toIntOrNull() ?: 0
            val patientId = backStackEntry.arguments?.getString("patientId")?.toIntOrNull() ?: 0
            val labCode = backStackEntry.arguments?.getString("labCode") ?: ""
            
            FillResultsScreen(
                testIds = testIds,
                visitId = visitId,
                patientId = patientId,
                labCode = labCode,
                onNavigateBack = { navController.popBackStack() },
                onSaveSuccess = { navController.popBackStack() }
            )
        }
    }
}

