package id.slava.nt.cryptocurrencyinfoapp.domain.use_case

import id.slava.nt.cryptocurrencyinfoapp.common.Resource
import id.slava.nt.cryptocurrencyinfoapp.data.remote.data_transfer_object.toCoinDetail
import id.slava.nt.cryptocurrencyinfoapp.domain.model.CoinDetail
import id.slava.nt.cryptocurrencyinfoapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinByIdUseCase @Inject constructor(private val coinRepository: CoinRepository) {

   operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = flow {
        try {
            emit(Resource.Loading())
            val coin = coinRepository.getCoinById(coinId).toCoinDetail()
            emit(Resource.Success(coin))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occured"))
        } catch(e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }

}