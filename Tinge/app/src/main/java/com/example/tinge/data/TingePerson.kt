package com.example.tinge.data

import android.graphics.Bitmap

data class TingePerson(
    val firstName: String,
    val lastName: String,
    var imageId: String,
    var age: Int,
    val height: Int,
    val gender: String = "Male",
    val email: String?,
    val preference: String
)
{
    constructor() : this("", "", "", 0, 0, "", "","")
}
