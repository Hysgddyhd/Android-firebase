package com.example.makeitso.screens.edit_good

import android.graphics.drawable.Icon
import android.icu.util.Currency
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.makeitso.R
import com.example.makeitso.model.Good
import java.util.Locale


@Composable
fun GoodEntryScreen(
    popUpScreen: () -> Unit,
    viewModel: EditGoodViewModel = hiltViewModel(),
) {
    val good by viewModel.good
    val activity = LocalContext.current as AppCompatActivity
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        GoodEntryBody(
            onSaveClick = {
                viewModel.onDoneClick(popUpScreen)
            },

            good = good,
            onNameChange = viewModel::onNameChange,
            onPriceChange = viewModel::onPriceChange,
            onQuantityChange = viewModel::onQuantityChange,
            onDescrChange = viewModel::onDescriptionChange,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
        )
    }
}

@Composable
fun GoodEntryBody(
    good:Good,
    onNameChange:(String)->Unit,
    onPriceChange:(Double)->Unit,
    onQuantityChange:(Int)->Unit,
    onDescrChange:(String)->Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large)),
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium))
        ) {
        GoodInputForm(
            good = good,
            modifier = modifier,
            onNameChange = onNameChange,
            onPriceChange = onPriceChange,
            onQuantityChange = onQuantityChange,
            onDescrChange = onDescrChange,
        )

        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.save_action))
        }
    }
}


@Composable
fun GoodInputForm(
    good:Good,
    modifier: Modifier = Modifier,
    onNameChange:(String)->Unit,
    onPriceChange:(Double)->Unit,
    onQuantityChange:(Int)->Unit,
    onDescrChange:(String)->Unit,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
       // ListItem()
        OutlinedTextField(
            value = good.name,
            onValueChange = onNameChange,
            label = { Text(stringResource(R.string.item_name_req)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
       // ListItem()
        OutlinedTextField(
            value = good.price.toString(),
            onValueChange = {
                value->
                value.toDoubleOrNull()?.let { onPriceChange(it) }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next),
            label = { Text(stringResource(R.string.item_price_req)) },

            leadingIcon = {
                Text(text="RM", fontSize = 18.sp)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = good.quantity.toString(),
            onValueChange = {
                value->
                value.toIntOrNull()?.let { onQuantityChange(it) }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next),
            label = { Text(stringResource(R.string.quantity_req)) },

            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = good.description,
            onValueChange = onDescrChange,
            label = { Text(stringResource(R.string.description_req)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,

        )
        if (enabled) {
            Text(
                text = stringResource(R.string.required_fields),
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}



