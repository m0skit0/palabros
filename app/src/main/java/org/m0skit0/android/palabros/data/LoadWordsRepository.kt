package org.m0skit0.android.palabros.data

import android.content.Context
import org.m0skit0.android.palabros.di.NAMED_JSON_READER
import org.m0skit0.android.palabros.di.koin
import java.io.InputStream

typealias LoadWordsRepository = (Context) -> List<String>
typealias JsonReader = (InputStream) -> List<String>?

private var cachedWords: List<String> = emptyList()

fun loadWordsRepository(
    context: Context,
    jsonReader: JsonReader = koin.get(NAMED_JSON_READER)
): List<String> = run {
    if (cachedWords.isEmpty()) {
        context.assets.open("palabros.json").use { json ->
            cachedWords = (jsonReader(json) ?: emptyList())
        }
    }
    cachedWords
}

