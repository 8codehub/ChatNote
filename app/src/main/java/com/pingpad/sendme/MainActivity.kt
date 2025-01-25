package com.pingpad.sendme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sendme.coreui.component.ui.theme.SendMeTheme
import com.sendme.directnotesui.DirectNotesScreen
import com.sendme.navigation.NavigationRoute
import com.sendme.ui.folderlist.FolderListScreen
import com.sendme.ui.editor.FolderEditorScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SendMeTheme {
                val navController = rememberNavController()
                SendMeApp(navController)
            }
        }
    }
}

@Composable
fun SendMeApp(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoute.HomeList
    ) {
        composable<NavigationRoute.HomeList> {
            FolderListScreen(
                navigateTo = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable<NavigationRoute.DirectNotes> {
            DirectNotesScreen(
                onBackClick = { navController.popBackStack() },
                navigateTo = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable<NavigationRoute.FolderEditor> { _ ->
            FolderEditorScreen(
                onCancel = { navController.popBackStack() },
                navigateTo = { route ->
                    navigateWithClearBackStack(
                        targetRoute = route,
                        navController = navController
                    )
                }
            )
        }
    }
}

fun navigateWithClearBackStack(
    targetRoute: NavigationRoute,
    navController: NavHostController
) {
    navController.navigate(targetRoute) {
        popUpTo(NavigationRoute.HomeList) { inclusive = false }
        launchSingleTop = true
    }
}
