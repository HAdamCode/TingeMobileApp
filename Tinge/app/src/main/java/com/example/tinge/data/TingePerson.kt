package com.example.tinge.data

data class TingePerson(
    val firstName: String,
    val lastName: String,
    val imageId: Int,
    var age: Int,
    val height: Int,
    val gender: String,
    val email: String?
)
{
    constructor() : this("", "", 0, 0, 0, "", "")
}
