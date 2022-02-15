package org.m0skit0.android.palabros.data

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import org.m0skit0.android.palabros.di.NAMED_JSON_READER
import org.m0skit0.android.palabros.di.NAMED_PLAY_GRID_STATE_FLOW
import org.m0skit0.android.palabros.di.koin
import org.m0skit0.android.palabros.state.PlayGridState
import java.io.InputStream

typealias LoadWordsRepository = () -> Unit
typealias JsonReader = (InputStream) -> List<String>?

fun loadWordsRepository(
    context: Context = koin.get(),
    jsonReader: JsonReader = koin.get(NAMED_JSON_READER),
    playGridState: MutableStateFlow<PlayGridState> = koin.get(NAMED_PLAY_GRID_STATE_FLOW),
) {
    playGridState.value = if (playGridState.value.wordDictionary.isEmpty())
        context.assets.open("palabros.json").use { json ->
            jsonReader.read(json).let { newWordDictionary ->
                playGridState.value.copy(wordDictionary = newWordDictionary)
            }
        }
    else playGridState.value
}

private fun JsonReader.read(inputStream: InputStream): List<String> = try {
    this(inputStream) ?: emptyList()
} catch (e: Exception) {
    e.printStackTrace()
    emptyList()
}

