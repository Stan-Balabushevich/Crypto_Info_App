package id.slava.nt.cryptocurrencyinfoapp.data.local

import id.slava.nt.cryptocurrencyinfoapp.data.local.data_base_object.CoinEntity
import id.slava.nt.cryptocurrencyinfoapp.domain.database.CoinDatabase
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
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

    override suspend fun getChunkedCoins(limit: Int, offset: Int): Flow<List<CoinEntity>> {
        // Query all coins, and then skip and take the needed chunk
        return realm
            .query<CoinEntity>()
            .asFlow()
            .map { result ->
                // Convert the result to a list and then get the subList
                val coinList = result.list.toList()
                val end = minOf(offset + limit, coinList.size)
                if (offset < end) {
                    coinList.subList(offset, end)
                } else {
                    emptyList()
                }
            }
    }

    override suspend fun saveCoins(coins: List<CoinEntity>) {
        realm.write {
            coins.forEach {
                // UpdatePolicy.ALL This policy will update an existing object
                // if its primary key already exists in the database or insert it as a new object if it doesn't.
                copyToRealm(it, UpdatePolicy.ALL)
            }
        }
    }

}