package org.m0skit0.android.palabros.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.m0skit0.android.palabros.R
import org.m0skit0.android.palabros.theme.PalabrosTheme

@Composable
fun CardDialog(onDismissRequest: () -> Unit, content: @Composable () -> Unit) {
    PalabrosTheme {
        Dialog(onDismissRequest = onDismissRequest) {
            Card(
                elevation = 8.dp,
                shape = RoundedCornerShape(10.dp),
                backgroundColor = MaterialTheme.colors.primary,
                content = content
            )
        }
    }
}

@Preview
@Composable
private fun ConfirmationDialogPreview() {
    ConfirmationDialogContent(text = R.string.abandon_confirmation, onCancel = {}) {}
}

@Composable
fun ConfirmationDialog(@StringRes text: Int, onCancel: () -> Unit, onOk: () -> Unit) {
    CardDialog(onDismissRequest = onCancel) {
        ConfirmationDialogContent(text = text, onCancel = onCancel, onOk = onOk)
    }
}

@Composable
private fun ConfirmationDialogContent(
    @StringRes text: Int,
    onCancel: () -> Unit,
    onOk: () -> Unit
) {
    Column(
        modifier = Modifier.padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(modifier = Modifier.padding(5.dp), text = stringResource(id = text))
        Row(modifier = Modifier.align(Alignment.End)) {
            PalabrosButton(onClick = onCancel) {
                Text(text = stringResource(id = android.R.string.cancel))
            }
            PalabrosButton(onClick = onOk) {
                Text(text = stringResource(id = android.R.string.ok))
            }
        }
    }
}
