package org.m0skit0.android.palabros.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import org.m0skit0.android.palabros.di.NAMED_PLAY_GRID_STATE_FLOW
import org.m0skit0.android.palabros.di.koin
import org.m0skit0.android.palabros.state.PlayGridState

typealias OnKeyClickedUseCase = (key: Char) -> Unit

fun onKeyClicked(
    key: Char,
    state: MutableStateFlow<PlayGridState> = koin.get(NAMED_PLAY_GRID_STATE_FLOW)
) {
    state.value = state.value.let { oldState ->
        when (key) {
            '⬅' -> delete(oldState)
            else -> oldState.copy(grid = oldState.grid + key)
        }
    }
}

private fun delete(
    state: PlayGridState = koin.get(NAMED_PLAY_GRID_STATE_FLOW)
): PlayGridState =
    if (state.grid.size % 5 == 0) state
    else state.copy(grid = state.grid.dropLast(1))
