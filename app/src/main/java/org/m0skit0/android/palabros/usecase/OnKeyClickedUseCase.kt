package org.m0skit0.android.palabros.usecase

import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.MutableStateFlow
import org.m0skit0.android.palabros.di.NAMED_PLAY_GRID_STATE_FLOW
import org.m0skit0.android.palabros.di.koin
import org.m0skit0.android.palabros.presentation.playgrid.BACKSPACE
import org.m0skit0.android.palabros.presentation.playgrid.ENTER
import org.m0skit0.android.palabros.presentation.playgrid.UNDO
import org.m0skit0.android.palabros.state.PlayGridState
import org.m0skit0.android.palabros.theme.CorrectLetterColor
import org.m0skit0.android.palabros.theme.MisplacedLetterColor
import org.m0skit0.android.palabros.theme.WrongLetterColor

typealias OnKeyClickedUseCase = (key: Char) -> Unit

fun onKeyClicked(
    key: Char,
    state: MutableStateFlow<PlayGridState> = koin.get(NAMED_PLAY_GRID_STATE_FLOW)
) {
    state.value = state.value.cleanFlags().run {
        if (isFinished) return
        when (key) {
            BACKSPACE -> deleteLastChar()
            UNDO -> deleteWord()
            ENTER -> checkWord()
            else -> addChar(key)
        }
    }
}

private fun PlayGridState.cleanFlags(): PlayGridState =
    copy(
        isNotComplete = false,
        isUnknownWord = false,
    )

private fun PlayGridState.deleteWord(): PlayGridState =
    copy(grid = grid.dropLast(1).plusElement(emptyList()))

private fun PlayGridState.deleteLastChar(): PlayGridState =
    grid.last().let { currentRow ->
        if (currentRow.isEmpty()) this
        else copy(grid = grid.dropLast(1).plusElement(currentRow.dropLast(1)))
    }

private fun PlayGridState.addChar(key: Char): PlayGridState =
    grid.last().let { currentRow ->
        if (currentRow.size == width) this
        else copy(grid = grid.dropLast(1).plusElement(currentRow.plusElement(key)))
    }

private fun PlayGridState.checkWord(): PlayGridState =
    grid.last().toCharArray().concatToString().let { guess ->
        if (guess.length < width) notComplete()
        else if (!wordDictionary.contains(guess)) unknownWord()
        else if (guess == secretWord) win()
        else nextWord()
    }

private fun PlayGridState.notComplete(): PlayGridState = copy(isNotComplete = true)

private fun PlayGridState.unknownWord(): PlayGridState = copy(isUnknownWord = true)

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
            secretWord[index] == letter -> CorrectLetterColor
            secretWord.contains(letter) -> MisplacedLetterColor
            else -> WrongLetterColor
        }
    }
