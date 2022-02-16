package org.m0skit0.android.palabros.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.m0skit0.android.palabros.di.NAMED_PLAY_GRID_STATE_FLOW
import org.m0skit0.android.palabros.di.koin
import org.m0skit0.android.palabros.state.PlayGridState
import org.m0skit0.android.palabros.state.TRIES
import org.m0skit0.android.palabros.state.WORD_LENGTH
import org.m0skit0.android.palabros.theme.GridBackgroundColor
import org.m0skit0.android.palabros.theme.GridTextColor

@ExperimentalFoundationApi
@Preview
@Composable
fun WordGridPreview() {
    WordGrid()
}

@ExperimentalFoundationApi
@Composable
fun WordGrid(
    wordLength: Int = WORD_LENGTH,
    tries: Int = TRIES,
    gridPadding: Dp = 8.dp,
    cardPadding: Dp = 4.dp,
    textPadding: Dp = 20.dp,
    playGridState: Flow<PlayGridState> = koin.get<MutableStateFlow<PlayGridState>>(NAMED_PLAY_GRID_STATE_FLOW)
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(wordLength),
        contentPadding = PaddingValues(gridPadding)
    ) {
        repeat(wordLength * tries) { index ->
            item {
                WordGridLetter(
                    cardPadding = cardPadding,
                    textPadding = textPadding,
                    row = index / 5,
                    column = index % 5,
                    playGridState = playGridState
                )
            }
        }
    }
}

@Composable
fun WordGridLetter(
    cardPadding: Dp,
    textPadding: Dp,
    row: Int,
    column: Int,
    playGridState: Flow<PlayGridState>
) {
    val state = playGridState.collectAsState(initial = PlayGridState())
    Card(
        modifier = Modifier.padding(cardPadding),
        backgroundColor = state.value.colorFor(row, column)
    ) {
        val letter = state.value.grid.getOrNull(row)?.getOrNull(column)?.uppercase() ?: ""
        Text(
            text = letter,
            style = MaterialTheme.typography.h5,
            color = GridTextColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(textPadding),
        )
    }
}

private fun PlayGridState.colorFor(row: Int, column: Int): Color =
    if (grid.lastIndex == row) GridBackgroundColor
    else gridLetterColors.getOrNull(row)?.getOrNull(column) ?: GridBackgroundColor
