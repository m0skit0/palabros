package org.m0skit0.android.palabros.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.m0skit0.android.palabros.di.NAMED_ON_KEY_CLICKED_USE_CASE
import org.m0skit0.android.palabros.di.NAMED_PLAY_GRID_STATE_FLOW
import org.m0skit0.android.palabros.di.NAMED_RANDOM_WORD_USE_CASE
import org.m0skit0.android.palabros.state.PlayGridState
import org.m0skit0.android.palabros.usecase.OnKeyClickedUseCase
import org.m0skit0.android.palabros.usecase.RandomWordUseCase

@ExperimentalFoundationApi
class MainActivity : ComponentActivity(), KoinComponent {

    private val onKeyClickedUseCase: OnKeyClickedUseCase by inject(NAMED_ON_KEY_CLICKED_USE_CASE)
    private val randomWordUseCase: RandomWordUseCase by inject(NAMED_RANDOM_WORD_USE_CASE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO Refactor outside of activity
        lifecycleScope.launch {
            setContent {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
            randomWordUseCase().let { secretWord ->
                get<MutableStateFlow<PlayGridState>>(NAMED_PLAY_GRID_STATE_FLOW).let { state ->
                    state.value = state.value.copy(secretWord = secretWord)
                }
            }
            setContent {
                PlayGrid(onKeyClick = onKeyClickedUseCase)
            }
        }
    }
}
