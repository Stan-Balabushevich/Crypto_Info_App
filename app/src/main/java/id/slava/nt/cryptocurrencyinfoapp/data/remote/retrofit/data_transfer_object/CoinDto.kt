package id.slava.nt.cryptocurrencyinfoapp.data.remote.retrofit.data_transfer_object

import com.google.gson.annotations.SerializedName
import id.slava.nt.cryptocurrencyinfoapp.data.local.data_base_object.CoinEntity
import id.slava.nt.cryptocurrencyinfoapp.domain.model.Coin


data class CoinDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("is_new")
    val isNew: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("type")
    val type: String
)

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        isActive = isActive,
        name = name,
        rank = rank,
        symbol = symbol
    )
}

fun CoinDto.toCoinEntity(): CoinEntity {
    return CoinEntity().apply {
        id = this@toCoinEntity.id
        isActive = this@toCoinEntity.isActive
        isNew = this@toCoinEntity.isNew
        name = this@toCoinEntity.name
        rank = this@toCoinEntity.rank
        symbol = this@toCoinEntity.symbol
        type = this@toCoinEntity.type
    }
}