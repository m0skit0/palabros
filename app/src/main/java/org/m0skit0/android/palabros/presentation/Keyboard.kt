package org.m0skit0.android.palabros.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.m0skit0.android.palabros.di.NAMED_PLAY_GRID_STATE_FLOW
import org.m0skit0.android.palabros.di.koin
import org.m0skit0.android.palabros.state.PlayGridState

private val QWERTY = listOf(
    "qwertyuiop",
    "asdfghjklñ",
    "⬅zxcvbnm ⎆",
)

@ExperimentalFoundationApi
@Preview
@Composable
fun KeyboardPreview() {
    Keyboard(onKeyClick = {})
}

@ExperimentalFoundationApi
@Composable
fun Keyboard(
    layout: List<String> = QWERTY,
    gridPadding: Dp = 8.dp,
    keyPadding: Dp = 4.dp,
    textPadding: Dp = 4.dp,
    fontSize: TextUnit = 16.sp,
    onKeyClick: (Char) -> Unit,
    playGridState: Flow<PlayGridState> = koin.get<MutableStateFlow<PlayGridState>>(NAMED_PLAY_GRID_STATE_FLOW)
) {
    val state = playGridState.collectAsState(initial = PlayGridState())
    LazyVerticalGrid(
        cells = GridCells.Fixed(layout.first().length),
        contentPadding = PaddingValues(gridPadding)
    ) {
        layout.forEach { line ->
            items(items = line.toCharArray().asList()) { key ->
                KeyboardKeyCard(
                    key,
                    keyPadding,
                    textPadding,
                    fontSize,
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
    fontSize: TextUnit = 16.sp,
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
        KeyboardKeyText(key, textPadding, fontSize)
    }
}

@Composable
fun KeyboardKeyText(
    key: Char,
    textPadding: Dp,
    fontSize: TextUnit,
) {
    Text(
        text = key.uppercase(),
        fontSize = fontSize,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(textPadding),
    )
}

private fun PlayGridState.colorForKey(key: Char): Color =
    when {
        (key == ' ') -> Color.Transparent
        greenLetters.contains(key) -> Color.Green
        yellowLetters.contains(key) -> Color.Yellow
        redLetters.contains(key) -> Color.Red
        else -> Color.LightGray
    }
