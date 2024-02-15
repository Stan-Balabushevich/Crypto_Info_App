package id.slava.nt.cryptocurrencyinfoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.slava.nt.cryptocurrencyinfoapp.common.Constants
import id.slava.nt.cryptocurrencyinfoapp.data.local.RealmDB
import id.slava.nt.cryptocurrencyinfoapp.data.remote.CoinPaprikaApi
import id.slava.nt.cryptocurrencyinfoapp.data.repository.CoinRepositoryImpl
import id.slava.nt.cryptocurrencyinfoapp.domain.repository.CoinRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePaprikaApi(): CoinPaprikaApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)
    }


    //TODO Create Realm Database singleton instance

//    @Provides
//    @Singleton
//    fun provideLocalDb(): RealmDB {
//        return
//    }


    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinPaprikaApi,
//                              localDb: RealmDB
                    ): CoinRepository {
        return CoinRepositoryImpl(api,
//            localDb
        )
    }





}