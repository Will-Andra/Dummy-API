/*
package com.example.projetodummydraft.util
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.tasks.await

class Translate {

    private val translator = Translation.getClient(
        TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.PORTUGUESE)
            .build()
    )

    suspend fun translate(text: String): String {
        return try {
            translator.downloadModelIfNeeded().await()
            translator.translate(text).await()
        } catch (e: Exception) {
            e.printStackTrace()
            text
        }
    }

    fun close() {
        translator.close()
    }

}


*/
