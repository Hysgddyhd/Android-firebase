package com.example.makeitso.screens.goods

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.makeitso.common.composable.ActionToolbar
import com.example.makeitso.common.ext.smallSpacer
import com.example.makeitso.common.ext.toolbarActions
import com.example.makeitso.model.Good
import com.example.makeitso.R.drawable as AppIcon
import com.example.makeitso.R.string as AppText

@Composable
@ExperimentalMaterialApi
fun GoodsScreen(
    openScreen: (String) -> Unit,
    viewModel: EventsViewModel = hiltViewModel()
) {
    val options by viewModel.options
    val goods=viewModel
        .goods
        .collectAsStateWithLifecycle(emptyList())
    GoodsScreenContent(
        onAddClick = viewModel::onAddClick,
        onSettingsClick = viewModel::onSettingsClick,
        onGoodCheckChange = viewModel::onGoodCheckChange,
        onGoodActionClick = viewModel::onGoodActionClick,
        openScreen = openScreen,
        goods = goods,
        options=options
    )

    LaunchedEffect(viewModel) { viewModel.loadGoodOptions() }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@ExperimentalMaterialApi
fun GoodsScreenContent(
    modifier: Modifier = Modifier,
    onAddClick: ((String) -> Unit) -> Unit,
    onSettingsClick: ((String) -> Unit) -> Unit,
    onGoodCheckChange: (Good) -> Unit,
    onGoodActionClick: ((String) -> Unit, Good, String) -> Unit,
    openScreen: (String) -> Unit,
    goods:State<List<Good>>,
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
                title = AppText.goods,
                modifier = Modifier.toolbarActions(),
                endActionIcon = AppIcon.ic_settings,
                endAction = { onSettingsClick(openScreen) }
            )

            Spacer(modifier = Modifier.smallSpacer())

            LazyColumn {
                items(goods.value, key = { it.id }) { goodItem ->
                    GoodItem(
                        good = goodItem,
                        options = options,
                        onActionClick = { action -> onGoodActionClick(openScreen, goodItem, action) }
                    )
                }
            }
        }
    }
}

