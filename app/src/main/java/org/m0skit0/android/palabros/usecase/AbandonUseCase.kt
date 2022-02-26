package org.m0skit0.android.palabros.usecase

import kotlinx.coroutines.flow.MutableStateFlow
import org.m0skit0.android.palabros.di.NAMED_PLAY_GRID_STATE_FLOW
import org.m0skit0.android.palabros.di.koin
import org.m0skit0.android.palabros.state.PlayGridState

typealias AbandonUseCase = () -> Unit

fun abandon(
    state: MutableStateFlow<PlayGridState> = koin.get(NAMED_PLAY_GRID_STATE_FLOW)
) {
    state.value = with(state.value) {
        copy(
            isFinished = true,
            isWon = false,
        )
    }
}
