package id.slava.nt.cryptocurrencyinfoapp.domain.use_case

import id.slava.nt.cryptocurrencyinfoapp.common.Resource
import id.slava.nt.cryptocurrencyinfoapp.domain.model.CoinDetail
import id.slava.nt.cryptocurrencyinfoapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinByIdUseCase @Inject constructor(private val coinRepository: CoinRepository) {

   suspend operator fun invoke(coinId: String): Flow<Resource<CoinDetail>> = coinRepository.getCoinById(coinId)

}