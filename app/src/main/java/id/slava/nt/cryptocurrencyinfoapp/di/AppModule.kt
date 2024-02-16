package id.slava.nt.cryptocurrencyinfoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.slava.nt.cryptocurrencyinfoapp.common.Constants
import id.slava.nt.cryptocurrencyinfoapp.domain.database.CoinDatabase
import id.slava.nt.cryptocurrencyinfoapp.data.local.RealmCoinDatabaseImpl
import id.slava.nt.cryptocurrencyinfoapp.data.local.data_base_object.CoinEntity
import id.slava.nt.cryptocurrencyinfoapp.data.remote.CoinPaprikaApi
import id.slava.nt.cryptocurrencyinfoapp.data.repository.CoinRepositoryImpl
import id.slava.nt.cryptocurrencyinfoapp.domain.repository.CoinRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
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

//    @Provides
//    @Singleton
//    fun provideRealm(
//        @ApplicationContext context: Context
//    ): Realm {
//        val realmConfig = RealmConfiguration.create(
//            schema = setOf(
//                CoinEntity::class,
//            )
//        )
//        return Realm.open(realmConfig)
//    }

    @Provides
    @Singleton
    fun provideRealm(): Realm = Realm.open(
        configuration = RealmConfiguration.create(
            schema = setOf(
                CoinEntity::class
            )
        )
    )

    @Provides
    @Singleton
    fun provideCoinDatabase(realm: Realm): CoinDatabase {
        return RealmCoinDatabaseImpl(realm)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinPaprikaApi,
                              database: CoinDatabase
                    ): CoinRepository {
        return CoinRepositoryImpl(api, database)
    }

}