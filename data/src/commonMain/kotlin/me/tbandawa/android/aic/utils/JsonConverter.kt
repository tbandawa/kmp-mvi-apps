package me.tbandawa.android.aic.utils

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class JsonConverter {

    inline fun <reified T> readJsonResponse(fileName: String): T? {
        val sharedFileReader = SharedFileReader()
        val json = Json {
            isLenient = true
            encodeDefaults = true
            ignoreUnknownKeys = true
            useAlternativeNames = true
        }
        return sharedFileReader.loadJsonFile(fileName)?.let { json.decodeFromString<T>(it) }
    }
}