package com.example.makeitso.screens.events

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.makeitso.common.composable.DropdownContextMenu
import com.example.makeitso.common.ext.contextMenu
import com.example.makeitso.common.ext.hasPeriod
import com.example.makeitso.common.ext.hasStartDate
import com.example.makeitso.model.Event
import com.example.makeitso.theme.DarkOrange
import com.example.makeitso.R.drawable as AppIcon

@Composable
@ExperimentalMaterialApi
fun EventItem(
    event: Event,
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
      Column(modifier = Modifier.weight(1f)) {
        Text(text = event.title, style = MaterialTheme.typography.subtitle2, textAlign = TextAlign.Center, fontSize = 30.sp)
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
          Text(text = getStartTimeAndPeriod(event), fontSize = 18.sp)
        }
      }

      if (event.flag) {
        Icon(
          painter = painterResource(AppIcon.ic_flag),
          tint = DarkOrange,
          contentDescription = "Flag"
        )
      }

      DropdownContextMenu(options, Modifier.contextMenu(), onActionClick)
    }
  }
}

fun getStartTimeAndPeriod(event: Event): String {
  val stringBuilder = StringBuilder("")

  if (event.hasStartDate()) {
    stringBuilder.append(event.startTime.getDate())
    stringBuilder.append("/")
    stringBuilder.append(event.startTime.month.inc())
    stringBuilder.append(" at ")
    stringBuilder.append(event.startTime.hours)
    stringBuilder.append(":")
    stringBuilder.append(event.startTime.minutes)
  }

  if (event.hasPeriod()) {
    stringBuilder.append(" of ")
    stringBuilder.append(event.period)
    stringBuilder.append(" mins")

  }

  return stringBuilder.toString()
}
