package org.m0skit0.android.palabros.usecase

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.m0skit0.android.palabros.presentation.playgrid.BACKSPACE
import org.m0skit0.android.palabros.presentation.playgrid.ENTER
import org.m0skit0.android.palabros.presentation.playgrid.UNDO
import org.m0skit0.android.palabros.state.PlayGridState

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

    "when isFinished is false and passed a key and no previous letters, it should add the key to first position of the first row" {
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

    "when isFinished is false and passed a key and already some letters on the first row, it should add the key to the first row" {
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

    "when isFinished is false and passed a key and first row is full, it should not add the key" {
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
})
