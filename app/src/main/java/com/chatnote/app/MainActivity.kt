package com.chatnote.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chatnote.coreui.ui.theme.AppTheme
import com.chatnote.directnotesui.DirectNotesScreen
import com.chatnote.navigation.NavigationRoute
import com.chatnote.ui.editor.FolderEditorScreen
import com.chatnote.ui.folderlist.FolderListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()
                MainApp(navController)
            }
        }
    }
}

@Composable
fun MainApp(navController: NavHostController) {
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
