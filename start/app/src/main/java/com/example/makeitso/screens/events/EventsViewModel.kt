package com.example.makeitso.screens.events

import androidx.compose.runtime.mutableStateOf
import com.example.makeitso.EDIT_EVENT_SCREEN
import com.example.makeitso.EVENT_ID
import com.example.makeitso.SETTINGS_SCREEN
import com.example.makeitso.model.Event
import com.example.makeitso.model.service.ConfigurationService
import com.example.makeitso.model.service.LogService
import com.example.makeitso.model.service.StorageService
import com.example.makeitso.screens.MakeItSoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val configurationService: ConfigurationService
): MakeItSoViewModel(logService){
    val options = mutableStateOf<List<String>>(listOf())

    val events = storageService.events

    fun loadEventOptions() {
        val hasEditOption=configurationService.isShowEventEditButtonConfig
        options.value=EventActionOption.getOptions(hasEditOption)
    }

    fun onEventCheckChange(event: Event) {
        launchCatching { storageService.updateEvent(event.copy(experied = !event.experied)) }
    }

    fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_EVENT_SCREEN)

    fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

    fun onEventActionClick(openScreen: (String) -> Unit, event: Event, action: String) {
        when (EventActionOption.getByTitle(action)) {
            EventActionOption.EditEvent -> openScreen("$EDIT_EVENT_SCREEN?$EVENT_ID={${event.id}}")
            EventActionOption.ToggleFlag -> onFlagEventClick(event)
            EventActionOption.DeleteEvent -> onDeleteEventClick(event)
        }
    }

    private fun onFlagEventClick(event: Event) {
        launchCatching { storageService.updateEvent(event.copy(flag = !event.flag)) }
    }

    private fun onDeleteEventClick(event: Event) {
        launchCatching { storageService.deleteEvent(event.id) }
    }

}
