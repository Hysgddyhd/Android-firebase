package com.example.makeitso.screens.events

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText
import com.example.makeitso.common.composable.ActionToolbar
import com.example.makeitso.common.ext.smallSpacer
import com.example.makeitso.common.ext.toolbarActions
import com.example.makeitso.model.Event
import com.example.makeitso.theme.MakeItSoTheme
import kotlinx.coroutines.flow.Flow

@Composable
@ExperimentalMaterialApi
fun EventsScreen(
    openScreen: (String) -> Unit,
    viewModel: EventsViewModel = hiltViewModel()
) {
    val options by viewModel.options
    val events=viewModel
        .events
        .collectAsStateWithLifecycle(emptyList())
    EventsScreenContent(
        onAddClick = viewModel::onAddClick,
        onSettingsClick = viewModel::onSettingsClick,
        onEventCheckChange = viewModel::onEventCheckChange,
        onEventActionClick = viewModel::onEventActionClick,
        openScreen = openScreen,
        events = events,
        options=options
    )

    LaunchedEffect(viewModel) { viewModel.loadEventOptions() }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@ExperimentalMaterialApi
fun EventsScreenContent(
    modifier: Modifier = Modifier,
    onAddClick: ((String) -> Unit) -> Unit,
    onSettingsClick: ((String) -> Unit) -> Unit,
    onEventCheckChange: (Event) -> Unit,
    onEventActionClick: ((String) -> Unit, Event, String) -> Unit,
    openScreen: (String) -> Unit,
    events:State<List<Event>>,
    options:List<String>
) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddClick(openScreen) },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = modifier.padding(16.dp)
            ) {
                Icon(Icons.Filled.Add, "Add")
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            ActionToolbar(
                title = AppText.events,
                modifier = Modifier.toolbarActions(),
                endActionIcon = AppIcon.ic_settings,
                endAction = { onSettingsClick(openScreen) }
            )

            Spacer(modifier = Modifier.smallSpacer())

            LazyColumn {
                items(events.value, key = { it.id }) { eventItem ->
                    EventItem(
                        event = eventItem,
                        options = options,
                        onActionClick = { action -> onEventActionClick(openScreen, eventItem, action) }
                    )
                }
            }
        }
    }
}

