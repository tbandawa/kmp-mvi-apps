package me.tbandawa.android.aic.remote.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtworksResponse(
    @SerialName("pagination") val pagination : Pagination,
    @SerialName("data") var data: ArrayList<ArtworkEntity>,
    @SerialName("info") var info: InfoEntity,
    @SerialName("config") var config: ConfigEntity
)
