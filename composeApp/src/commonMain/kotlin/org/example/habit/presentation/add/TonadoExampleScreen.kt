package org.example.habit.presentation.add

import TornadoDeleteContainer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.random.Random

//import kotlin.time.Clock.System

// Helper extension function to convert degrees to radians
fun Float.toRadians(): Float = (this * PI / 180f).toFloat()

/**
 * Data class to hold the state of a single particle in our "tornado".
 */
data class Particle(
    val initialRadius: Float, // The starting distance from the center
    val initialAngle: Float,  // The starting angle in degrees
    val size: Dp,             // The size of the particle
    val color: Color,
    val speedFactor: Float    // A random factor to make particles spin at different speeds
)

/**
 * Generates a list of random particles for the vortex effect.
 *
 * @param count Number of particles to create.
 *A @param maxRadius The maximum distance from the center a particle can start at.
 * @param baseColor The base color for the particles.
 */
fun generateParticles(count: Int, maxRadius: Float, baseColor: Color): List<Particle> {
    val random = Random(Random.nextInt())
    return List(count) {
        Particle(
            // Start particles at varying distances, but not too close to the center
            initialRadius = maxRadius * (random.nextFloat() * 0.7f + 0.3f),
            initialAngle = random.nextFloat() * 360f,
            size = (random.nextFloat() * 4f + 2f).dp, // Size from 2.dp to 6.dp
            color = baseColor.copy(alpha = random.nextFloat() * 0.5f + 0.5f), // Vary alpha
            speedFactor = random.nextFloat() * 0.5f + 0.8f // Speed from 0.8x to 1.3x
        )
    }
}



// (Paste the TornadoDeleteContainer and its helper code/imports from above)

@Composable
fun TornadoExampleScreen() {
    // Remember the list of items
    var items by remember {
        mutableStateOf(List(20) { "Item ${it + 1}" })
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Long press any card for 3 seconds to delete it.",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        items(items, key = { it }) { item ->
            TornadoDeleteContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(), // Animate list changes
                onDelete = {
                    // Remove the item from the list
                    items = items - item
                }
            ) {
                // This is the content that will be displayed
                MyItemCard(text = item)
            }
        }
    }
}

/**
 * A simple card composable to display the item content.
 */
//@Composable
//fun MyItemCard(
//    text: String,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        modifier = modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(100.dp)
//                .padding(16.dp)
//        ) {
//            Text(
//                text = text,
//                style = MaterialTheme.typography.titleLarge
//            )
//        }
//    }
//}