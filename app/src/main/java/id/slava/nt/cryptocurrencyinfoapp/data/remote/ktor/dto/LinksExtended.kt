package id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinksExtended(
    @SerialName("stats")
    val stats: Stats = Stats(),
    @SerialName("type")
    val type: String = "",
    @SerialName("url")
    val url: String = ""
)