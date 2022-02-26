package org.m0skit0.android.palabros.presentation.playgrid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.m0skit0.android.palabros.R
import org.m0skit0.android.palabros.di.NAMED_ABANDON_USE_CASE
import org.m0skit0.android.palabros.di.NAMED_PLAY_GRID_STATE_FLOW
import org.m0skit0.android.palabros.di.koin
import org.m0skit0.android.palabros.presentation.ConfirmationDialog
import org.m0skit0.android.palabros.state.PlayGridState
import org.m0skit0.android.palabros.theme.HelpSymbolColor
import org.m0skit0.android.palabros.usecase.AbandonUseCase

@Preview
@Composable
private fun OptionBarPreview() {
    OptionBar(
        abandonUseCase = {},
        playGridState = MutableStateFlow(PlayGridState())
    )
}

@Composable
fun OptionBar(
    abandonUseCase: AbandonUseCase = koin.get(NAMED_ABANDON_USE_CASE),
    playGridState: Flow<PlayGridState> = koin.get<MutableStateFlow<PlayGridState>>(NAMED_PLAY_GRID_STATE_FLOW)
) {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        Abandon(abandonUseCase)
        HelpButton()
    }
}

@Composable
private fun Abandon(
    abandonUseCase: AbandonUseCase = koin.get(NAMED_ABANDON_USE_CASE)
) {
    val showConfirmation = remember { mutableStateOf(false) }
    val isConfirmed = remember { mutableStateOf(false) }
    Button(
        modifier = Modifier.padding(5.dp),
        onClick = { showConfirmation.value = true }
    ) {
        Text(
            style = MaterialTheme.typography.button,
            color = HelpSymbolColor,
            text = stringResource(R.string.abandon_question)
        )
    }
    if (showConfirmation.value) {
        ConfirmationDialog(text = R.string.abandon_confirmation, onCancel = {
            showConfirmation.value = false
        }) {
            showConfirmation.value = false
            isConfirmed.value = true
        }
    }
    if (isConfirmed.value) {
        abandonUseCase()
        isConfirmed.value = false
    }
}

@Composable
private fun HelpButton() {
    val isDialogOpen = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Button(
            modifier = Modifier.padding(5.dp),
            onClick = { isDialogOpen.value = true },
        ) {
            Text(
                style = MaterialTheme.typography.button,
                color = HelpSymbolColor,
                text = stringResource(R.string.help_symbol)
            )
        }
    }
    if (isDialogOpen.value) {
        HelpDialog {
            isDialogOpen.value = false
        }
    }
}
