package com.example.makeitso.common.ext

import com.example.makeitso.model.Good

fun Good.hasName(): Boolean {
  return !this.name.isNullOrEmpty()
}

fun Good.hasPrice(): Boolean {
  return this.price!=0.0
}
