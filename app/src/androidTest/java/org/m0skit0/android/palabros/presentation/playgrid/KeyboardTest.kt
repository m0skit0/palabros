package org.m0skit0.android.palabros.presentation.playgrid

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.m0skit0.android.palabros.state.PlayGridState

@ExperimentalFoundationApi
class KeyboardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun checkClickingOnEveryButtonCallsListener() {
        var clickedChar = ' '
        val mutableFlow: MutableStateFlow<PlayGridState> = MutableStateFlow(value = PlayGridState())
        composeTestRule.setContent {
            Keyboard(
                playGridState = mutableFlow,
                onKeyClick = { clickedChar = it }
            )
        }

        composeTestRule.onNodeWithText(text = "a", ignoreCase = true).performClick()
        assert(clickedChar == 'a')

        assert(mutableFlow.value.grid.first().first() == 'a')
    }
}
