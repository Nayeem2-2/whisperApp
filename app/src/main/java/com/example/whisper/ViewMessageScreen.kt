package com.example.whisper

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ViewMessagesScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    var receiver by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<Message>()) }
    val db = FirebaseFirestore.getInstance()

    Column(modifier = Modifier.padding(24.dp)) {
        TextField(
            value = receiver,
            onValueChange = { receiver = it },
            label = { Text("Search Receiver Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            if (receiver.isNotBlank()) {
                db.collection("messages")
                    .whereEqualTo("receiverName", receiver)
                    .orderBy("timestamp")
                    .get()
                    .addOnSuccessListener { snapshot ->
                        messages = snapshot.toObjects(Message::class.java)
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn {
            items(messages) { msg ->
                MessageItem(message = msg)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onNavigateBack, modifier = Modifier.fillMaxWidth()) {
            Text("Back to Send")
        }
    }
}
