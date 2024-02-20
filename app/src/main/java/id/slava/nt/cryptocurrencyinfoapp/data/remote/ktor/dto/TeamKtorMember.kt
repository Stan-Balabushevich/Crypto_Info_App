package id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor.dto


import id.slava.nt.cryptocurrencyinfoapp.data.remote.retrofit.data_transfer_object.TeamMember
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamKtorMember(
    @SerialName("id")
    val id: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("position")
    val position: String = ""
)

fun TeamKtorMember.toTeamMember(): TeamMember{

    return TeamMember(
        id = id,
        name = name,
        position = position
    )

}

