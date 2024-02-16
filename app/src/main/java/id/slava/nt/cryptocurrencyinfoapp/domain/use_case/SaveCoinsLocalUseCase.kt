package id.slava.nt.cryptocurrencyinfoapp.domain.use_case

import id.slava.nt.cryptocurrencyinfoapp.domain.repository.CoinRepository
import javax.inject.Inject

class SaveCoinsLocalUseCase @Inject constructor(private val coinRepository: CoinRepository) {

    suspend operator fun invoke(){
        coinRepository.saveCoinsLocal()
    }

}
