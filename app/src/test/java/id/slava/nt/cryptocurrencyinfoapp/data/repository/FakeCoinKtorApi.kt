package id.slava.nt.cryptocurrencyinfoapp.data.repository

import id.slava.nt.cryptocurrencyinfoapp.util.coinDetailKtorDto
import id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor.CoinKtorApi
import id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor.dto.CoinDetailKtorDto
import id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor.dto.CoinKtorDto
import java.io.IOException

class FakeCoinKtorApi: CoinKtorApi {

    var shouldThrowIOException = false

    override suspend fun getCoins(): List<CoinKtorDto> {
        return emptyList()
    }

    override suspend fun getCoinById(coinId: String): CoinDetailKtorDto {
        if (shouldThrowIOException) {
            throw IOException()
        }
        return coinDetailKtorDto().copy(id = coinId)
    }
}
