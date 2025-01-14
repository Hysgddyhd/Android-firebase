package com.example.makeitso.screens.edit_event

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.makeitso.EVENT_ID
import com.example.makeitso.common.ext.idFromParameter
import com.example.makeitso.model.Event
import com.example.makeitso.model.service.LogService
import com.example.makeitso.model.service.StorageService
import com.example.makeitso.screens.MakeItSoViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class EditEventViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    logService: LogService,
    private val storageService: StorageService,
) : MakeItSoViewModel(logService) {
    val event = mutableStateOf(Event())

    init {
        val eventId = savedStateHandle.get<String>(EVENT_ID)
        if (eventId != null) {
            launchCatching {
                event.value = storageService.getEvent(eventId.idFromParameter()) ?: Event()
            }
        }
    }

    fun onTitleChange(newValue: String) {
        event.value = event.value.copy(title = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        event.value = event.value.copy(description = newValue)
    }

    fun onLocationChange(newValue: String) {
        event.value = event.value.copy(location = newValue)
    }

    fun onDateChange(newValue: Long) {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(UTC))
        calendar.timeInMillis = newValue
        //val newStartDate = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(calendar.time)
        event.value = event.value.copy(startTime = calendar.getTime())
    }

    fun onTimeChange(hour: Int, minute: Int) {
        val calendar:Calendar = Calendar.getInstance()
        calendar.set(
            event.value.startTime.year,
            event.value.startTime.month,
            event.value.startTime.date,
            hour,
            minute)
        //val newStartDate = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(calendar.time)
        event.value = event.value.copy(startTime = calendar.getTime())
    }

    fun onPeriodChange(hour: Int, minute: Int) {
        val newDueTime = hour*60+ minute
        event.value = event.value.copy(period = newDueTime)
    }

    fun onFlagToggle(newValue: String) {
        val newFlagOption = EditFlagOption.getBooleanValue(newValue)
        event.value = event.value.copy(flag = newFlagOption)
    }

    fun onPriorityChange(newValue: String) {
        event.value = event.value.copy(priority = newValue)
    }

    fun onDoneClick(popUpScreen: () -> Unit) {
        launchCatching {
            val editedEvent = event.value
            if (editedEvent.id.isBlank()) {
                storageService.saveEvent(editedEvent)
            } else {
                storageService.updateEvent(editedEvent)
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