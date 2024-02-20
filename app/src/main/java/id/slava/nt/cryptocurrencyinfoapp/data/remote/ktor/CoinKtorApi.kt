package id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor

import id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor.dto.CoinDetailKtorDto
import id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor.dto.CoinKtorDto

interface CoinKtorApi {

    suspend fun getCoins(): List<CoinKtorDto>

    suspend fun getCoinById(coinId: String): CoinDetailKtorDto

}