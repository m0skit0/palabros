package org.m0skit0.android.palabros.presentation.playgrid

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
import org.m0skit0.android.palabros.di.NAMED_ABANDON_USE_CASE
import org.m0skit0.android.palabros.di.NAMED_DEFINITION_USE_CASE
import org.m0skit0.android.palabros.di.NAMED_PLAY_GRID_STATE_FLOW
import org.m0skit0.android.palabros.di.koin
import org.m0skit0.android.palabros.presentation.Loading
import org.m0skit0.android.palabros.presentation.toast
import org.m0skit0.android.palabros.state.PlayGridState
import org.m0skit0.android.palabros.usecase.AbandonUseCase
import org.m0skit0.android.palabros.usecase.DefinitionUseCase

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
    playGridState: Flow<PlayGridState> = koin.get<MutableStateFlow<PlayGridState>>(
        NAMED_PLAY_GRID_STATE_FLOW
    ),
    definitionUseCase: DefinitionUseCase = koin.get(NAMED_DEFINITION_USE_CASE),
) {
    playGridState.collectAsState(initial = PlayGridState()).value.run {
        if (isLoading) Loading()
        else PlayGridColumn(
            onKeyClick = onKeyClick,
            playGridState = playGridState,
        )
        CheckVictoryConditions(
            onReset = onReset,
            definitionUseCase = definitionUseCase
        )
        CheckUnknownWord()
        CheckWordComplete()
    }
}

@ExperimentalFoundationApi
@Composable
private fun PlayGridColumn(
    onKeyClick: (Char) -> Unit = {},
    playGridState: Flow<PlayGridState> = koin.get<MutableStateFlow<PlayGridState>>(
        NAMED_PLAY_GRID_STATE_FLOW
    ),
    abandonUseCase: AbandonUseCase = koin.get(NAMED_ABANDON_USE_CASE),
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxSize()
    ) {
        OptionBar(
            playGridState = playGridState,
            abandonUseCase = abandonUseCase
        )
        WordGrid(playGridState = playGridState)
        KeyboardBox(
            onKeyClick = onKeyClick,
            playGridState = playGridState,
        )
    }
}

@Composable
private fun PlayGridState.CheckVictoryConditions(
    onReset: () -> Unit,
    definitionUseCase: DefinitionUseCase,
) {
    if (isFinished) {
        if (isWon) WinSnackbar(
            onTryAgain = onReset,
            onDefinition = { definitionUseCase(secretWord) })
        else LostSnackbar(
            secretWord = secretWord,
            onTryAgain = onReset,
            onDefinition = { definitionUseCase(secretWord) })
    }
}

@Composable
private fun PlayGridState.CheckUnknownWord() {
    if (isUnknownWord) {
        LocalContext.current.toast(R.string.word_not_found)
    }
}

@Composable
private fun PlayGridState.CheckWordComplete() {
    if (isNotComplete) {
        LocalContext.current.toast(R.string.word_not_complete, width.toString())
    }
}

@ExperimentalFoundationApi
@Composable
private fun KeyboardBox(
    onKeyClick: (Char) -> Unit,
    playGridState: Flow<PlayGridState> = koin.get<MutableStateFlow<PlayGridState>>(
        NAMED_PLAY_GRID_STATE_FLOW
    )
) {
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
