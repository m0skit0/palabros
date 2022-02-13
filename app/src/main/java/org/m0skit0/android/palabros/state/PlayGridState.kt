package org.m0skit0.android.palabros.state

data class PlayGridState(
    val secretWord: String = "dedos",
    val grid: List<Char> = emptyList(),
)
