package org.m0skit0.android.palabros.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@ExperimentalFoundationApi
class MainActivity : ComponentActivity(), KoinComponent {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            get<PlayGrid>()()
        }
    }
}
