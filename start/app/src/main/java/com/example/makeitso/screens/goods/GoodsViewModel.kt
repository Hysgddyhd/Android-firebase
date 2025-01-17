package com.example.makeitso.screens.goods

import androidx.compose.runtime.mutableStateOf
import com.example.makeitso.EDIT_EVENT_SCREEN
import com.example.makeitso.EDIT_GOOD_SCREEN
import com.example.makeitso.GOOD_ID
import com.example.makeitso.SETTINGS_SCREEN
import com.example.makeitso.model.Good
import com.example.makeitso.model.service.ConfigurationService
import com.example.makeitso.model.service.LogService
import com.example.makeitso.model.service.StorageService
import com.example.makeitso.screens.MakeItSoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
    private val configurationService: ConfigurationService
): MakeItSoViewModel(logService){
    val options = mutableStateOf<List<String>>(listOf())

    val goods = storageService.goods


    fun loadGoodOptions() {
        val hasEditOption=configurationService.isShowEventEditButtonConfig
        options.value=GoodActionOption.getOptions(hasEditOption)
    }

    fun onGoodCheckChange(good: Good) {
        launchCatching { storageService.updateGood(good.copy(addDate = Date().toString())) }
    }

    fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_GOOD_SCREEN)

    fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

    fun onGoodActionClick(openScreen: (String) -> Unit, good: Good, action: String) {
        when (GoodActionOption.getByTitle(action)) {
            GoodActionOption.EditGood -> openScreen("$EDIT_GOOD_SCREEN?$GOOD_ID={${good.id}}")
            GoodActionOption.ToggleFlag -> {}
            GoodActionOption.DeleteGood -> onDeleteEventClick(good)
        }
    }

    private fun onDeleteEventClick(good: Good) {
        launchCatching { storageService.deleteGood(good.id) }
    }

}
