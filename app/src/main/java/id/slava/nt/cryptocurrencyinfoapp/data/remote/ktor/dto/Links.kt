package id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Links(
    @SerialName("explorer")
    val explorer: List<String> = emptyList(),
    @SerialName("facebook")
    val facebook: List<String> = emptyList(),
    @SerialName("reddit")
    val reddit: List<String> = emptyList(),
    @SerialName("source_code")
    val sourceCode: List<String> = emptyList(),
    @SerialName("website")
    val website: List<String> = emptyList(),
    @SerialName("youtube")
    val youtube: List<String> = emptyList()
)
