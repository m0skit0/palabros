package org.m0skit0.android.palabros

import android.content.Context
import android.content.Intent
import android.net.Uri
import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.shouldBe
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.m0skit0.android.palabros.usecase.definition

class DefinitionUseCaseTest {

    @MockK
    private lateinit var mockContext: Context

    @MockK
    private lateinit var mockUri: Uri

    @MockK
    private lateinit var mockIntent: Intent

    @MockK
    private lateinit var mockUriProvider: (String) -> Uri?

    @MockK
    private lateinit var mockIntentProvider: (String, Uri) -> Intent

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { mockUriProvider(any()) } returns mockUri
        every { mockIntent.setFlags(any()) } returns mockIntent
        every { mockIntentProvider(any(), any()) } returns mockIntent
        every { mockContext.startActivity(any()) } just Runs

    }

    @Test
    fun `when URI parser returns null should not construct the intent and fail gracefully`() {
        definition(mockContext, "blah", { null }, mockIntentProvider)

        verify(inverse = true) {
            mockIntentProvider(any(), any())
            mockContext.startActivity(any())
        }
    }

    @Test
    fun `when URI parser is passed URI it should be correct URI`() {
        val word = "blah"
        var url: String? = null
        every { mockUriProvider(any()) } answers {
            url = firstArg()
            mockUri
        }
        every { mockIntentProvider(any(), any()) } returns mockIntent

        definition(mockContext, word, mockUriProvider, mockIntentProvider)

        url.run {
            shouldNotBeNull()
            shouldBe("https://dle.rae.es/${word}")
        }
    }

    @Test
    fun `when passed a word it should launch a new activity to browse to the URL`() {
        definition(mockContext, "blah", mockUriProvider, mockIntentProvider)

        verify {
            mockUriProvider(any())
            mockIntentProvider(Intent.ACTION_VIEW, any())
            mockIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mockContext.startActivity(mockIntent)
        }
    }
}
