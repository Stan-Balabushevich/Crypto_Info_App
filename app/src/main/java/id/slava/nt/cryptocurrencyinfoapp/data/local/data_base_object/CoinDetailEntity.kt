package id.slava.nt.cryptocurrencyinfoapp.data.local.data_base_object

import id.slava.nt.cryptocurrencyinfoapp.data.remote.retrofit.data_transfer_object.TeamMember
import id.slava.nt.cryptocurrencyinfoapp.domain.model.CoinDetail
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject

class CoinDetailEntity: RealmObject {

    var coinId: String = ""
    var name: String = ""
    var description: String =""
    var symbol: String = ""
    var rank: Int = 0
    var isActive: Boolean = false
    var tags: RealmList<String> = realmListOf()
    var team: RealmList<TeamMemberEntity> = realmListOf()

}

fun CoinDetailEntity.toCoinDetail(): CoinDetail {
    return CoinDetail(
        coinId = coinId,
        name = name,
        description = description,
        symbol = symbol,
        rank = rank,
        isActive = isActive,
        tags = tags,
        team = team.map { it.toTeamMember() }
    )
}

fun TeamMemberEntity.toTeamMember(): TeamMember {
    return  TeamMember(
        id = this@toTeamMember.id,
        name = this@toTeamMember.name,
        position = this@toTeamMember.position
    )

}
