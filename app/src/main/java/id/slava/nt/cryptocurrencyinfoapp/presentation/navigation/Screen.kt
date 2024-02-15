package id.slava.nt.cryptocurrencyinfoapp.presentation.navigation

sealed class Screen(val route: String) {
    data object CoinListScreen: Screen("coins_list_screen")
    data object CoinDetailScreen: Screen("coin_detail_screen")
}
