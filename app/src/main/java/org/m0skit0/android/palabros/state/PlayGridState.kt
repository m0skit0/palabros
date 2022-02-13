package org.m0skit0.android.palabros.state

const val WORD_LENGTH = 5
const val TRIES = 6

data class PlayGridState(
    val isFinished: Boolean = false,
    val isWon: Boolean = false,
    val width: Int = WORD_LENGTH,
    val height: Int = TRIES,
    val secretWord: String = "dedos",
    val grid: List<List<Char>> = listOf(emptyList()),
)
