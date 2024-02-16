package id.slava.nt.cryptocurrencyinfoapp.data.repository

import android.util.Log
import id.slava.nt.cryptocurrencyinfoapp.common.Resource
import id.slava.nt.cryptocurrencyinfoapp.domain.database.CoinDatabase
import id.slava.nt.cryptocurrencyinfoapp.data.local.data_base_object.toCoin
import id.slava.nt.cryptocurrencyinfoapp.data.remote.CoinPaprikaApi
import id.slava.nt.cryptocurrencyinfoapp.data.remote.data_transfer_object.CoinDto
import id.slava.nt.cryptocurrencyinfoapp.data.remote.data_transfer_object.toCoin
import id.slava.nt.cryptocurrencyinfoapp.data.remote.data_transfer_object.toCoinDetail
import id.slava.nt.cryptocurrencyinfoapp.data.remote.data_transfer_object.toCoinEntity
import id.slava.nt.cryptocurrencyinfoapp.domain.model.Coin
import id.slava.nt.cryptocurrencyinfoapp.domain.model.CoinDetail
import id.slava.nt.cryptocurrencyinfoapp.domain.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(private val api: CoinPaprikaApi,
                                             private val database: CoinDatabase
                                            ): CoinRepository {

    private suspend fun getCoinsFromDB(): Flow<List<Coin>> {
        return  database.getCoins()
            .map { flowCoins -> flowCoins.map { it.toCoin() }}
            .flowOn(Dispatchers.IO)
    }

    private suspend fun saveCoinsToDB(coins: List<CoinDto>) {
        database.saveCoins(coins.map { it.toCoinEntity() })
    }
    override suspend fun saveCoinsLocal() {
        try {
            val coinDtoList = api.getCoins()
            saveCoinsToDB(coinDtoList)
        } catch (e: Exception){
            Log.e("Save to Local Error","Could not save data locally")

        }
    }

    override suspend fun getCoins(): Flow<Resource<List<Coin>>>  = flow {
        try {
            emit(Resource.Loading())

            val localData = getCoinsFromDB().first()

            if (localData.isNotEmpty()){
                emit(Resource.Success(localData))
            } else{
                val remoteData = withContext(Dispatchers.IO){
                    api.getCoins().map { it.toCoin() }
                }
                emit(Resource.Success(remoteData))
            }

            // Emit Resource.Success with a Flow of coins from the database
            // This will emit updates whenever the data in the database changes
//            emitAll(getCoinsFromDB().map { Resource.Success(it) })

        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
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