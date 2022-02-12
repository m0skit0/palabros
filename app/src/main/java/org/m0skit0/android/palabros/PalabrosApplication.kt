package org.m0skit0.android.palabros

import android.app.Application
import androidx.compose.foundation.ExperimentalFoundationApi
import org.m0skit0.android.palabros.di.initializeKoin

@ExperimentalFoundationApi
class PalabrosApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }
}
