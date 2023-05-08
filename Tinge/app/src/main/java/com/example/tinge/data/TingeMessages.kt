package com.example.tinge.data

import com.google.firebase.Timestamp

data class TingeMessages(
    val sender: String, val receiver: String, val text: String, val timestamp: Timestamp
) {
    constructor() : this("", "", "", Timestamp.now())
}
