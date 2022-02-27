package org.m0skit0.android.palabros.presentation.playgrid

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.m0skit0.android.palabros.R

@Preview
@Composable
fun LostSnackbarPreview() {
    LostSnackbar(
        secretWord = "roble",
        onTryAgain = {},
        onDefinition = {}
    )
}

@Preview
@Composable
fun WinSnackbarPreview() {
    WinSnackbar(
        onTryAgain = {},
        onDefinition = {}
    )
}

@Composable
fun LostSnackbar(
    secretWord: String,
    onTryAgain: () -> Unit,
    onDefinition: () -> Unit
) {
    PlayGridSnackbar(
        onTryAgain = onTryAgain,
        onDefinition = onDefinition,
        text = R.string.lost,
        values = arrayOf(secretWord),
    )
}

@Composable
fun WinSnackbar(
    onTryAgain: () -> Unit,
    onDefinition: () -> Unit
) {
    PlayGridSnackbar(
        onTryAgain = onTryAgain,
        onDefinition = onDefinition,
        text = R.string.win
    )
}

@Composable
private fun PlayGridSnackbar(
    onTryAgain: () -> Unit,
    onDefinition: () -> Unit,
    @StringRes text: Int,
    vararg values: String,
) {
    SnackbarBox {
        Snackbar(
            action = {
                ActionButtons(
                    onTryAgain = onTryAgain,
                    onDefinition = onDefinition
                )
            }
        ) {
            Text(text = stringResource(text, *values))
        }
    }
}

@Composable
private fun SnackbarBox(snackbar: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = BottomCenter,
        content = snackbar
    )
}

@Composable
private fun ActionButtons(
    onTryAgain: () -> Unit,
    onDefinition: () -> Unit,
) {
    Row {
        Button(
            modifier = Modifier.padding(end = 5.dp),
            onClick = onDefinition
        ) {
            Text(text = stringResource(R.string.definition))
        }
        Button(onClick = onTryAgain) {
            Text(text = stringResource(R.string.again))
        }
    }
}
