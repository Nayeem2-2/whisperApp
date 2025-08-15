package com.example.whisper

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SendMessageScreen(onNavigate: () -> Unit) {
    val context = LocalContext.current
    var receiver by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    val db = FirebaseFirestore.getInstance()

    Column(modifier = Modifier.padding(24.dp)) {
        TextField(
            value = receiver,
            onValueChange = { receiver = it },
            label = { Text("Receiver Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Message") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            if (receiver.isNotBlank() && message.isNotBlank()) {
                val msg = Message(receiver, message)
                db.collection("messages")
                    .add(msg)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Message Sent", Toast.LENGTH_SHORT).show()
                        message = ""
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Send Message")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onNavigate, modifier = Modifier.fillMaxWidth()) {
            Text("View Messages")
        }
    }
}
