package id.slava.nt.cryptocurrencyinfoapp.domain.repository

import id.slava.nt.cryptocurrencyinfoapp.common.Resource
import id.slava.nt.cryptocurrencyinfoapp.domain.model.Coin
import id.slava.nt.cryptocurrencyinfoapp.domain.model.CoinDetail
import kotlinx.coroutines.flow.Flow

interface CoinRepository {

    suspend fun getCoins(): Flow<Resource<List<Coin>>>
    suspend fun getCoinsDB(): Flow<Resource<List<Coin>>>
    suspend fun getCoinById(coinId: String): Flow<Resource<CoinDetail>>
    suspend fun saveCoinsLocally()
}