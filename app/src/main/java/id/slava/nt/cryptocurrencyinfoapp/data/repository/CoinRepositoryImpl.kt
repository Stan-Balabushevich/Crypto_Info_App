package id.slava.nt.cryptocurrencyinfoapp.data.repository

import id.slava.nt.cryptocurrencyinfoapp.common.Resource
import id.slava.nt.cryptocurrencyinfoapp.data.local.RealmDB
import id.slava.nt.cryptocurrencyinfoapp.data.remote.CoinPaprikaApi
import id.slava.nt.cryptocurrencyinfoapp.data.remote.data_transfer_object.CoinDetailDto
import id.slava.nt.cryptocurrencyinfoapp.data.remote.data_transfer_object.CoinDto
import id.slava.nt.cryptocurrencyinfoapp.data.remote.data_transfer_object.toCoin
import id.slava.nt.cryptocurrencyinfoapp.data.remote.data_transfer_object.toCoinDetail
import id.slava.nt.cryptocurrencyinfoapp.domain.model.Coin
import id.slava.nt.cryptocurrencyinfoapp.domain.model.CoinDetail
import id.slava.nt.cryptocurrencyinfoapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(private val api: CoinPaprikaApi,
//                                             private val local: RealmDB
                                            ): CoinRepository {

    // In our case,  api.getCoins() and api.getCoinById() are  REST API calls and we only need to fetch the data once (or on specific user actions),
    // we probably don't need Flow. WE can use suspend functions and handle errors accordingly, returning a Resource type.
    // This approach is simpler and more aligned with the nature of one-off API calls.
//    override suspend fun getCoins(): Resource<List<Coin>> {
//        return try {
//            val coins = api.getCoins().map { it.toCoin() }
//            Resource.Success(coins)
//        } catch(e: HttpException) {
//            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
//        } catch(e: IOException) {
//            Resource.Error("Couldn't reach server. Check your internet connection.")
//        }
//    }



    override suspend fun getCoins(): Flow<Resource<List<Coin>>>  = flow {
        try {
            emit(Resource.Loading())
            val coins = api.getCoins().map { it.toCoin() }
            emit(Resource.Success(coins))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }



    override suspend fun getCoinById(coinId: String): Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading())
            val coin = api.getCoinById(coinId).toCoinDetail()
            emit(Resource.Success(coin))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

}