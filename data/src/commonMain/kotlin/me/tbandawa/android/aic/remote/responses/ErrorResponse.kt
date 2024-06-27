package me.tbandawa.android.aic.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse (
    val status: Int,
    val error : String,
    val detail : String
)