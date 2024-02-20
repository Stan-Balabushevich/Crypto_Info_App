package id.slava.nt.cryptocurrencyinfoapp.data

import id.slava.nt.cryptocurrencyinfoapp.data.remote.retrofit.data_transfer_object.CoinDto
import id.slava.nt.cryptocurrencyinfoapp.domain.model.Coin

fun coin(): Coin =
    Coin(
        id = "",
        isActive = false,
        name = "Bitcoin",
        rank = 0,
        symbol = "BTC"
    )

fun coinDto(): CoinDto =
    CoinDto("123", true, false, "Bitcoin", 1, "BTC", "cryptocurrency")