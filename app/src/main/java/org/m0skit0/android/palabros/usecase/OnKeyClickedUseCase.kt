package org.m0skit0.android.palabros.usecase

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import org.m0skit0.android.palabros.di.NAMED_PLAY_GRID_STATE_FLOW
import org.m0skit0.android.palabros.di.koin
import org.m0skit0.android.palabros.state.PlayGridState

typealias OnKeyClickedUseCase = (key: Char) -> Unit

fun onKeyClicked(
    key: Char,
    state: MutableStateFlow<PlayGridState> = koin.get(NAMED_PLAY_GRID_STATE_FLOW)
) {
    state.value = state.value.let { currentState ->
        if (currentState.isFinished) return
        when (key) {
            '⬅' -> currentState.deleteLastChar()
            '⎆' -> currentState.checkWord()
            else -> currentState.addChar(key)
        }
    }.copy(showSecretWord = false)
}

private fun PlayGridState.deleteLastChar(): PlayGridState =
    grid.last().let { currentRow ->
        if (currentRow.isEmpty()) this
        else copy(grid = grid.dropLast(1).plusElement(currentRow.dropLast(1)))
    }.copy(isUnknownWord = false)

private fun PlayGridState.addChar(key: Char): PlayGridState =
    grid.last().let { currentRow ->
        if (currentRow.size == width) this
        else copy(grid = grid.dropLast(1).plusElement(currentRow.plusElement(key)))
    }

private fun PlayGridState.checkWord(): PlayGridState =
    grid.last().toCharArray().concatToString().let { guess ->
        if (!wordDictionary.contains(guess)) unknownWord()
        else {
            copy(isUnknownWord = false).run {
                if (guess == secretWord) win()
                else nextWord()
            }
        }
    }

private fun PlayGridState.unknownWord(): PlayGridState =
    copy(isUnknownWord = true)

private fun PlayGridState.win(): PlayGridState = copy(
    isFinished = true,
    isWon = true,
    grid = grid.plusElement(emptyList()),
    gridLetterColors = gridLetterColors.plusElement(wordLetterColors()),
)

private fun PlayGridState.lose(): PlayGridState = copy(
    isFinished = true,
    grid = grid.plusElement(emptyList()),
    gridLetterColors = gridLetterColors.plusElement(wordLetterColors()),
)

private fun PlayGridState.nextWord(): PlayGridState =
    if (grid.size == height) lose()
    else
        copy(
            grid = grid.plusElement(emptyList()),
            greenLetters = greenLetters.plus(greenLetters()),
            yellowLetters = yellowLetters.plus(yellowLetters()),
            redLetters = redLetters.plus(redLetters()),
            gridLetterColors = gridLetterColors.plusElement(wordLetterColors())
        )

private fun PlayGridState.greenLetters(): List<Char> =
    grid.last().let { lastWord ->
        lastWord.filterIndexed { index, letter ->
            secretWord[index] == letter
        }
    }

private fun PlayGridState.yellowLetters(): List<Char> =
    grid.last().let { lastWord ->
        lastWord.filter { letter ->
            secretWord.contains(letter)
        }
    }

private fun PlayGridState.redLetters(): List<Char> =
    grid.last().let { lastWord ->
        lastWord.filterNot { letter ->
            secretWord.contains(letter)
        }
    }

private fun PlayGridState.wordLetterColors(): List<Color> =
    grid.last().mapIndexed { index, letter ->
        when {
            secretWord[index] == letter -> Color.Green
            secretWord.contains(letter) -> Color.Yellow
            else -> Color.Red
        }
    }
