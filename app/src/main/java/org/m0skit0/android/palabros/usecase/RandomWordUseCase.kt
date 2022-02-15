package org.m0skit0.android.palabros.usecase

import android.content.Context
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import org.m0skit0.android.palabros.data.LoadWordsRepository
import org.m0skit0.android.palabros.di.NAMED_LOAD_WORDS_REPOSITORY
import org.m0skit0.android.palabros.di.NAMED_PLAY_GRID_STATE_FLOW
import org.m0skit0.android.palabros.di.koin
import org.m0skit0.android.palabros.state.PlayGridState

typealias RandomWordUseCase = suspend () -> Unit

suspend fun randomWordUseCase(
    context: Context = koin.get(),
    loadWordsRepository: LoadWordsRepository = koin.get(NAMED_LOAD_WORDS_REPOSITORY),
    playGridState: MutableStateFlow<PlayGridState> = koin.get(NAMED_PLAY_GRID_STATE_FLOW),
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): Unit = withContext(dispatcher) {
    loadWordsRepository(context).random().also { randomWord ->
        playGridState.value = playGridState.value.copy(secretWord = randomWord, isLoading = false)
    }
}
