package org.example.habit.presentation.add


import TornadoDeleteContainer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// (Paste the HoldToDeleteContainer composable from above here)

@Composable
fun HoldToDeleteExampleScreen() {
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
@Composable
fun MyItemCard(
    text: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

