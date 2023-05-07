package com.example.tinge.data

import android.graphics.Bitmap

data class TingePerson(
    val firstName: String,
    val lastName: String,
    val imageId: Bitmap?,
    var age: Int,
    val height: Int,
    val gender: String = "Male",
    val email: String?,
    val preference: String
)
{
    constructor() : this("", "", null, 0, 0, "", "","")
}
