package me.tbandawa.android.aic.remote.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigEntity(
    @SerialName("iiif_url") val iiifUrl: String,
    @SerialName("website_url") val websiteUrl: String
)
