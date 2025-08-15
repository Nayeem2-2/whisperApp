package com.example.whisper

data class Message(
    val receiverName: String = "",
    val messageText: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
