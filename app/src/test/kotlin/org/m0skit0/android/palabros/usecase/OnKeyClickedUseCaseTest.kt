package org.m0skit0.android.palabros.usecase

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.m0skit0.android.palabros.presentation.playgrid.BACKSPACE
import org.m0skit0.android.palabros.presentation.playgrid.ENTER
import org.m0skit0.android.palabros.presentation.playgrid.UNDO
import org.m0skit0.android.palabros.state.PlayGridState
import org.m0skit0.android.palabros.theme.CorrectLetterColor
import org.m0skit0.android.palabros.theme.MisplacedLetterColor
import org.m0skit0.android.palabros.theme.WrongLetterColor

class OnKeyClickedUseCaseTest : StringSpec({

    lateinit var mockMutableStateFlow: MutableStateFlow<PlayGridState>

    beforeEach {
        mockMutableStateFlow = mockk()
    }

    "when isFinished is true, it should do nothing if standard letter is passed" {
        val state = PlayGridState(isFinished = true)
        every { mockMutableStateFlow.value = any() } just Runs
        every { mockMutableStateFlow.value } returns state

        onKeyClicked('a', mockMutableStateFlow)

        verify(inverse = true) {
            mockMutableStateFlow.value = any()
        }
    }

    "when isFinished is true, it should do nothing if backspace is passed" {
        val state = PlayGridState(isFinished = true)
        every { mockMutableStateFlow.value = any() } just Runs
        every { mockMutableStateFlow.value } returns state

        onKeyClicked(BACKSPACE, mockMutableStateFlow)

        verify(inverse = true) {
            mockMutableStateFlow.value = any()
        }
    }

    "when isFinished is true, it should do nothing if undo is passed" {
        val state = PlayGridState(isFinished = true)
        every { mockMutableStateFlow.value = any() } just Runs
        every { mockMutableStateFlow.value } returns state

        onKeyClicked(UNDO, mockMutableStateFlow)

        verify(inverse = true) {
            mockMutableStateFlow.value = any()
        }
    }

    "when isFinished is true, it should do nothing if enter is passed" {
        val state = PlayGridState(isFinished = true)
        every { mockMutableStateFlow.value = any() } just Runs
        every { mockMutableStateFlow.value } returns state

        onKeyClicked(ENTER, mockMutableStateFlow)

        verify(inverse = true) {
            mockMutableStateFlow.value = any()
        }
    }

    "when passed a letter and no previous letters, it should add the key to first position of the first row" {
        val state = PlayGridState(isFinished = false)
        lateinit var newState: PlayGridState
        every { mockMutableStateFlow.value = any() } answers {
            newState = firstArg()
        }
        every { mockMutableStateFlow.value } returns state

        onKeyClicked('a', mockMutableStateFlow)

        with(newState) {
            grid shouldBe listOf(listOf('a'))
            isNotComplete shouldBe state.isNotComplete
            isUnknownWord shouldBe state.isUnknownWord
            isLoading shouldBe state.isLoading
            isFinished shouldBe state.isFinished
            isWon shouldBe state.isWon
            width shouldBe state.width
            height shouldBe state.height
            secretWord shouldBe state.secretWord
            greenLetters shouldBe state.greenLetters
            yellowLetters shouldBe state.yellowLetters
            redLetters shouldBe state.redLetters
            gridLetterColors shouldBe state.gridLetterColors
            wordDictionary shouldBe state.wordDictionary
        }
    }

    "when passed a letter and already some letters on the first row, it should add the key to the first row" {
        val state = PlayGridState(isFinished = false, grid = listOf(listOf('b', 'a', 'b')))
        lateinit var newState: PlayGridState
        every { mockMutableStateFlow.value = any() } answers {
            newState = firstArg()
        }
        every { mockMutableStateFlow.value } returns state

        onKeyClicked('a', mockMutableStateFlow)

        with(newState) {
            grid shouldBe listOf(listOf('b', 'a', 'b', 'a'))
            isNotComplete shouldBe state.isNotComplete
            isUnknownWord shouldBe state.isUnknownWord
            isLoading shouldBe state.isLoading
            isFinished shouldBe state.isFinished
            isWon shouldBe state.isWon
            width shouldBe state.width
            height shouldBe state.height
            secretWord shouldBe state.secretWord
            greenLetters shouldBe state.greenLetters
            yellowLetters shouldBe state.yellowLetters
            redLetters shouldBe state.redLetters
            gridLetterColors shouldBe state.gridLetterColors
            wordDictionary shouldBe state.wordDictionary
        }
    }

    "when passed a letter and first row is full, it should not add the key" {
        val state = PlayGridState(isFinished = false, grid = listOf(listOf('b', 'a', 'b', 'a', 's')))
        lateinit var newState: PlayGridState
        every { mockMutableStateFlow.value = any() } answers {
            newState = firstArg()
        }
        every { mockMutableStateFlow.value } returns state

        onKeyClicked('a', mockMutableStateFlow)

        // NOTE: Cannot check "newState shouldBe state" because of equals override hack on PlayGridState
        with(newState) {
            grid shouldBe state.grid
            isNotComplete shouldBe state.isNotComplete
            isUnknownWord shouldBe state.isUnknownWord
            isLoading shouldBe state.isLoading
            isFinished shouldBe state.isFinished
            isWon shouldBe state.isWon
            width shouldBe state.width
            height shouldBe state.height
            secretWord shouldBe state.secretWord
            greenLetters shouldBe state.greenLetters
            yellowLetters shouldBe state.yellowLetters
            redLetters shouldBe state.redLetters
            gridLetterColors shouldBe state.gridLetterColors
            wordDictionary shouldBe state.wordDictionary
        }
    }

    "when passed ENTER and current row is empty, it should set isNotComplete to true" {
        val state = PlayGridState(isFinished = false, grid = listOf(listOf()))
        lateinit var newState: PlayGridState
        every { mockMutableStateFlow.value = any() } answers {
            newState = firstArg()
        }
        every { mockMutableStateFlow.value } returns state

        onKeyClicked(ENTER, mockMutableStateFlow)

        with(newState) {
            isNotComplete shouldBe true

            grid shouldBe state.grid
            isUnknownWord shouldBe state.isUnknownWord
            isLoading shouldBe state.isLoading
            isFinished shouldBe state.isFinished
            isWon shouldBe state.isWon
            width shouldBe state.width
            height shouldBe state.height
            secretWord shouldBe state.secretWord
            greenLetters shouldBe state.greenLetters
            yellowLetters shouldBe state.yellowLetters
            redLetters shouldBe state.redLetters
            gridLetterColors shouldBe state.gridLetterColors
            wordDictionary shouldBe state.wordDictionary
        }
    }

    "when passed ENTER and current row is not complete, it should set isNotComplete to true" {
        val state = PlayGridState(isFinished = false, grid = listOf(listOf('b', 'a', 'b', 'a')))
        lateinit var newState: PlayGridState
        every { mockMutableStateFlow.value = any() } answers {
            newState = firstArg()
        }
        every { mockMutableStateFlow.value } returns state

        onKeyClicked(ENTER, mockMutableStateFlow)

        with(newState) {
            isNotComplete shouldBe true

            grid shouldBe state.grid
            isUnknownWord shouldBe state.isUnknownWord
            isLoading shouldBe state.isLoading
            isFinished shouldBe state.isFinished
            isWon shouldBe state.isWon
            width shouldBe state.width
            height shouldBe state.height
            secretWord shouldBe state.secretWord
            greenLetters shouldBe state.greenLetters
            yellowLetters shouldBe state.yellowLetters
            redLetters shouldBe state.redLetters
            gridLetterColors shouldBe state.gridLetterColors
            wordDictionary shouldBe state.wordDictionary
        }
    }

    "when passed ENTER and current row is complete and word is not on dictionary, it should return isUnknownWord as false" {
        val state = PlayGridState(isFinished = false, grid = listOf(listOf('b', 'a', 'b', 'a', 's')))
        lateinit var newState: PlayGridState
        every { mockMutableStateFlow.value = any() } answers {
            newState = firstArg()
        }
        every { mockMutableStateFlow.value } returns state

        onKeyClicked(ENTER, mockMutableStateFlow)

        with(newState) {
            isUnknownWord shouldBe true

            grid shouldBe state.grid
            isNotComplete shouldBe state.isNotComplete
            isLoading shouldBe state.isLoading
            isFinished shouldBe state.isFinished
            isWon shouldBe state.isWon
            width shouldBe state.width
            height shouldBe state.height
            secretWord shouldBe state.secretWord
            greenLetters shouldBe state.greenLetters
            yellowLetters shouldBe state.yellowLetters
            redLetters shouldBe state.redLetters
            gridLetterColors shouldBe state.gridLetterColors
            wordDictionary shouldBe state.wordDictionary
        }
    }

    "when passed ENTER and current row is complete and word is on dictionary and word is not the secret word, it should create entry in grid and set letter colors for keyboard and grid" {
        val state = PlayGridState(
            isFinished = false,
            secretWord = "acbas",
            wordDictionary = listOf("cabaz"),
            grid = listOf(listOf('c', 'a', 'b', 'a', 'z'))
        )

        lateinit var newState: PlayGridState
        every { mockMutableStateFlow.value = any() } answers {
            newState = firstArg()
        }
        every { mockMutableStateFlow.value } returns state

        onKeyClicked(ENTER, mockMutableStateFlow)

        with(newState) {
            grid.size shouldBe state.grid.size + 1
            grid[0] shouldBe listOf('c', 'a', 'b', 'a', 'z')
            grid[1].shouldBeEmpty()
            greenLetters shouldBe listOf('b', 'a')
            yellowLetters shouldBe listOf('c', 'a', 'b', 'a')
            redLetters shouldBe listOf('z')
            gridLetterColors shouldBe listOf(
                listOf(
                    MisplacedLetterColor,
                    MisplacedLetterColor,
                    CorrectLetterColor,
                    CorrectLetterColor,
                    WrongLetterColor,
                )
            )
            isNotComplete shouldBe state.isNotComplete
            isUnknownWord shouldBe state.isUnknownWord
            isLoading shouldBe state.isLoading
            isFinished shouldBe state.isFinished
            isWon shouldBe state.isWon
            width shouldBe state.width
            height shouldBe state.height
            secretWord shouldBe state.secretWord
            wordDictionary shouldBe state.wordDictionary
        }
    }
})
