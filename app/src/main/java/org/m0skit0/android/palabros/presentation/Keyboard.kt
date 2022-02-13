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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
    onKeyClick: (Char) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(layout.first().length),
        contentPadding = PaddingValues(gridPadding)
    ) {
        layout.forEach { line ->
            items(items = line.toCharArray().asList()) { key ->
                KeyboardKeyCard(key, keyPadding, textPadding, fontSize, onClick = onKeyClick)
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
    onClick: (Char) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(cardPadding)
            .clickable { onClick(key) },
        backgroundColor = Color.LightGray,
    ) {
        KeyboardKeyText(key, textPadding, fontSize)    }
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
