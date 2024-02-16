package id.slava.nt.cryptocurrencyinfoapp.data.remote.data_transfer_object


import com.google.gson.annotations.SerializedName
import id.slava.nt.cryptocurrencyinfoapp.data.local.data_base_object.TeamMemberEntity

data class TeamMember(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("position")
    val position: String
)

fun TeamMember.toTeamMemberEntity(): TeamMemberEntity {
    return TeamMemberEntity().apply {
        id = this@toTeamMemberEntity.id
        name = this@toTeamMemberEntity.name
        position = this@toTeamMemberEntity.position
    }
}
