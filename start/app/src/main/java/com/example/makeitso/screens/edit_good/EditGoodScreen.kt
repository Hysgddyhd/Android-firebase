package com.example.makeitso.screens.edit_good

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.makeitso.common.composable.ActionToolbar
import com.example.makeitso.common.composable.BasicField
import com.example.makeitso.common.composable.CardSelector
import com.example.makeitso.common.composable.RegularCardEditor
import com.example.makeitso.common.ext.card
import com.example.makeitso.common.ext.fieldModifier
import com.example.makeitso.common.ext.spacer
import com.example.makeitso.common.ext.toolbarActions
import com.example.makeitso.model.Good
import com.example.makeitso.model.Priority
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText

@Composable
@ExperimentalMaterialApi
fun EditGoodScreen(
    popUpScreen: () -> Unit,
    viewModel: EditGoodViewModel = hiltViewModel()
) {
    val good by viewModel.good
    val activity = LocalContext.current as AppCompatActivity

    EditEventScreenContent(
        event = event,
        onDoneClick = { viewModel.onDoneClick(popUpScreen) },
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onLocationChange = viewModel::onLocationChange,
        onDateChange = viewModel::onDateChange,
        onTimeChange = viewModel::onTimeChange,
        onPeriodChange = viewModel::onPeriodChange,
        onPriorityChange = viewModel::onPriorityChange,
        onFlagToggle = viewModel::onFlagToggle,
        activity = activity
    )
}

@Composable
@ExperimentalMaterialApi
fun EditEventScreenContent(
    modifier: Modifier = Modifier,
    event: Event,
    onDoneClick: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onDateChange: (Long) -> Unit,
    onTimeChange: (Int, Int) -> Unit,
    onPriorityChange: (String) -> Unit,
    onPeriodChange: (Int, Int) -> Unit,
    onFlagToggle: (String) -> Unit,
    activity: AppCompatActivity?
) {
    Column(
        modifier = modifier.fillMaxWidth().fillMaxHeight().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActionToolbar(
            title = AppText.edit_event,
            modifier = Modifier.toolbarActions(),
            endActionIcon = AppIcon.ic_check,
         //   endAction = { onDoneClick() }
        )

        Spacer(modifier = Modifier.spacer())

        val fieldModifier = Modifier.fieldModifier()
        BasicField(AppText.title, event.title, onTitleChange, fieldModifier)
        BasicField(AppText.description, event.description, onDescriptionChange, fieldModifier)
        BasicField(AppText.location, event.location, onLocationChange, fieldModifier)

        Spacer(modifier = Modifier.spacer())
        CardEditors(event, onDateChange, onTimeChange,onPeriodChange, activity)
        CardSelectors(event, onPriorityChange, onFlagToggle)

        Spacer(modifier = Modifier.spacer())

        Button(onClick = onDoneClick) { Text("Done", color = Color.White, fontSize = 18.sp)}
    }
}

@ExperimentalMaterialApi
@Composable
private fun CardEditors(
    event: Event,
    onDateChange: (Long) -> Unit,
    onTimeChange: (Int, Int) -> Unit,
    activity: AppCompatActivity?
) {
    val startTime=event.startTime.date.toString()+"/"+event.startTime.getMonth().inc()
    RegularCardEditor(AppText.date, AppIcon.ic_calendar, startTime, Modifier.card()) {
        showDatePicker(activity, onDateChange)

    }

    RegularCardEditor(AppText.time, AppIcon.ic_clock, event.period.toString(), Modifier.card()) {
        showTimePicker(activity, onTimeChange)
    }
}

@ExperimentalMaterialApi
@Composable
private fun CardEditors(
    event: Event,
    onDateChange: (Long) -> Unit,
    onTimeChange: (Int, Int) -> Unit,
    onPeriodChange:(Int, Int) -> Unit,
    activity: AppCompatActivity?
) {
    val eventDate=event.startTime.date.toString()+"/"+event.startTime.getMonth().inc()
    val eventTime=event.startTime.hours.toString()+":"+event.startTime.minutes
    RegularCardEditor(AppText.date, AppIcon.ic_calendar, eventDate, Modifier.card()) {
        showDatePicker(activity, onDateChange)

    }
    RegularCardEditor(AppText.time, AppIcon.ic_flag, eventTime, Modifier.card()) {
        showTimePicker(activity, onTimeChange)
    }

    RegularCardEditor(AppText.period, AppIcon.ic_clock, event.period.toString(), Modifier.card()) {
        showTimePicker(activity, onPeriodChange)
    }
}

@Composable
@ExperimentalMaterialApi
private fun CardSelectors(
    good: Good,
    onPriorityChange: (String) -> Unit,
    onFlagToggle: (String) -> Unit
) {
    CardSelector(AppText.priority, Priority.getOptions(), prioritySelection, Modifier.card()) {
            newValue ->
        onPriorityChange(newValue)
    }

    val flagSelection = EditFlagOption.getByCheckedState(event.flag).name
    CardSelector(AppText.flag, EditFlagOption.getOptions(), flagSelection, Modifier.card()) { newValue
        ->
        onFlagToggle(newValue)
    }
}

private fun showDatePicker(activity: AppCompatActivity?, onDateChange: (Long) -> Unit) {
    val picker = MaterialDatePicker.Builder.datePicker().build()

    activity?.let {
        picker.show(it.supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener { timeInMillis -> onDateChange(timeInMillis) }
    }
}

private fun showTimePicker(activity: AppCompatActivity?, onTimeChange: (Int, Int) -> Unit) {
    val picker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()

    activity?.let {
        picker.show(it.supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener { onTimeChange(picker.hour, picker.minute) }
    }
}

