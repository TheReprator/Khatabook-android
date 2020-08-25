package com.reprator.khatabook_android.ui.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Text
import androidx.compose.foundation.contentColor
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.launchInComposition
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.reprator.khatabook_android.ui.snackbarAction
import kotlinx.coroutines.delay


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ErrorSnackbar(
    errorMessage: String,
    showError: Boolean = errorMessage.isNotBlank(),
    modifier: Modifier = Modifier,
    onErrorAction: () -> Unit = { },
    onDismiss: () -> Unit = { }
) {
    launchInComposition(showError) {
        delay(timeMillis = 5000L)
        if (showError) {
            onDismiss()
        }
    }
    AnimatedVisibility(
        visible = showError,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        modifier = modifier
    ) {
        Snackbar(
            modifier = Modifier.padding(16.dp),
            text = { Text(errorMessage) },
            action = {
                TextButton(
                    onClick = {
                        onErrorAction()
                        onDismiss()
                    },
                    contentColor = contentColor()
                ) {
                    Text(
                        text = "Ok",
                        color = MaterialTheme.colors.snackbarAction
                    )
                }
            }
        )
    }
}