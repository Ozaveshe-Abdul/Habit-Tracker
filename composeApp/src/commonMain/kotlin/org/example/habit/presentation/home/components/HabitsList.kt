package org.example.habit.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.habit.domain.models.Habit

@Composable
fun HabitsList(habits: List<Habit>, modifier: Modifier = Modifier) {
    var list = remember { mutableStateOf(habits) }

    LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

        items(list.value) { habit ->
            HabitCard(
                habit,
                onClick = {
                    list.value = list.value.map {
                        if (habit.name == it.name) it.copy(isDone = !habit.isDone) else it
                    }.toMutableList()
                 }
            )
        }

    }
//    FlowRow {
//        Items
//    }
}