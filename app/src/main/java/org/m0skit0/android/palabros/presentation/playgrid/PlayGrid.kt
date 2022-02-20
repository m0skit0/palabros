package org.m0skit0.android.palabros.presentation.playgrid

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.m0skit0.android.palabros.R
import org.m0skit0.android.palabros.di.NAMED_PLAY_GRID_STATE_FLOW
import org.m0skit0.android.palabros.di.koin
import org.m0skit0.android.palabros.presentation.HelpDialog
import org.m0skit0.android.palabros.presentation.Loading
import org.m0skit0.android.palabros.presentation.toast
import org.m0skit0.android.palabros.state.PlayGridState
import org.m0skit0.android.palabros.theme.HelpSymbolColor

@ExperimentalFoundationApi
@Preview
@Composable
fun PlayGridPreview() {
    PlayGridColumn(
        onKeyClick = {},
        playGridState = MutableStateFlow(PlayGridState())
    )
}

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
private fun PlayGridColumn(
    onKeyClick: (Char) -> Unit,
    playGridState: Flow<PlayGridState> = koin.get<MutableStateFlow<PlayGridState>>(NAMED_PLAY_GRID_STATE_FLOW)
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .fillMaxSize()
    ) {
        HelpButton()
        WordGrid(
            playGridState = playGridState,
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Keyboard(
                onKeyClick = onKeyClick,
                playGridState = playGridState,
            )
        }
    }
}

private fun PlayGridState.checkVictoryConditions(context: Context) {
    if (isFinished) {
        if (isWon) context.toast(R.string.win)
        else context.toast(R.string.lost, secretWord)
    }
}

private fun PlayGridState.checkUnknownWord(context: Context) {
    if (isUnknownWord) {
        context.toast(R.string.word_not_found)
    }
}

@Composable
private fun HelpButton() {
    val isDialogOpen = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp, top = 5.dp),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Text(
            modifier = Modifier.clickable { isDialogOpen.value = true },
            style = MaterialTheme.typography.button,
            color = HelpSymbolColor,
            text = stringResource(R.string.help_symbol)
        )
    }
    if (isDialogOpen.value) {
        HelpDialog {
            isDialogOpen.value = false
        }
    }
}
