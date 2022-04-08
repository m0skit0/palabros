package org.m0skit0.android.palabros.usecase

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.m0skit0.android.palabros.data.LoadWordsRepository
import org.m0skit0.android.palabros.state.PlayGridState

@ExperimentalCoroutinesApi
class LoadWordsUseCaseTest : StringSpec({

    lateinit var mockLoadWordsRepository: LoadWordsRepository
    lateinit var mockPlayGridStateFlow: MutableStateFlow<PlayGridState>

    beforeEach {
        mockLoadWordsRepository = mockk()
        mockPlayGridStateFlow = mockk()
    }

    "when loading words it should set loading state to true before loading then to false after loading" {
        var isLoading = false
        every { mockPlayGridStateFlow.value } returns PlayGridState()
        every { mockPlayGridStateFlow.value = any() } answers {
            (firstArg() as PlayGridState).let {
                isLoading = it.isLoading
            }
        }
        every { mockLoadWordsRepository() } answers {
            isLoading.shouldBeTrue()
        }
        runTest {
            loadWordsUseCase(mockLoadWordsRepository, mockPlayGridStateFlow, StandardTestDispatcher(scheduler = testScheduler))
            isLoading.shouldBeFalse()
        }
    }

    "when loading words it should call the repository to load words" {
        every { mockPlayGridStateFlow.value } returns PlayGridState()
        every { mockPlayGridStateFlow.value = any() } just Runs
        every { mockLoadWordsRepository() } just Runs
        runTest {
            loadWordsUseCase(mockLoadWordsRepository, mockPlayGridStateFlow, StandardTestDispatcher(scheduler = testScheduler))
        }
        verify {
            mockLoadWordsRepository()
        }
    }
})
