package me.tbandawa.android.aic.remote.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfoEntity(
    @SerialName("license_text" ) val licenseText: String,
    @SerialName("license_links") val licenseLinks: ArrayList<String>,
    @SerialName("version") val version: String
)
