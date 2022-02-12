package org.m0skit0.android.palabros.presentation

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
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
import org.m0skit0.android.palabros.di.NAMED_KEYBOARD_KEY
import org.m0skit0.android.palabros.di.NAMED_KEYBOARD_KEY_TEXT
import org.m0skit0.android.palabros.di.koin

typealias Keyboard = @Composable () -> Unit
typealias KeyboardKey = @Composable (key: Char, keyPadding: Dp, textPadding: Dp, fontSize: TextUnit) -> Unit
typealias KeyboardKeyText = @Composable (key: Char, textPadding: Dp, fontSize: TextUnit) -> Unit

private val QWERTY = listOf(
    "qwertyuiop",
    "asdfghjklñ",
    " zxcvbnm  ",
)

@ExperimentalFoundationApi
@Preview
@Composable
fun KeyboardPreview() {
    Keyboard()
}

@ExperimentalFoundationApi
@Composable
fun Keyboard(
    layout: List<String> = QWERTY,
    gridPadding: Dp = 8.dp,
    keyPadding: Dp = 4.dp,
    textPadding: Dp = 4.dp,
    fontSize: TextUnit = 16.sp,
    keyboardKey: KeyboardKey = koin().get(NAMED_KEYBOARD_KEY)
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(layout.first().length),
        contentPadding = PaddingValues(gridPadding)
    ) {
        layout.forEach { line ->
            items(items = line.toCharArray().asList()) { key ->
                keyboardKey(key, keyPadding, textPadding, fontSize)
            }
        }
    }
}

@Composable
fun KeyboardKeyButton(
    key: Char,
    buttonPadding: Dp,
    textPadding: Dp,
    fontSize: TextUnit,
    keyboardKeyText: KeyboardKeyText = koin().get(NAMED_KEYBOARD_KEY_TEXT)
) {
    Button(
        modifier = Modifier
            .padding(buttonPadding)
            .background(Color.LightGray),
        onClick = { Log.d("HEY", "HEY") }
    ) {
        keyboardKeyText(key, textPadding, fontSize)
    }
}

@Composable
fun KeyboardKeyCard(
    key: Char,
    cardPadding: Dp,
    textPadding: Dp,
    fontSize: TextUnit,
    keyboardKeyText: KeyboardKeyText = koin().get(NAMED_KEYBOARD_KEY_TEXT)
) {
    Card(
        modifier = Modifier
            .padding(cardPadding)
            .clickable { Log.d("HEY", "HEY") },
        backgroundColor = Color.LightGray,
    ) {
        keyboardKeyText(key, textPadding, fontSize)    }
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