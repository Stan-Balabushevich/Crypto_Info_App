package id.slava.nt.cryptocurrencyinfoapp.domain.use_case

import id.slava.nt.cryptocurrencyinfoapp.common.Resource
import id.slava.nt.cryptocurrencyinfoapp.domain.model.Coin
import id.slava.nt.cryptocurrencyinfoapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinsDbUseCase @Inject constructor(private val repository: CoinRepository) {

    suspend operator fun invoke(): Flow<Resource<List<Coin>>> = repository.getCoinsDB()


}