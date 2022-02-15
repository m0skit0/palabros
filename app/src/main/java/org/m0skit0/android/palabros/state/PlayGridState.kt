package org.m0skit0.android.palabros.state

import androidx.compose.ui.graphics.Color

const val WORD_LENGTH = 5
const val TRIES = 6

data class PlayGridState(
    val isLoading: Boolean = true,
    val isFinished: Boolean = false,
    val isWon: Boolean = false,
    val width: Int = WORD_LENGTH,
    val height: Int = TRIES,
    val secretWord: String = "",
    val grid: List<List<Char>> = listOf(emptyList()),
    val greenLetters: List<Char> = emptyList(),
    val yellowLetters: List<Char> = emptyList(),
    val redLetters: List<Char> = emptyList(),
    val gridLetterColors: List<List<Color>> = emptyList(),
    val wordDictionary: List<String> = emptyList(),
)
