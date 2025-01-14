package com.example.makeitso.model

import com.google.firebase.firestore.DocumentId
import java.util.Date
import kotlin.time.Duration.Companion.days

val date1:Date=Date()
data class Event(
    @DocumentId val id: String = "",
    val title: String = "",
    val priority: String = "",
    val startTime: Date = date1.apply { date.inc() },
    val period: Int = 0,
    val description: String = "",
    val location: String = "",
    val flag: Boolean = false,
    val experied: Boolean = false,
    val userId: String = ""

    )
