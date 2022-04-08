package org.m0skit0.android.palabros.usecase

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.m0skit0.android.palabros.state.PlayGridState

class AbandonUseCaseTest : StringSpec({

    lateinit var mockPlayGridFlow: MutableStateFlow<PlayGridState>

    beforeEach {
        mockPlayGridFlow = mockk()
    }

    "when calling abandon() should set correct values on play state" {
        var newPlayState: PlayGridState? = null
        every { mockPlayGridFlow.value } returns PlayGridState()
        every { mockPlayGridFlow.value = any() } answers {
            newPlayState = firstArg()
        }

        abandon(mockPlayGridFlow)

        newPlayState.run {
            shouldNotBeNull()
            isFinished.shouldBeTrue()
            isWon.shouldBeFalse()
        }
    }
})
