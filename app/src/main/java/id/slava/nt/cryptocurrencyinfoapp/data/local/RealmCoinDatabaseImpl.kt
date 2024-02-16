package id.slava.nt.cryptocurrencyinfoapp.data.local

import id.slava.nt.cryptocurrencyinfoapp.data.local.data_base_object.CoinEntity
import id.slava.nt.cryptocurrencyinfoapp.domain.database.CoinDatabase
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RealmCoinDatabaseImpl @Inject constructor(private val realm: Realm) : CoinDatabase {

    override suspend fun getCoins(): Flow<List<CoinEntity>> {
        return  realm
            .query<CoinEntity>()
            .asFlow()
            .map { result -> result.list.toList()
            }
    }

    override suspend fun saveCoins(coins: List<CoinEntity>) {
        realm.write {
            coins.forEach {
                copyToRealm(it)
            }
        }
    }

}