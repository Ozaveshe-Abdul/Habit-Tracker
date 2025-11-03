package org.example.habit.presentation.add.components

/**
 * A container that triggers an action after a 3-second long press.
 * It shows a circular progress indicator overlay during the press.
 *
 * @param modifier The modifier to be applied to the container Box.
 * @param onDelete A lambda that is invoked when the 3-second press is completed.
 * @param content The content to display inside the container (e.g., your Card).
 */

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@Composable
fun HoldToDeleteContainer(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val progress = remember { Animatable(0f) }
    var pressJob by remember { mutableStateOf<Job?>(null) }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        // Wait for a press
                        awaitFirstDown(requireUnconsumed = false)

                        // Cancel any previous job, just in case
                        pressJob?.cancel()

                        // Launch a new job for this press
                        pressJob = coroutineScope.launch {
                            // Animate progress to 1f over 3 seconds
                            progress.animateTo(
                                targetValue = 1f,
                                animationSpec = tween(
                                    durationMillis = 2000,
                                    easing = LinearEasing
                                )
                            )

                            // If animation completed (progress is 1f), trigger delete
                            if (progress.value == 1f) {
                                onDelete()
                            }
                        }

                        // Wait for the finger to be lifted
                        waitForUpOrCancellation()

                        // Finger was lifted, cancel the deletion job
                        pressJob?.cancel()

                        // If the job was cancelled before finishing, snap progress back to 0
                        if (progress.value < 1f) {
                            coroutineScope.launch {
                                progress.snapTo(0f)
                            }
                        }
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        // Your actual UI content (the card)
        content()

        // The progress bar overlay
        if (progress.value > 0f) {
            CircularProgressIndicator(
                progress = { progress.value },
                modifier = Modifier.size(100.dp),
                strokeWidth = 10.dp,
                color = MaterialTheme.colorScheme.error,
                trackColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f)
            )
        }
    }
}