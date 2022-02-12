package org.m0skit0.android.palabros.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
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

private const val WORD_LENGTH = 5
private const val TRIES = 6

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
    fontSize: TextUnit = 16.sp
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(wordLength),
        contentPadding = PaddingValues(gridPadding)
    ) {
        repeat(wordLength * tries) {
            item {
                Card(
                    modifier = Modifier.padding(cardPadding),
                    backgroundColor = Color.LightGray,
                ) {
                    Text(
                        text = "A",
                        fontSize = fontSize,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(textPadding),
                    )
                }
            }
        }
    }
}
