package com.example.makeitso.screens.events

enum class EventActionOption(val title: String) {
    EditEvent("Edit event"),
    ToggleFlag("Toggle flag"),
    DeleteEvent("Delete event");

    companion object {
        fun getByTitle(title: String): EventActionOption {
            EventActionOption.values().forEach { action -> if (title == action.title) return action }

            return EditEvent
        }

        fun getOptions(hasEditOption: Boolean): List<String> {
            val options = mutableListOf<String>()
            EventActionOption.values().forEach { taskAction ->
                if (hasEditOption || taskAction != EditEvent) {
                    options.add(taskAction.title)
                }
            }
            return options
        }
    }
}


