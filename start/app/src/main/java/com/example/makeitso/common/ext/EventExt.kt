package com.example.makeitso.common.ext

import com.example.makeitso.model.Event

fun Event?.hasStartDate(): Boolean {
  return true
}

fun Event?.hasPeriod(): Boolean {
  return this?.period!=0
}
