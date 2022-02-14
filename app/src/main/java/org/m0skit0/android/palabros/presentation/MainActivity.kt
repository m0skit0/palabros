package org.m0skit0.android.palabros.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.m0skit0.android.palabros.di.NAMED_ON_KEY_CLICKED_USE_CASE
import org.m0skit0.android.palabros.usecase.OnKeyClickedUseCase

@ExperimentalFoundationApi
class MainActivity : ComponentActivity(), KoinComponent {

    private val onKeyClickedUseCase: OnKeyClickedUseCase by inject(NAMED_ON_KEY_CLICKED_USE_CASE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlayGrid(onKeyClick = onKeyClickedUseCase)
        }
    }
}
