package org.m0skit0.android.palabros.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import org.m0skit0.android.palabros.di.NAMED_KEYBOARD
import org.m0skit0.android.palabros.di.NAMED_WORD_GRID
import org.m0skit0.android.palabros.di.koin

typealias PlayGrid = @Composable (onKeyClick: (Char) -> Unit) -> Unit

@Composable
fun PlayGrid(
    wordGrid: WordGrid = koin.get(NAMED_WORD_GRID),
    keyboard: Keyboard = koin.get(NAMED_KEYBOARD),
    onKeyClick: (Char) -> Unit,
) {
    Column {
        wordGrid()
        keyboard(onKeyClick)
    }
}
