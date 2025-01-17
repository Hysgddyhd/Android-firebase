package com.example.makeitso.model

import com.example.makeitso.R
import com.google.firebase.firestore.DocumentId
import java.util.Date

val date1:Date=Date()
data class Good(
    @DocumentId val id: String = "",
    val name: String,
    val category:String="",
    val price: Int,
    val quantity: Int =5,
    val description:String="",
    val addDate: Date=Date(),
    val imageResourceId: Int = R.drawable.ic_visibility_on,
    val userId: String = ""

    )
