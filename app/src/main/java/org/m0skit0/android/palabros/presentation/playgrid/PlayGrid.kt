package org.m0skit0.android.palabros.presentation.playgrid

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.m0skit0.android.palabros.R
import org.m0skit0.android.palabros.di.NAMED_PLAY_GRID_STATE_FLOW
import org.m0skit0.android.palabros.di.koin
import org.m0skit0.android.palabros.presentation.Loading
import org.m0skit0.android.palabros.presentation.toast
import org.m0skit0.android.palabros.state.PlayGridState

@ExperimentalFoundationApi
@Preview
@Composable
fun PlayGridPreview() {
    PlayGridColumn(
        playGridState = MutableStateFlow(PlayGridState())
    )
}

@ExperimentalFoundationApi
@Composable
fun PlayGrid(
    onKeyClick: (Char) -> Unit = {},
    onReset: () -> Unit = {},
    playGridState: Flow<PlayGridState> = koin.get<MutableStateFlow<PlayGridState>>(NAMED_PLAY_GRID_STATE_FLOW),
) {
    playGridState.collectAsState(initial = PlayGridState()).value.run {
        if (isLoading) Loading()
        else PlayGridColumn(
            onKeyClick = onKeyClick,
            playGridState = playGridState,
        )
        CheckVictoryConditions(onReset)
        checkUnknownWord(LocalContext.current)
    }
}

@ExperimentalFoundationApi
@Composable
private fun PlayGridColumn(
    onKeyClick: (Char) -> Unit = {},
    playGridState: Flow<PlayGridState> = koin.get<MutableStateFlow<PlayGridState>>(NAMED_PLAY_GRID_STATE_FLOW)
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxSize()
    ) {
        OptionBar()
        WordGrid(
            playGridState = playGridState,
        )
        Box(
            modifier = Modifier
                .padding(
                    start = 5.dp,
                    end = 5.dp,
                ),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Keyboard(
                onKeyClick = onKeyClick,
                playGridState = playGridState,
            )
        }
    }
}

@Composable
private fun PlayGridState.CheckVictoryConditions(onReset: () -> Unit) {
    if (isFinished) {
        if (isWon) WinSnackbar(onTryAgain = onReset)
        else LostSnackbar(secretWord = secretWord, onTryAgain = onReset)
    }
}

private fun PlayGridState.checkUnknownWord(context: Context) {
    if (isUnknownWord) {
        context.toast(R.string.word_not_found)
    }
}
