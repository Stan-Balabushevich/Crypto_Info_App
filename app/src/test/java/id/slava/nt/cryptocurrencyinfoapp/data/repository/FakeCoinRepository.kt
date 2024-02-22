package id.slava.nt.cryptocurrencyinfoapp.data.repository

import id.slava.nt.cryptocurrencyinfoapp.common.Resource
import id.slava.nt.cryptocurrencyinfoapp.util.coin
import id.slava.nt.cryptocurrencyinfoapp.util.coinDetail
import id.slava.nt.cryptocurrencyinfoapp.domain.model.Coin
import id.slava.nt.cryptocurrencyinfoapp.domain.model.CoinDetail
import id.slava.nt.cryptocurrencyinfoapp.domain.repository.CoinRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCoinRepository: CoinRepository {

    val coins = mutableListOf<Coin>()
    var coinDetail  = coinDetail()
    var shouldReturnError = false

//    fun addCoins(vararg newCoins: Coin) {
//        coins.addAll(newCoins)
//    }

    fun addCoins(newCoins: List<Coin>) {
        coins.addAll(newCoins)
    }

    override suspend fun getCoins(): Flow<Resource<List<Coin>>> = flow {
        emit(Resource.Loading())

        delay(200)

        if (shouldReturnError) {
            emit(Resource.Error("Test error"))
        } else {
            emit(Resource.Success(coins))
        }
    }

    override suspend fun getCoinsDB(): Flow<Resource<List<Coin>>> = flow {
        emit(Resource.Loading())
        if (shouldReturnError) {
            emit(Resource.Error("Test error"))
        } else {
            emit(Resource.Success(coins))
        }
    }

    override suspend fun getCoinById(coinId: String): Flow<Resource<CoinDetail>> = flow {
        emit(Resource.Loading())
        if (shouldReturnError) {
            emit(Resource.Error("Test error"))
        } else {

                emit(Resource.Success(coinDetail))
        }
    }

    override suspend fun saveCoinsLocally() {
        val dbCoins = (1..10).map {
            coin().copy(
                id = it.toString()
            )
        }
        coins.addAll(dbCoins)
    }
}
