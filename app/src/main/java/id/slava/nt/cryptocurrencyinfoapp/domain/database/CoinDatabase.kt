package id.slava.nt.cryptocurrencyinfoapp.domain.database

import id.slava.nt.cryptocurrencyinfoapp.data.local.data_base_object.CoinEntity
import kotlinx.coroutines.flow.Flow

interface CoinDatabase {

    suspend fun getCoins(): Flow<List<CoinEntity>>

    suspend fun saveCoins(coins: List<CoinEntity>)

    suspend fun getChunkedCoins(limit: Int, offset: Int):  Flow<List<CoinEntity>>

}