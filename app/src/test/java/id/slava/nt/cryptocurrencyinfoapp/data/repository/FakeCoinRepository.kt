package id.slava.nt.cryptocurrencyinfoapp.data.repository

import id.slava.nt.cryptocurrencyinfoapp.common.Resource
import id.slava.nt.cryptocurrencyinfoapp.domain.model.Coin
import id.slava.nt.cryptocurrencyinfoapp.domain.model.CoinDetail
import id.slava.nt.cryptocurrencyinfoapp.domain.repository.CoinRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCoinRepository: CoinRepository {

    private val coins = mutableListOf<Coin>()
    private var coinDetails = mutableMapOf<String, CoinDetail>()
    var shouldReturnError = false

//    fun addCoins(vararg newCoins: Coin) {
//        coins.addAll(newCoins)
//    }

    fun addCoins(newCoins: List<Coin>) {
        coins.addAll(newCoins)
    }

    fun setCoinDetail(coinId: String, coinDetail: CoinDetail) {
        coinDetails[coinId] = coinDetail
    }

    override suspend fun getCoins(): Flow<Resource<List<Coin>>> = flow {
        emit(Resource.Loading())

        delay(2000)

        if (shouldReturnError) {
            emit(Resource.Error("Test error"))
        } else {
            emit(Resource.Success(coins))
        }
    }

    override suspend fun getCoinsDB(): Flow<Resource<List<Coin>>> = flow {
        if (shouldReturnError) {
            emit(Resource.Error("Test error"))
        } else {
            emit(Resource.Success(coins))
        }
    }

    override suspend fun getCoinById(coinId: String): Flow<Resource<CoinDetail>> = flow {
        if (shouldReturnError) {
            emit(Resource.Error("Test error"))
        } else {
            coinDetails[coinId]?.let {
                emit(Resource.Success(it))
            } ?: emit(Resource.Error("Coin not found"))
        }
    }

    override suspend fun saveCoinsLocally() {
        // Implementation for saving coins, if needed for testing
    }
}
