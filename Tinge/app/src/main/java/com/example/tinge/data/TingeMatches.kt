package com.example.tinge.data

data class TingeMatches(
    val currentuser: String,
    val otheruser: String,
    val liked: Boolean
){
    constructor() : this("", "", true)
}
