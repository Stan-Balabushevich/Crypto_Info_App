package id.slava.nt.cryptocurrencyinfoapp.domain.use_case

import javax.inject.Inject

data class CoinUseCases @Inject constructor(
    val getCoins: GetCoinsUseCase,
    val getCoinById: GetCoinByIdUseCase
)