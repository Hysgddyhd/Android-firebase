package com.example.makeitso.screens.goods

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.makeitso.common.composable.DropdownContextMenu
import com.example.makeitso.common.ext.contextMenu
import com.example.makeitso.model.Good

@Composable
@ExperimentalMaterialApi
fun GoodItem(
    good: Good,
    options: List<String>,
    onActionClick: (String) -> Unit
) {
  Card(
    backgroundColor = MaterialTheme.colors.background,
    modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp),
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.fillMaxWidth(),
    ) {

        Row(

        ){
          Image(painter = painterResource(good.imageResourceId),contentScale = ContentScale.FillHeight, contentDescription="",
            modifier = Modifier.heightIn(min=100.dp,max=180.dp).padding(8.dp))
          Column(modifier = Modifier.weight(1f)) {
          Text(
            text = good.name,
            style = MaterialTheme.typography.subtitle2,
            textAlign = TextAlign.Center,
            fontSize = 30.sp
          )
          CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            var array = good.addDate.split(" ");
            Log.d("good", array.toString())
            Text(
              text = array[2] + "/" + array[1] + "/" + array[5] + " " + array[0],
              fontSize = 18.sp
            )
          }


        }
          DropdownContextMenu(options, Modifier.contextMenu(), onActionClick)
      }


    }
  }
}

fun getStartTimeAndPeriod(good: Good): String {
  val stringBuilder = StringBuilder("")

  stringBuilder.append(good.addDate)



  return stringBuilder.toString()
}
