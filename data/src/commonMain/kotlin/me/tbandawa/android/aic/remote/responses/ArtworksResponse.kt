package me.tbandawa.android.aic.remote.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtworksResponse(
    @SerialName("pagination") val pagination : Pagination,
    @SerialName("data") var data: ArrayList<Data>,
    @SerialName("info") var info: Info,
    @SerialName("config") var config: Config
)
