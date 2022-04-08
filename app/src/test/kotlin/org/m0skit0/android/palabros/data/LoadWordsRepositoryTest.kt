package org.m0skit0.android.palabros.data

import android.content.Context
import io.kotest.core.spec.style.StringSpec
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.m0skit0.android.palabros.state.PlayGridState
import java.io.InputStream

class LoadWordsRepositoryTest : StringSpec({

    lateinit var mockContext: Context
    lateinit var mockMutableStateFlow: MutableStateFlow<PlayGridState>

    beforeEach {
        mockContext = mockk()
        mockMutableStateFlow = mockk()
    }

    "when word dictionary is not empty on the play state, should do nothing" {
        val playGridState = PlayGridState(wordDictionary = listOf("one"))
        val jsonReader = { _ : InputStream -> listOf("duh") }
        every { mockContext.assets } returns mockk()
        every { mockMutableStateFlow.value } returns playGridState
        every { mockMutableStateFlow.value = any() } just Runs

        loadWordsRepository(mockContext, jsonReader = jsonReader, mockMutableStateFlow)

        verify(inverse = true) {
            mockContext.assets
            jsonReader(any())
        }
        verify {
            mockMutableStateFlow.value = playGridState
        }
    }
})
