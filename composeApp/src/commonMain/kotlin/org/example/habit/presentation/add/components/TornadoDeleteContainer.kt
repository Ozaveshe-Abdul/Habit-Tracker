//package org.example.habit.presentation.add


// REMOVE this import, it's not needed and was part of the problem
// import androidx.compose.runtime.rememberCoroutineScope
// ADD this import

// (Your Particle data class and generateParticles function go here, unchanged)

// Your imports here...


// (Your Particle data class and generateParticles function go here, unchanged)
// (Make sure they have the 'kotlin.math.PI' and 'Random.Default' fixes)

/**
 * A container that triggers an action after a 3second long press.
 * Shows a "tornado" or "junk cleaner" particle vortex effect during the press.
 *
 * THIS IS THE FINAL WORKING VERSION
 *
 * @param modifier The modifier to be applied to the container Box.
 * @param onDelete A lambda that is invoked when the 3-second press is completed.
 * @param content The content to display inside the container (e.g., your Card).
 */
// ... (All your imports are correct) ...
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.habit.presentation.add.Particle
import org.example.habit.presentation.add.generateParticles
import org.example.habit.presentation.add.toRadians
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

// ... (rest of your imports) ...

@Composable
fun TornadoDeleteContainer(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    val progress = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }

    var pressJob by remember { mutableStateOf<Job?>(null) }
    val particleColor = MaterialTheme.colorScheme.tertiary
    var particleList by remember { mutableStateOf<List<Particle>>(emptyList()) }

    // *** ADD THIS LINE BACK ***
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .pointerInput(Unit) { // 'this' is a CoroutineScope

                while (true) {
                    // 1. Wait for a press
                    awaitPointerEventScope {
                        awaitFirstDown(requireUnconsumed = false)
                    }

                    // 2. We are back in the main scope.
                    pressJob?.cancel() // Cancel any previous (unlikely) job

                    // Generate new particles
                    val maxRadius = min(size.width, size.height) / 2f
                    particleList = generateParticles(100, maxRadius, particleColor)
                    // 3. *** THIS IS THE FIX ***
                    // Launch from the 'remember'ed scope
                    pressJob = coroutineScope.launch {
                        delay(200L)
                        coroutineScope {
                            launch {
                                progress.animateTo(
                                    targetValue = 1f,
                                    animationSpec = tween(
                                        durationMillis = 1000, // Using your time
                                        easing = LinearEasing
                                    )
                                )
                            }
                            launch {
                                rotation.animateTo(
                                    targetValue = 3600f,
                                    animationSpec = tween(
                                        durationMillis = 1500, // Using your time
                                        easing = LinearEasing
                                    )
                                )
                            }
                        }
                        // This code only runs if the animations *completed*
                        onDelete()
                    } // End of the 'pressJob' launch

                    // 4. Wait for the finger to be lifted.
                    awaitPointerEventScope {
                        waitForUpOrCancellation()
                    }

                    // 5. We are back in the main scope. Handle the release.
                    pressJob?.cancel()
                    pressJob?.join()

                    // 6. *** THIS IS THE OTHER FIX ***
                    // Also launch from the 'remember'ed scope
                    coroutineScope.launch {
                        progress.snapTo(0f)
                        rotation.snapTo(0f)
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        // ... (Canvas drawing code is unchanged and correct) ...
        content()

        if (progress.value > 0f) {
            val currentProgress = progress.value
            val currentRotation = rotation.value
            val strokeWidth = (4.dp * (1f - currentProgress)).value

            Canvas(modifier = Modifier.fillMaxSize()) {
                val centerX = size.width / 2
                val centerY = size.height / 2
                val center = Offset(centerX, centerY)
                val coreRadius = (20.dp * currentProgress).toPx()

                drawCircle(
                    color = particleColor,
                    radius = coreRadius,
                    center = center
                )
                drawCircle(
                    color = particleColor.copy(alpha = 0.5f),
                    radius = coreRadius + (15.dp.toPx() * currentProgress),
                    center = center,
                    style = Stroke(width = strokeWidth.dp.toPx())
                )

                particleList.forEach { particle ->
                    val radiusProgress = (1f - currentProgress)
                    val animatedRadius = particle.initialRadius * radiusProgress
                    val animatedAngle = particle.initialAngle + (currentRotation * particle.speedFactor)
                    val animatedSize = particle.size.toPx() * radiusProgress
                    val animatedAlpha = particle.color.alpha * radiusProgress

                    val x = centerX + animatedRadius * cos(animatedAngle.toRadians())
                    val y = centerY + animatedRadius * sin(animatedAngle.toRadians())

                    drawCircle(
                        color = particle.color.copy(alpha = animatedAlpha),
                        radius = animatedSize,
                        center = Offset(x, y)
                    )
                }
            }
        }
    }
}

/**
 * A container that triggers an action after a 3second long press.
 * Shows a "tornado" or "junk cleaner" particle vortex effect during the press.
 *
 * @param modifier The modifier to be applied to the container Box.
 * @param onDelete A lambda that is invoked when the 3-second press is completed.
 * @param content The content to display inside the container (e.g., your Card).
 */
@Composable
fun TornadoDeleteContainer2(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    val progress = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }

    var pressJob by remember { mutableStateOf<Job?>(null) }
    val particleColor = MaterialTheme.colorScheme.tertiary
    var particleList by remember { mutableStateOf<List<Particle>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .pointerInput(Unit) { // 'this' is a CoroutineScope
                awaitPointerEventScope {
                    while (true) {
                        // 1. Wait for a press
//                    awaitPointerEventScope {
                        awaitFirstDown(requireUnconsumed = false)


                        // 2. We are back in the main scope.
                        pressJob?.cancel() // Cancel any previous (unlikely) job

                        // Generate new particles
                        val maxRadius = min(size.width, size.height) / 2f
                        particleList = generateParticles(100, maxRadius, particleColor)

                        // 3. Launch the 3-second animation job
                        pressJob = coroutineScope.launch {
                            delay(150L)
                            launch {
                                progress.animateTo(
                                    targetValue = 1f,
                                    animationSpec = tween(
                                        durationMillis = 1500,
                                        easing = LinearEasing
                                    )
                                )
                            }
                            launch {
                                rotation.animateTo(
                                    targetValue = 3600f,
                                    animationSpec = tween(
                                        durationMillis = 1500,
                                        easing = LinearEasing
                                    )
                                )
                            }
                            // *** IMPORTANT FIX ***
                            // If we get here, it means the animations *completed* without
                            // being cancelled. This is the only place onDelete should be.
                            if (progress.value == 1f) {
                                onDelete()
                            }
                        }

                        // 4. Wait for the finger to be lifted
//                        awaitPointerEventScope {
                        waitForUpOrCancellation()
//                        }

                        // 5. We are back in the main scope. Handle the release.

                        // *** IMPORTANT FIX ***
                        // Request cancellation. This is non-blocking.
                        pressJob?.cancel()
                        // Wait for the job to be fully "Cancelled". This IS blocking.
                        if (progress.value < 1f) {
                            coroutineScope.launch {
                                pressJob?.join()

                                // 6. *Now* it is 100% safe to snap back.
                                // 'snapTo' is a suspend function, so it's fine to call directly
                                // inside this coroutine scope. No extra 'launch' needed.

                                    progress.snapTo(0f)
                                    rotation.snapTo(0f)
                            }
                        }
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        // Your actual UI content (the card) - remains unmodified
        content()

        // The "Tornado" overlay (This part is unchanged)
        if (progress.value > 0f) {
            val currentProgress = progress.value
            val currentRotation = rotation.value
            val strokeWidth = (4.dp * (1f - currentProgress)).value

            Canvas(modifier = Modifier.fillMaxSize()) {
                val centerX = size.width / 2
                val centerY = size.height / 2
                val center = Offset(centerX, centerY)
                val coreRadius = (20.dp * currentProgress).toPx()

                drawCircle(
                    color = particleColor,
                    radius = coreRadius,
                    center = center
                )
                drawCircle(
                    color = particleColor.copy(alpha = 0.5f),
                    radius = coreRadius + (15.dp.toPx() * currentProgress),
                    center = center,
                    style = Stroke(width = strokeWidth.dp.toPx())
                )

                particleList.forEach { particle ->
                    val radiusProgress = (1f - currentProgress)
                    val animatedRadius = particle.initialRadius * radiusProgress
                    val animatedAngle = particle.initialAngle + (currentRotation * particle.speedFactor)
                    val animatedSize = particle.size.toPx() * radiusProgress
                    val animatedAlpha = particle.color.alpha * radiusProgress

                    val x = centerX + animatedRadius * cos(animatedAngle.toRadians())
                    val y = centerY + animatedRadius * sin(animatedAngle.toRadians())

                    drawCircle(
                        color = particle.color.copy(alpha = animatedAlpha),
                        radius = animatedSize,
                        center = Offset(x, y)
                    )
                }
            }
        }
    }
}
/**
 * A container that triggers an action after a 3second long press.
 * Shows a "tornado" or "junk cleaner" particle vortex effect during the press.
 *
 * @param modifier The modifier to be applied to the container Box.
 * @param onDelete A lambda that is invoked when the 3-second press is completed.
 * @param content The content to display inside the container (e.g., your Card).
 */

/*

@Composable
fun TornadoDeleteContainer(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    // Animatable for overall progress (0f to 1f) - drives the "sucking in"
    val progress = remember { Animatable(0f) }
    // Animatable for rotation (degrees) - drives the "spinning"
    val rotation = remember { Animatable(0f) }

    var pressJob by remember { mutableStateOf<Job?>(null) }
    val particleColor = MaterialTheme.colorScheme.primary

    // This state will hold our list of particles
    var particleList by remember { mutableStateOf<List<Particle>>(emptyList()) }
    val density = LocalDensity.current

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        awaitFirstDown(requireUnconsumed = false)

                        pressJob?.cancel() // Cancel any previous job

                        // When pressed, generate a new set of particles based on the component size
                        val maxRadius = min(size.width, size.height) / 2f
                        particleList = generateParticles(100, maxRadius, particleColor)

                        // Launch animation job
                        pressJob = coroutineScope.launch {
                            // Reset state for new press
                            progress.snapTo(0f)
                            rotation.snapTo(0f)

                            // Animate progress and rotation simultaneously
                            launch {
                                progress.animateTo(
                                    targetValue = 1f,
                                    animationSpec = tween(
                                        durationMillis = 3000,
                                        easing = LinearEasing
                                    )
                                )
                            }
                            launch {
                                rotation.animateTo(
                                    // Spin multiple times
                                    targetValue = 3600f, // 10 full rotations
                                    animationSpec = tween(
                                        durationMillis = 3000,
                                        easing = LinearEasing
                                    )
                                )
                            }
                        }

                        // Wait for the job to finish (or be cancelled)
                        pressJob?.join()

                        // If animation completed, trigger delete
                        if (progress.value == 1f) {
                            onDelete()
                        }

                        // Wait for the finger to be lifted
                        waitForUpOrCancellation()

                        // Finger was lifted, cancel the animation job
                        pressJob?.cancel()

                        // Snap back to initial state
                        coroutineScope.launch {
                            progress.snapTo(0f)
                            rotation.snapTo(0f)
                        }
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        // Your actual UI content (the card) - remains unmodified
        content()

        // The "Tornado" overlay
        if (progress.value > 0f) {
            val currentProgress = progress.value
            val currentRotation = rotation.value
            val strokeWidth = (4.dp * (1f - currentProgress)).value // Stroke gets thinner

            Canvas(modifier = Modifier.fillMaxSize()) {
                val centerX = size.width / 2
                val centerY = size.height / 2
                val center = Offset(centerX, centerY)

                // 1. Draw the central "vortex" core
                val coreRadius = (20.dp * currentProgress).toPx() // Core grows as it sucks
                drawCircle(
                    color = particleColor,
                    radius = coreRadius,
                    center = center
                )
                // Draw the outer ring of the vortex
                drawCircle(
                    color = particleColor.copy(alpha = 0.5f),
                    radius = coreRadius + (15.dp.toPx() * currentProgress),
                    center = center,
                    style = Stroke(width = strokeWidth.dp.toPx())
                )

                // 2. Draw all the particles
                particleList.forEach { particle ->
                    // Progress (0 to 1) determines how "sucked in" the particle is
                    // (1f - currentProgress) goes from 1 to 0
                    val radiusProgress = (1f - currentProgress)

                    // Current distance from center
                    val animatedRadius = particle.initialRadius * radiusProgress
                    // Current angle
                    val animatedAngle = particle.initialAngle + (currentRotation * particle.speedFactor)
                    // Particles shrink and fade as they get closer to the center
                    val animatedSize = particle.size.toPx() * radiusProgress
                    val animatedAlpha = particle.color.alpha * radiusProgress

                    // Convert polar (radius, angle) to cartesian (x, y) coordinates
                    val x = centerX + animatedRadius * cos(animatedAngle.toRadians())
                    val y = centerY + animatedRadius * sin(animatedAngle.toRadians())

                    drawCircle(
                        color = particle.color.copy(alpha = animatedAlpha),
                        radius = animatedSize,
                        center = Offset(x, y)
                    )
                }
            }
        }
    }
}*/
