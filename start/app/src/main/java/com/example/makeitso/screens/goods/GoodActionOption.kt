package com.example.makeitso.screens.goods

enum class GoodActionOption(val title: String) {
    EditGood("Edit good"),
    ToggleFlag("Toggle flag"),
    DeleteGood("Delete good");

    companion object {
        fun getByTitle(title: String): GoodActionOption {
            GoodActionOption.values().forEach { action -> if (title == action.title) return action }

            return EditGood
        }

        fun getOptions(hasEditOption: Boolean): List<String> {
            val options = mutableListOf<String>()
            GoodActionOption.values().forEach { taskAction ->
                if (hasEditOption || taskAction != EditGood) {
                    options.add(taskAction.title)
                }
            }
            return options
        }
    }
}


