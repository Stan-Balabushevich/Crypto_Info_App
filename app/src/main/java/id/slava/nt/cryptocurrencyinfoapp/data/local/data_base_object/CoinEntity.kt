package id.slava.nt.cryptocurrencyinfoapp.data.local.data_base_object

import id.slava.nt.cryptocurrencyinfoapp.domain.model.Coin
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId


class CoinEntity: RealmObject{

    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var id: String = ""
    var isActive: Boolean = false
    var isNew: Boolean = false
    var name: String = ""
    var rank: Int =  0
    var symbol: String = ""
    var type: String = ""

}

fun CoinEntity.toCoin(): Coin {
    return Coin(
        id = id,
        isActive = isActive,
        name = name,
        rank = rank,
        symbol = symbol
    )
}