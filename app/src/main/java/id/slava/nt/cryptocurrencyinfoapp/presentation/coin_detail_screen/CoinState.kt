package id.slava.nt.cryptocurrencyinfoapp.presentation.coin_detail_screen

import id.slava.nt.cryptocurrencyinfoapp.domain.model.CoinDetail

data class CoinState (
    val isLoading: Boolean = false,
    val coin: CoinDetail? = null,
    val error: String = ""
)