package id.slava.nt.cryptocurrencyinfoapp.data.repository

import id.slava.nt.cryptocurrencyinfoapp.data.local.data_base_object.CoinEntity
import id.slava.nt.cryptocurrencyinfoapp.domain.database.CoinDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDatabase: CoinDatabase {

    private val coinsDB = mutableListOf<CoinEntity>()
    var shouldReturnError = false

    override suspend fun getCoins(): Flow<List<CoinEntity>> = flow {
        emit(coinsDB)
    }

    override suspend fun saveCoins(coins: List<CoinEntity>) {
        this.coinsDB.clear()
        this.coinsDB.addAll(coins)


    }
}