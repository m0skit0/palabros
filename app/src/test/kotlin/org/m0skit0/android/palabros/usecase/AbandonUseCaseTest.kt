package org.m0skit0.android.palabros.usecase

import io.kotlintest.matchers.boolean.shouldBeFalse
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.matchers.types.shouldNotBeNull
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Test
import org.m0skit0.android.palabros.state.PlayGridState

class AbandonUseCaseTest {

    @MockK
    private lateinit var playGridFlow: MutableStateFlow<PlayGridState>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `when calling abandon() should set correct values on play state`() {
        var newPlayState: PlayGridState? = null
        every { playGridFlow.value } returns PlayGridState()
        every { playGridFlow.value = any() } answers {
            newPlayState = firstArg()
        }

        abandon(playGridFlow)

        newPlayState.run {
            shouldNotBeNull()
            isFinished.shouldBeTrue()
            isWon.shouldBeFalse()
        }
    }
}
