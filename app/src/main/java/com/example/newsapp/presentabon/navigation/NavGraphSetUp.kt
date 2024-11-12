package com.example.newsapp.presentabon.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.newsapp.presentabon.articlescreen.ArticleScreen
import com.example.newsapp.presentabon.newsscreen.NewsScreen
import com.example.newsapp.presentabon.newsscreen.NewsScreenViewModel

@Composable
fun NavGraphSetUp(
    navController: NavHostController = rememberNavController(),
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    NavHost(navController = navController, startDestination = NewsScreen) {

        composable<NewsScreen> {
            val viewModel: NewsScreenViewModel = hiltViewModel()
            NewsScreen(
                state = viewModel.state,
                onEvent = viewModel::onEvent,
                isDarkTheme = isDarkTheme, // Pass theme state
                onToggleTheme = onToggleTheme, // Pass toggle function
                onRedFullStoryButtonClicked = { url ->
                    navController.navigate(ArticleScreen(url))
                }
            )
        }

        composable<ArticleScreen> { backStackEntry ->
            val articleScreen: ArticleScreen = backStackEntry.toRoute()
            ArticleScreen(
                url = articleScreen.webUrl,
                onBackPressed = {
                    navController.popBackStack(NewsScreen, inclusive = false)
                }
            )
        }
    }
}
