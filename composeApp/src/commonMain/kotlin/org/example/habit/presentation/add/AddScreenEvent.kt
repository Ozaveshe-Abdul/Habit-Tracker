package org.example.habit.presentation.add

sealed interface AddScreenEvent {
    data class TextFieldInputChange(val newString: String) : AddScreenEvent
    data object SaveButtonClick : AddScreenEvent
    data object FloatingButtonClick : AddScreenEvent

    data object DismissBottomSheet : AddScreenEvent
}