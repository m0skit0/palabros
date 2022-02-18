package org.m0skit0.android.palabros.presentation.playgrid

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
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
import org.m0skit0.android.palabros.theme.*

private val QWERTY = listOf(
    "qwertyuiop",
    "asdfghjklñ",
    "⬅zxcvbnm ⎆",
)

@ExperimentalFoundationApi
@Preview
@Composable
fun KeyboardPreview() {
    Keyboard(
        onKeyClick = {},
        playGridState = MutableStateFlow(PlayGridState())
    )
}

@ExperimentalFoundationApi
@Composable
fun Keyboard(
    layout: List<String> = QWERTY,
    gridPadding: Dp = 4.dp,
    keyPadding: Dp = 2.dp,
    textPadding: Dp = 4.dp,
    onKeyClick: (Char) -> Unit,
    playGridState: Flow<PlayGridState> = koin.get<MutableStateFlow<PlayGridState>>(NAMED_PLAY_GRID_STATE_FLOW)
) {
    val state = playGridState.collectAsState(initial = PlayGridState())
    LazyVerticalGrid(
        cells = GridCells.Fixed(layout.first().length),
        contentPadding = PaddingValues(gridPadding),
    ) {
        layout.forEach { line ->
            items(items = line.toCharArray().asList()) { key ->
                KeyboardKeyCard(
                    key,
                    keyPadding,
                    textPadding,
                    onClick = onKeyClick,
                    state = state.value
                )
            }
        }
    }
}

@Composable
fun KeyboardKeyCard(
    key: Char,
    cardPadding: Dp = 4.dp,
    textPadding: Dp = 4.dp,
    onClick: (Char) -> Unit,
    state: PlayGridState,
) {
    val modifier = Modifier.padding(cardPadding).let {
        if (key == ' ') it
        else it.clickable { onClick(key) }
    }
    Card(
        modifier = modifier,
        backgroundColor = state.colorForKey(key),
    ) {
        KeyboardKeyText(key, textPadding)
    }
}

@Composable
fun KeyboardKeyText(
    key: Char,
    textPadding: Dp,
) {
    Text(
        text = key.uppercase(),
        style = MaterialTheme.typography.h6,
        textAlign = TextAlign.Center,
        color = KeyTextColor,
        modifier = Modifier.padding(textPadding),
    )
}

private fun PlayGridState.colorForKey(key: Char): Color =
    when {
        greenLetters.contains(key) -> CorrectLetterColor
        yellowLetters.contains(key) -> MisplacedLetterColor
        redLetters.contains(key) -> WrongLetterColor
        else -> KeysBackgroundColor
    }
