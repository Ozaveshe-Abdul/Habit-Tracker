package org.example.habit.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.habit.domain.models.Habit
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
@Composable
fun HabitCard(habit: Habit, onClick: () -> Unit, modifier: Modifier = Modifier) {

    // 1. Wrap everything in a Box. Apply the modifier here.
    Box(
        modifier = modifier
    ) {
        // This is your original Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            // I removed your inner `Box` as it wasn't necessary
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceAround,
            ) {

                Text(
                    text = habit.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Column {
                    CheckRow("Yesterday ", false) {
                        IconButton(onClick = {}) { // icon button is added to achieve alignment with the checkbox from its built in padding
                            Box(
                                modifier = Modifier.size(30.dp)
                                    .border(1.dp, Color.Gray, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (habit.streakCount == 0) "\uD83D\uDE11" else "\uD83D\uDE42",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = if (habit.streakCount == 0) 25.sp else 20.sp,
                                    color = if (habit.streakCount == 0) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                    CheckRow("Today") {
                        Checkbox(
                            checked = habit.isDone,
                            onCheckedChange = { onClick() },
                            enabled = true
                        )
                    }
                }

                Row(
                    // Note: Use Modifier, not the 'modifier' parameter from the function
                    modifier = Modifier.fillMaxWidth().padding(end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "⚡streak⚡ ⚡ ${habit.streakCount}",
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "highest streak ${habit.highestStreak}",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // 2. Add the Icon here, as a sibling to the Card
        // It will only show if the habit is done
        if (habit.isDone) {
            val iconSize = 40.dp
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "Completed",
                tint = Color(0xFF00C853), // Bright green
                modifier = Modifier
                    .size(iconSize)
                    .align(Alignment.TopEnd) // 3. Align to the top-right of the Box
                    .offset( // 4. Nudge it 50% out in each direction
                        x = (iconSize / 2 - 4.dp),
                        y = -(iconSize / 2 - 4.dp)
                    )
            )
        }
    }
}
@Composable
fun CheckRow(text: String, enabled: Boolean = true, modifier: Modifier = Modifier, content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text, color = if (!enabled) Color.Gray else MaterialTheme.colorScheme.onSurface)
        content()
//        Checkbox(
//            checked = isChecked,
//            onCheckedChange = { onClick() },
//            enabled = enabled
//        )

    }
}

//val habits = listOf(
//    Habit(
//        5,
//        name = "Waking up",
//        streakCount = 15,
//        highestStreak = 15,
//        isDone = true
//    ),
//    Habit(1,"Night Rest", 14, 14, false),
//    Habit(2,"Rest", 12, 14, true),
//    Habit(3,"Reading", 0, 5, false),
//    Habit(4,"School", 14, 14, false)
//)