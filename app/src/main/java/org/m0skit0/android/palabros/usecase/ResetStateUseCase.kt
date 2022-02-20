package org.m0skit0.android.palabros.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import org.m0skit0.android.palabros.di.NAMED_PLAY_GRID_STATE_FLOW
import org.m0skit0.android.palabros.di.NAMED_RANDOM_WORD_USE_CASE
import org.m0skit0.android.palabros.di.koin
import org.m0skit0.android.palabros.state.PlayGridState

typealias ResetStateUseCase = () -> Unit

fun resetStateUseCase(
    randomWordUseCase: RandomWordUseCase  = koin.get(NAMED_RANDOM_WORD_USE_CASE),
    playGridState: MutableStateFlow<PlayGridState> = koin.get(NAMED_PLAY_GRID_STATE_FLOW),
) {
    playGridState.value.let { previousState ->
        playGridState.value = PlayGridState().copy(wordDictionary = previousState.wordDictionary)
    }
    randomWordUseCase()
}
