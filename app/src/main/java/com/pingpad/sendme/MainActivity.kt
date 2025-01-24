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
import com.sendme.directnotesui.screen.DirectNotesScreen
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
                navigateTo = {
                    navController.navigate(it)
                }
            )
        }
        composable<NavigationRoute.DirectNotes> {
            DirectNotesScreen(
                onBackClick = { navController.popBackStack() },
                navigateTo = {
                    navController.navigate(it)
                }
            )
        }

        composable<NavigationRoute.FolderEditor> { _ ->
            FolderEditorScreen(
                navigateTo = { navRout ->
                    navigateWithClearBackStack(
                        navController = navController,
                        targetRoute = navRout
                    )
                },
                onCancel = {
                    navController.popBackStack()
                })
        }
    }
}

fun navigateWithClearBackStack(
    navController: NavHostController,
    targetRoute: NavigationRoute
) {
    navController.navigate(targetRoute) {
        popUpTo(NavigationRoute.HomeList) { inclusive = false } // Retain HomeList in the back stack
        launchSingleTop = true // Avoid multiple instances of the target route
    }
}