package org.m0skit0.android.palabros.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import org.m0skit0.android.palabros.di.NAMED_LOGGER
import org.m0skit0.android.palabros.di.NAMED_PLAY_GRID_STATE_FLOW
import org.m0skit0.android.palabros.di.koin
import org.m0skit0.android.palabros.log.Logger
import org.m0skit0.android.palabros.state.PlayGridState

typealias OnKeyClickedUseCase = (key: Char) -> Unit

fun onKeyClicked(
    key: Char,
    state: MutableStateFlow<PlayGridState> = koin.get(NAMED_PLAY_GRID_STATE_FLOW)
) {
    state.value = state.value.let { currentState ->
        when (key) {
            '⬅' -> currentState.deleteLastChar()
            '⎆' -> currentState.checkWord()
            else -> currentState.addChar(key)
        }
    }
}

private fun PlayGridState.deleteLastChar(): PlayGridState =
    grid.last().let { currentRow ->
        if (currentRow.isEmpty()) this
        else copy(grid = grid.dropLast(1).plusElement(currentRow.dropLast(1)))
    }

private fun PlayGridState.addChar(key: Char): PlayGridState =
    grid.last().let { currentRow ->
        if (currentRow.size == width) this
        else copy(grid = grid.dropLast(1).plusElement(currentRow.plusElement(key)))
    }

private fun PlayGridState.checkWord(): PlayGridState = also {
    grid.last().toCharArray().toString().also {
        koin.get<Logger>(NAMED_LOGGER)(it)
    }
}