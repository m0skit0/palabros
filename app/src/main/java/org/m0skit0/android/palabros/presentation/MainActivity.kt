package org.m0skit0.android.palabros.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.m0skit0.android.palabros.di.NAMED_KEYBOARD

@ExperimentalFoundationApi
class MainActivity : ComponentActivity(), KoinComponent {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                WordGrid()
                get(NAMED_KEYBOARD)
            }
        }
    }
}
