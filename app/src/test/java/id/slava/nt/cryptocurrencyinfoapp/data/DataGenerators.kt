package id.slava.nt.cryptocurrencyinfoapp.data

import id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor.dto.CoinDetailKtorDto
import id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor.dto.Links
import id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor.dto.LinksExtended
import id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor.dto.Tag
import id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor.dto.TeamKtorMember
import id.slava.nt.cryptocurrencyinfoapp.data.remote.ktor.dto.Whitepaper
import id.slava.nt.cryptocurrencyinfoapp.data.remote.retrofit.data_transfer_object.CoinDto
import id.slava.nt.cryptocurrencyinfoapp.domain.model.Coin
import kotlinx.serialization.SerialName

fun coin(): Coin =
    Coin(
        id = "",
        isActive = false,
        name = "Bitcoin",
        rank = 0,
        symbol = "BTC"
    )

fun coinDto(): CoinDto =
    CoinDto(
        id = "123",
        isActive = true,
        isNew = false,
        name = "Bitcoin",
        rank = 1,
        symbol = "BTC",
        type = "cryptocurrency"
    )

 fun coinDetailKtorDto(): CoinDetailKtorDto =
     CoinDetailKtorDto(
         description = "description",
         developmentStatus = "development_status",
         firstDataAt = "first_data_at",
         hardwareWallet = false,
         hashAlgorithm = "hash_algorithm",
         id = "",
         isActive = true,
         isNew = false,
         lastDataAt = "",
         links = Links(),
         linksExtended = emptyList() ,
         logo = "",
         message = "",
         name = "",
         openSource= true,
         orgStructure = "",
         proofType = "",
         rank = 0,
         startedAt = "",
         symbol= "",
         tags = emptyList(),
         team= emptyList(),
         type = "",
         whitepaper =  Whitepaper()
)