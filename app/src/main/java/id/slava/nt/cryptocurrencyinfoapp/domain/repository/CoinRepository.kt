package id.slava.nt.cryptocurrencyinfoapp.domain.repository

import id.slava.nt.cryptocurrencyinfoapp.data.remote.data_transfer_object.CoinDetailDto
import id.slava.nt.cryptocurrencyinfoapp.data.remote.data_transfer_object.CoinDto

interface CoinRepository {

    suspend fun getCoins(): List<CoinDto>
    suspend fun getCoinById(coinId: String): CoinDetailDto
}