package id.slava.nt.cryptocurrencyinfoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import id.slava.nt.cryptocurrencyinfoapp.common.Constants
import id.slava.nt.cryptocurrencyinfoapp.presentation.coin_detail_screen.CoinDetailScreenVM
import id.slava.nt.cryptocurrencyinfoapp.presentation.coins_list_screen.CoinListScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.CoinListScreen.route
    ) {
        composable(route = Screen.CoinListScreen.route) {
            CoinListScreen(
                navController = navController)
        }
        composable(
            route = Screen.CoinDetailScreen.route + "?${Constants.PARAM_COIN_ID}={${Constants.PARAM_COIN_ID}}",
            arguments = listOf(
                navArgument(
                    name = Constants.PARAM_COIN_ID
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                })
        ) {

//            CoinDetailScreen()
            CoinDetailScreenVM()

        }
    }
}