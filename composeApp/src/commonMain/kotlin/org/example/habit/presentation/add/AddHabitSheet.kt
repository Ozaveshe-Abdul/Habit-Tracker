package org.example.habit.presentation.add

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitSheet(uiState: AddUiState, onDismiss: () -> Unit, onSaveClicked: () -> Unit, onValueChange: (String) -> Unit ) {
//    var showBottomSheet by remember { mutableStateOf(false) }
//    val value = remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState( skipPartiallyExpanded = false)
        if (uiState.isShowBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight().fillMaxWidth(),
                sheetState = sheetState,
                onDismissRequest = { onDismiss() },
                containerColor = MaterialTheme.colorScheme.outline
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "Swipe up to open sheet. Swipe down to dismiss.",
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    TextField(
                        value = uiState.habitName,
                        onValueChange = { onValueChange(it) },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .clip(MaterialTheme.shapes.medium)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                                shape = MaterialTheme.shapes.medium
                            )
                            .background(MaterialTheme.colorScheme.surface),
                        placeholder = {
                            Text(
                                text = "Enter habit name",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        singleLine = true,
//                        colors = TextFieldDefaults.colors(
//                            containerColor = MaterialTheme.colorScheme.surface,
//                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
//                            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
//                            cursorColor = MaterialTheme.colorScheme.primary,
//                            textColor = MaterialTheme.colorScheme.onSurface
//                        ),
                        shape = MaterialTheme.shapes.medium,
                    )

                    Button(
                        onClick = { onSaveClicked() },
                        shape = CircleShape,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Text("Add Habit")
                    }
                }

            }
    }

}
//
//@Composable
//fun MyTextField(){
//    TextField(
//        value = uiState.habitName,
//        onValueChange = { onValueChange(it) },
//        modifier = Modifier
//            .fillMaxWidth(0.6f)
//            .clip(MaterialTheme.shapes.medium)
//            .border(
//                width = 1.dp,
//                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
//                shape = MaterialTheme.shapes.medium
//            )
//            .background(MaterialTheme.colorScheme.surface),
//        placeholder = {
//            Text(
//                text = "Enter habit name",
//                color = MaterialTheme.colorScheme.onSurfaceVariant
//            )
//        },
//        singleLine = true,
//        colors = TextFieldDefaults.textFieldColors(
//            containerColor = MaterialTheme.colorScheme.surface,
//            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
//            unfocusedIndicatorColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
//            cursorColor = MaterialTheme.colorScheme.primary,
//            textColor = MaterialTheme.colorScheme.onSurface
//        ),
//        shape = MaterialTheme.shapes.medium,
//    )
//
//}