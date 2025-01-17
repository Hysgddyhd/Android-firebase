package com.example.makeitso.screens.edit_good

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.makeitso.GOOD_ID
import com.example.makeitso.common.ext.idFromParameter
import com.example.makeitso.model.Good
import com.example.makeitso.model.service.LogService
import com.example.makeitso.model.service.StorageService
import com.example.makeitso.screens.MakeItSoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EditGoodViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    logService: LogService,
    private val storageService: StorageService,
) : MakeItSoViewModel(logService) {
    val good = mutableStateOf(Good(name="", price = 0))

    init {
        val goodId = savedStateHandle.get<String>(GOOD_ID)
        if (goodId != null) {
            launchCatching {
                good.value = storageService.getGood(goodId.idFromParameter()) ?: Good(name = "", price = 0)
            }
        }
    }

    fun onNameChange(newValue: String) {
        good.value = good.value.copy(name = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        good.value = good.value.copy(description = newValue)
    }


    fun onDoneClick(popUpScreen: () -> Unit) {
        good.value = good.value.copy(addDate = Date())
        launchCatching {
            val editedGood = good.value
            if (editedGood.id.isBlank()) {
                storageService.saveGood(editedGood)
            } else {
                storageService.updateGood(editedGood)
            }
            popUpScreen()
        }
    }

    private fun Int.toClockPattern(): String {
        return if (this < 10) "0$this" else "$this"
    }

    companion object {
        private const val UTC = "UTC"
        private const val DATE_FORMAT = "EEE, d MMM yyyy"
    }
}