package org.m0skit0.android.palabros.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import org.m0skit0.android.palabros.di.NAMED_PLAY_GRID_STATE_FLOW
import org.m0skit0.android.palabros.di.koin
import org.m0skit0.android.palabros.state.PlayGridState


typealias OnLongKeyClickedUseCase = (key: Char) -> Unit

fun onLongKeyCliked(
    key: Char,
    state: MutableStateFlow<PlayGridState> = koin.get(NAMED_PLAY_GRID_STATE_FLOW)
) {
    state.value = state.value.let { currentState ->
        if (currentState.isFinished) return
        when (key) {
            '⬅' -> currentState.deleteWord()
            else -> currentState
        }
    }
}

private fun PlayGridState.deleteWord(): PlayGridState =
    copy(grid = grid.dropLast(1).plusElement(emptyList()))
