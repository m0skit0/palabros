package org.m0skit0.android.palabros.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.m0skit0.android.palabros.di.NAMED_PLAY_GRID_STATE_FLOW
import org.m0skit0.android.palabros.di.koin
import org.m0skit0.android.palabros.state.PlayGridState

@ExperimentalFoundationApi
@Composable
fun PlayGrid(
    onKeyClick: (Char) -> Unit,
    playGridState: Flow<PlayGridState> = koin.get<MutableStateFlow<PlayGridState>>(NAMED_PLAY_GRID_STATE_FLOW),
) {
    val state = playGridState.collectAsState(initial = PlayGridState())
    state.value.let { currentState ->
        if (currentState.isFinished) {
            if (currentState.isWon) LocalContext.current.toast("You won!")
            else LocalContext.current.toast("You lost!")
        }
    }
    Column {
        WordGrid()
        Keyboard(onKeyClick = onKeyClick)
    }
}
