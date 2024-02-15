package id.slava.nt.cryptocurrencyinfoapp.presentation.coins_list_screen

import id.slava.nt.cryptocurrencyinfoapp.domain.model.Coin

data class CoinListState (
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = ""
)