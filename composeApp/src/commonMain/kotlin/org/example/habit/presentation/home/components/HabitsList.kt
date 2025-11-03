package org.example.habit.presentation.home.components

import TornadoDeleteContainer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.habit.domain.models.Habit

@Composable
fun HabitsList(habits: List<Habit>, onDeleteClick: (Long) -> Unit, onCheck: (Habit) -> Unit, modifier: Modifier = Modifier) {
//    val list = remember { mutableStateOf(habits) }

    LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

        items(habits, key = {it.id}) { habit ->
            TornadoDeleteContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(), // Animate list changes
                onDelete = {
                    // Remove the item from the list
//                    habits -= habit
                    onDeleteClick(habit.id)
                }
            ) {
                // This is the content that will be displayed
                HabitCard(
                    habit = habit,
                    onClick = { onCheck(habit) }
                )
            }
        }

    }
}