package id.slava.nt.cryptocurrencyinfoapp.data.remote.data_transfer_object


import com.google.gson.annotations.SerializedName

data class LinksExtended(
    @SerializedName("stats")
    val stats: Stats,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)