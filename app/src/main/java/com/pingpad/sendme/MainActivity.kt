package com.pingpad.sendme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sendme.coreui.component.ui.theme.SendMeTheme
import com.sendme.directnotesui.screen.DirectNotesScreen
import com.sendme.navigation.NavigationRoute
import com.sendme.ui.FolderListScreen
import com.sendme.ui.newfolder.content.FolderEditorScreen
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
                onFolderClick = { uiEvent ->
                    navController.navigate(
                        uiEvent.route
                    )
                },
                navigateTo = {
                    navController.navigate(it)
                }
            )
        }
        composable<NavigationRoute.DirectNotes> {
            DirectNotesScreen(
                onBackClick = { navController.popBackStack() },
                onOptionsClick = {
                    // Handle options menu
                }
            )
        }

        composable<NavigationRoute.FolderEditor> { _ ->
            FolderEditorScreen(
                onCreateFolder = { uiEvent ->
                    navController.navigate(uiEvent.route)
                },
                onCancel = {
                    navController.popBackStack()
                })
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SendMeTheme {
        Greeting("Android")
    }
}