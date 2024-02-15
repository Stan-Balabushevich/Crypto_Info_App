package id.slava.nt.cryptocurrencyinfoapp.data.remote.data_transfer_object


import com.google.gson.annotations.SerializedName

data class Whitepaper(
    @SerializedName("link")
    val link: String,
    @SerializedName("thumbnail")
    val thumbnail: String
)