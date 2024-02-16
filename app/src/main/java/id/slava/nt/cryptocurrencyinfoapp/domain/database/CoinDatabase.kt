package id.slava.nt.cryptocurrencyinfoapp.domain.database

import id.slava.nt.cryptocurrencyinfoapp.data.local.data_base_object.CoinEntity
import kotlinx.coroutines.flow.Flow

interface CoinDatabase {

    fun getCoins(): Flow<List<CoinEntity>>

    suspend fun saveCoins(coins: List<CoinEntity>)

//    suspend fun getCoinById(coinId: String): CoinDetailDto

}