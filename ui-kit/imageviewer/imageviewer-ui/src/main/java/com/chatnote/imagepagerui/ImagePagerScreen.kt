package com.chatnote.imagepagerui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.chatnote.coreui.ui.theme.AppTheme
import com.chatnote.imagepagerui.navigation.PagerNavigationRoute
import com.chatnote.imagepagerui.screen.imagelist.ImageListScreen
import com.chatnote.imagepagerui.screen.imagepreview.ImagePreviewScreen
import com.chatnote.imagepagerui.viewmodel.ImagePagerViewModel


@Composable
fun ImagePagerScreen(
    onBackClick: () -> Unit,
) {

    AppTheme {
        val navController = rememberNavController()
        ImagePagerNav(
            onBackClick = onBackClick,
            navController = navController
        )
    }
}

@Composable
fun ImagePagerNav(
    navController: NavHostController,
    onBackClick: () -> Unit,
    viewModel: ImagePagerViewModel = hiltViewModel(),
) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    NavHost(
        navController = navController,
        startDestination = PagerNavigationRoute.ImagesList(
            images = uiState.images
        )
    ) {
        composable<PagerNavigationRoute.ImagesList> {
            ImageListScreen(
                images = uiState.images,
                onBackClick = onBackClick
            ) { route ->
                navController.navigate(route)
            }
        }

        composable<PagerNavigationRoute.ImagePreview> { navBackStackEntry ->
            val selectedImage =
                navBackStackEntry.toRoute<PagerNavigationRoute.ImagePreview>().selectedImage
            ImagePreviewScreen(
                imageUrl = selectedImage,
                onBackClick = { navController.popBackStack() }
            )
        }

    }

}