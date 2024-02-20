package id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor

import id.slava.nt.cryptocurrencyinfoapp.common.Constants
import id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor.dto.CoinDetailKtorDto
import id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor.dto.CoinKtorDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class CoinKtorImpl(private val client: HttpClient): CoinKtorApi {

    override suspend fun getCoins(): List<CoinKtorDto>  {
        return client.get(Constants.COINS).body()

    }

    override suspend fun getCoinById(coinId: String): CoinDetailKtorDto {

        return client.get("${Constants.COINS}/$coinId").body()

    }
}