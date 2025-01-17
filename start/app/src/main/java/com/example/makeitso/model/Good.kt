package com.example.makeitso.model

import androidx.annotation.DrawableRes
import com.example.makeitso.R
import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Good(
    @DocumentId val id: String = "",
    val name: String="",
    val category:String="",
    val price: Double=0.0,
    val quantity: Int =5,
    val description:String="",
    val addDate: String=Date().toString(),
    @DrawableRes val imageResourceId: Int =R.drawable.book ,
    val userId: String = ""

    )
