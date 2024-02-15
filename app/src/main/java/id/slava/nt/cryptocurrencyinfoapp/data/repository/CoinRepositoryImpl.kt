package id.slava.nt.cryptocurrencyinfoapp.data.repository

import id.slava.nt.cryptocurrencyinfoapp.data.remote.CoinPaprikaApi
import id.slava.nt.cryptocurrencyinfoapp.data.remote.data_transfer_object.CoinDetailDto
import id.slava.nt.cryptocurrencyinfoapp.data.remote.data_transfer_object.CoinDto
import id.slava.nt.cryptocurrencyinfoapp.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(private val api: CoinPaprikaApi): CoinRepository {

    override suspend fun getCoins(): List<CoinDto> {
        return api.getCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinDetailDto {
        return api.getCoinById(coinId = coinId)
    }

}