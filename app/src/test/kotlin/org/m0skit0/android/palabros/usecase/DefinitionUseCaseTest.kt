package org.m0skit0.android.palabros.usecase

import android.content.Context
import android.content.Intent
import android.net.Uri
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.*

class DefinitionUseCaseTest : StringSpec({

    lateinit var mockContext: Context
    lateinit var mockUri: Uri
    lateinit var mockIntent: Intent
    lateinit var mockUriProvider: (String) -> Uri?
    lateinit var mockIntentProvider: (String, Uri) -> Intent

    beforeEach {
        mockContext = mockk()
        mockUri = mockk()
        mockIntent = mockk()
        mockUriProvider = mockk()
        mockIntentProvider = mockk()
        every { mockUriProvider(any()) } returns mockUri
        every { mockIntent.setFlags(any()) } returns mockIntent
        every { mockIntentProvider(any(), any()) } returns mockIntent
        every { mockContext.startActivity(any()) } just Runs
    }

    "when URI parser returns null should not construct the intent and fail gracefully" {
        definition(mockContext, "blah", { null }, mockIntentProvider)

        verify(inverse = true) {
            mockIntentProvider(any(), any())
            mockContext.startActivity(any())
        }
    }

    "when URI parser is passed URI it should be correct URI" {
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

    "when passed a word it should launch a new activity to browse to the URL" {
        definition(mockContext, "blah", mockUriProvider, mockIntentProvider)

        verify {
            mockUriProvider(any())
            mockIntentProvider(Intent.ACTION_VIEW, any())
            mockIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mockContext.startActivity(mockIntent)
        }
    }
})
