package org.m0skit0.android.palabros.state

import androidx.compose.ui.graphics.Color

const val WORD_LENGTH = 5
const val TRIES = 6

data class PlayGridState(
    val isNotComplete: Boolean = false,
    val isUnknownWord: Boolean = false,
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
) {
    // Hack: avoids StateFlow not being update if same state is pushed into it
    override fun equals(other: Any?): Boolean = false
    override fun hashCode(): Int = 0
}
