package id.slava.nt.cryptocurrencyinfoapp.data.local.data_base_object

import com.google.gson.annotations.SerializedName
import io.realm.kotlin.types.EmbeddedRealmObject

class TeamMemberEntity: EmbeddedRealmObject {

    var id: String = ""
    var name: String = ""
    var position: String = ""

}