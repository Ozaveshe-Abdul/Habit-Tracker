package org.example.habit.presentation.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.habit.domain.models.Habit

@Composable
fun HabitCard(habit: Habit, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(shape = RoundedCornerShape(20.dp), border = BorderStroke(1.dp, Color.Gray)) {
        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer).padding(16.dp),
            verticalArrangement = Arrangement.SpaceAround,

        ) {
            Text(
                text = habit.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
//                fontStyle = MaterialTheme.typography.displayMedium
            )
            CheckRow("Yesterday", isChecked = habit.streakCount > 0, false, onClick = { })
            CheckRow("Today", isChecked = habit.isDone,true, onClick = onClick)
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Streak Count ${habit.streakCount}",
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = "highest streak ${habit.highestStreak}",
                    color = Color.Green
                )
            }
        }
    }
}

@Composable
fun CheckRow(text: String, isChecked: Boolean, enabled: Boolean, onClick :() -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text, color = if (!enabled) Color.Gray else MaterialTheme.colorScheme.onSurface)
        Checkbox(
            checked = isChecked,
            onCheckedChange = { onClick() },
            enabled = enabled
        )

    }
}

val habits = listOf(
    Habit(
        name = "Waking up",
        streakCount = 15,
        highestStreak = 15,
        isDone = true
    ),
    Habit("Night Rest", 14, 14, false),
    Habit("Rest", 12, 14, true),
    Habit("Reading", 0, 5, false),
    Habit("School", 14, 14, false)
)