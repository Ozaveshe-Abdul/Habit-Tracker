package org.example.habit.presentation.add

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitScreen (addHabitViewModel: AddHabitViewModel) {
//    val addViewModel = koinViewModel<AddHabitViewModel>()
    val uiState by addHabitViewModel.uiState.collectAsStateWithLifecycle()
    AddHabitSheet(
        uiState = uiState,
        onDismiss = { addHabitViewModel.actions(AddScreenEvent.DismissBottomSheet) },
        onSaveClicked = {
            addHabitViewModel.actions(
                AddScreenEvent.SaveButtonClick
            )
        },
        onValueChange = { addHabitViewModel.actions(AddScreenEvent.TextFieldInputChange(it)) }
    )
}
   /* val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
            // Screen content
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Show bottom sheet") },
                icon = { *//*Icon(Icons.Filled.Add, contentDescription = "") *//*},
                onClick = {
                    showBottomSheet = true
                }
            )
        }
    ) { contentPadding ->
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                Button(onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }) {
                    Text("Hide bottom sheet")
                }
            }
        }
    }*/


