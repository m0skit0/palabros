package org.m0skit0.android.palabros.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import org.m0skit0.android.palabros.di.NAMED_PLAY_GRID_STATE_FLOW
import org.m0skit0.android.palabros.di.koin
import org.m0skit0.android.palabros.log.log
import org.m0skit0.android.palabros.state.PlayGridState

typealias RandomWordUseCase = suspend () -> Unit

fun randomWordUseCase(
    playGridState: MutableStateFlow<PlayGridState> = koin.get(NAMED_PLAY_GRID_STATE_FLOW),
) {
    playGridState.value.wordDictionary.random().also { randomWord ->
        playGridState.value = playGridState.value.copy(secretWord = randomWord, isLoading = false)
        log("Palabra secreta: $randomWord")
    }
}
