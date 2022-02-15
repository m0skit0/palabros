package org.m0skit0.android.palabros.presentation

import android.content.Context
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
    playGridState.collectAsState(initial = PlayGridState()).value.run {
        checkVictoryConditions(LocalContext.current)
        checkUnknownWord(LocalContext.current)
        if (isLoading) Loading()
        else PlayGridColumn(onKeyClick)
    }
}

@ExperimentalFoundationApi
@Composable
private fun PlayGridColumn(onKeyClick: (Char) -> Unit) {
    Column {
        WordGrid()
        Keyboard(onKeyClick = onKeyClick)
    }
}

private fun PlayGridState.checkVictoryConditions(context: Context) {
    if (isFinished) {
        if (isWon) context.toast("Ganaste :D")
        else context.toast("Perdiste :( La palabra era $secretWord")
    }
}

private fun PlayGridState.checkUnknownWord(context: Context) {
    if (isUnknownWord) {
        context.toast("Palabra desconocida")
    }
}
