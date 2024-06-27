package me.tbandawa.android.aic.remote.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pagination(
    @SerialName("total") val total: Int,
    @SerialName("limit") val limit: Int,
    @SerialName("offset") val offset: Int,
    @SerialName("total_pages" ) val totalPages: Int,
    @SerialName("current_page") val currentPage: Int,
    @SerialName("next_url") val nextUrl: String
)
