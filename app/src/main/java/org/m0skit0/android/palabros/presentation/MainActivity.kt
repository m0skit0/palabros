package org.m0skit0.android.palabros.presentation

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.lifecycleScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.m0skit0.android.palabros.di.NAMED_ON_KEY_CLICKED_USE_CASE
import org.m0skit0.android.palabros.di.NAMED_RANDOM_WORD_USE_CASE
import org.m0skit0.android.palabros.presentation.playgrid.PlayGrid
import org.m0skit0.android.palabros.theme.PalabrosTheme
import org.m0skit0.android.palabros.usecase.OnKeyClickedUseCase
import org.m0skit0.android.palabros.usecase.OnLongKeyClickedUseCase
import org.m0skit0.android.palabros.usecase.RandomWordUseCase

@ExperimentalFoundationApi
class MainActivity : ComponentActivity(), KoinComponent {

    private val onKeyClickedUseCase: OnKeyClickedUseCase by inject(NAMED_ON_KEY_CLICKED_USE_CASE)
    private val onLongKeyClickedUseCase: OnLongKeyClickedUseCase by inject(NAMED_ON_KEY_CLICKED_USE_CASE)
    private val randomWordUseCase: RandomWordUseCase by inject(NAMED_RANDOM_WORD_USE_CASE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // TODO
        lifecycleScope.launchWhenCreated { randomWordUseCase() }
        setContent {
            PalabrosTheme {
                PlayGrid(
                    onKeyClick = onKeyClickedUseCase,
                    onLongKeyClick = onLongKeyClickedUseCase
                )
            }
        }
    }
}
