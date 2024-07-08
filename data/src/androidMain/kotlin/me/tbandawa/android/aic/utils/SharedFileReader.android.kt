package me.tbandawa.android.aic.utils

import java.io.InputStreamReader

actual class SharedFileReader actual constructor() {

    actual fun loadJsonFile(fileName: String): String? {
        return javaClass.classLoader?.getResourceAsStream(fileName).use { stream ->
            InputStreamReader(stream).use { reader ->
                reader.readText()
            }
        }
    }
}