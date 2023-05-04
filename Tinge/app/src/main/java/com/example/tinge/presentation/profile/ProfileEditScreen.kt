package com.example.tinge.presentation.profile

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tinge.data.TingePerson
import com.example.tinge.data.TingeRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.floor

@Composable
fun ProfileEditScreen(person: TingePerson) {
    var firstName by remember { mutableStateOf(person.firstName) }
    var lastName by remember { mutableStateOf(person.lastName) }
    var feet by remember { mutableStateOf(floor((person.height / 12.0)).toString()) }
    var inches by remember { mutableStateOf((person.height % 12).toString()) }
    var age by remember { mutableStateOf(person.age.toString()) }

    val userEmail = FirebaseAuth.getInstance().currentUser?.email
    val db = Firebase.firestore
    val collectionRef = db.collection("TingePerson")
//    val data = TingePerson(firstName = "Hunter", lastName = "Adam", imageId = 123, age = 21, height = 63, gender = "Male")
//    val documentRef = collectionRef.document()
//    documentRef.set(data)
    collectionRef.whereEqualTo("email", userEmail)
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d("TAG", "${document.id} => ${document.data}")
                val tingePerson = document.toObject(TingePerson::class.java)
                firstName = tingePerson.firstName
                lastName = tingePerson.lastName
                feet = floor((tingePerson.height / 12.0)).toString()
                inches = (tingePerson.height % 12).toString()
                age = tingePerson.age.toString()
            }
        }
        .addOnFailureListener { exception ->
            Log.w("TAG", "Error getting documents: ", exception)
        }
    Log.d("Profile Edit", "Here")
    Column() {
        TextField(
            placeholder = { Text(text = person.firstName) },
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text(text = "First Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp, 4.dp, 4.dp, 4.dp)
        )
        TextField(
            placeholder = { Text(text = person.lastName) },
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text(text = "Last Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp, 4.dp, 4.dp, 4.dp)
        )
        Row() {
            TextField(
                placeholder = { Text(text = feet.toString()) },
                value = feet,
                onValueChange = { feet = it },
                label = { Text(text = "Feet") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(4.dp, 4.dp, 4.dp, 4.dp)
            )
            TextField(
                placeholder = { Text(text = inches.toString()) },
                value = inches,
                onValueChange = { inches = it },
                label = { Text(text = "Inches") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp, 4.dp, 4.dp, 4.dp)
            )
        }
        TextField(
            // TODO: Fix the values not changing. Maybe use a remember from MosterLab
            placeholder = { Text(text = person.age.toString()) },
            value = age,
            onValueChange = { age = it },
            label = { Text(text = "Age") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp, 4.dp, 4.dp, 4.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1,
            singleLine = true,
        )
    }
}

@Preview
@Composable
fun PreviewProfileEditScreen() {
    ProfileEditScreen(person = TingeRepo.getInstance(LocalContext.current).persons.last())
}