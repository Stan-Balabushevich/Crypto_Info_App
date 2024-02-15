package id.slava.nt.cryptocurrencyinfoapp.domain.use_case

import id.slava.nt.cryptocurrencyinfoapp.common.Resource
import id.slava.nt.cryptocurrencyinfoapp.data.remote.data_transfer_object.toCoin
import id.slava.nt.cryptocurrencyinfoapp.domain.model.Coin
import id.slava.nt.cryptocurrencyinfoapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(private val repository: CoinRepository) {

    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
            try {
                emit(Resource.Loading())
                val coins = repository.getCoins().map { it.toCoin() }
                emit(Resource.Success(coins))
            } catch(e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
            } catch(e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            }
        }

}