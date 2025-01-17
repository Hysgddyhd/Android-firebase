package com.example.makeitso.screens.edit_good

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import com.example.makeitso.GOOD_ID
import com.example.makeitso.R
import com.example.makeitso.common.ext.idFromParameter
import com.example.makeitso.model.Good
import com.example.makeitso.model.service.LogService
import com.example.makeitso.model.service.StorageService
import com.example.makeitso.screens.MakeItSoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class EditGoodViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    logService: LogService,
    private val storageService: StorageService,
) : MakeItSoViewModel(logService) {
    val good = mutableStateOf(Good())


    init {
        val goodId = savedStateHandle.get<String>(GOOD_ID)
        if (goodId != null) {
            launchCatching {
                good.value = storageService.getGood(goodId.idFromParameter()) ?: Good()
            }
        }
    }

    fun onNameChange(s: String) {
        Log.d("good","name: "+s)
        good.value=good.value.copy(name = s+"")
    }

    fun onPriceChange(d: Double) {
        Log.d("good","price: "+d)
        good.value=good.value.copy(price = d+0.0)
    }

    fun onQuantityChange(i: Int) {
        Log.d("good","quantity: "+i)
        good.value=good.value.copy(quantity = i+0)
    }

    fun onDescriptionChange(newValue: String) {
        Log.d("good","description: "+newValue)
        good.value = good.value.copy(description = newValue)
    }


    fun onDoneClick(popUpScreen: () -> Unit) {
        Log.d("good","good: "+good.toString())
        good.value = good.value.copy(addDate = Date().toString())
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


    private fun validateInput(): Boolean {
        return false
    }

    fun saveItem(popUpScreen: () -> Unit) {
        if (validateInput()) {
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
    }



}

