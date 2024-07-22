package me.tbandawa.android.aic.domain.models

data class Exception(
    val status: Int,
    val error : String,
    val detail : String
)
