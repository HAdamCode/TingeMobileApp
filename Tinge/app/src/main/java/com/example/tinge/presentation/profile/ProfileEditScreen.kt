package com.example.tinge.presentation.profile

import android.content.Context
import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import com.example.tinge.data.TingePerson
import com.example.tinge.data.TingeRepo
import com.example.tinge.presentation.navigation.specs.ListScreenSpec
import com.example.tinge.presentation.navigation.specs.ProfileEditScreenSpec
import com.example.tinge.presentation.viewmodel.ITingeViewModel
import com.example.tinge.presentation.viewmodel.TingeViewModel
import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.floor

@Composable
fun ProfileEditScreen(person: TingePerson, tingeViewModel: ITingeViewModel, navController: NavHostController, context: Context) {
    var firstName by remember { mutableStateOf(person.firstName) }
    var lastName by remember { mutableStateOf(person.lastName) }
    var feet by remember { mutableStateOf(floor((person.height / 12.0)).toInt().toString()) }
    var inches by remember { mutableStateOf((person.height % 12).toString()) }
    var age by remember { mutableStateOf(person.age.toString()) }
    var gender by remember { mutableStateOf(person.gender) }

//    val userEmail = FirebaseAuth.getInstance().currentUser?.email
//    val db = Firebase.firestore
//    val collectionRef = db.collection("TingePerson")
////    val data = TingePerson(firstName = "Hunter", lastName = "Adam", imageId = 123, age = 21, height = 63, gender = "Male")
////    val documentRef = collectionRef.document()
////    documentRef.set(data)
//    collectionRef.whereEqualTo("email", userEmail)
//        .get()
//        .addOnSuccessListener { documents ->
//            for (document in documents) {
//                Log.d("TAG", "${document.id} => ${document.data}")
//                val tingePerson = document.toObject(TingePerson::class.java)
//                firstName = tingePerson.firstName
//                lastName = tingePerson.lastName
//                feet = floor((tingePerson.height / 12.0)).toString()
//                inches = (tingePerson.height % 12).toString()
//                age = tingePerson.age.toString()
//            }
//        }
//        .addOnFailureListener { exception ->
//            Log.w("TAG", "Error getting documents: ", exception)
//        }
    Log.d("Profile Edit", "Here")
    Column(modifier = Modifier.fillMaxSize()) {
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
        var genderItems = listOf("Male", "Female", "Non-Binary")
        var expanded by remember { mutableStateOf(false) }
        var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
        val icon = if (expanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown
        Column {
            Text(text = "Gender:")
            OutlinedTextField(value = gender, onValueChange = { gender = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        mTextFieldSize = coordinates.size.toSize()
                    }, trailingIcon = {
                    Icon(icon, "contentDescription",
                        Modifier.clickable { expanded = !expanded })
                },
                readOnly = true
            )
//            OutlinedTextField(value = gender, onValueChange = {gender = it},
//            modifier = Modifier
//                .fillMaxWidth()
//                .onGloballyPositioned { coordinates ->
//                    mTextFieldSize = coordinates.size.toSize()
//                }, trailingIcon = {
//                    Icon(icon, "contentDescription",
//                        Modifier.clickable { expanded = !expanded })
//                }
//            )
            DropdownMenu(
                expanded = expanded, onDismissRequest = { expanded = !expanded },
                modifier = Modifier.width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
            ) {
                genderItems.forEach { genderItem ->
                    DropdownMenuItem(text = { Text(text = genderItem) }, onClick = {
                        gender = genderItem
                        expanded = false
                    })
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(onClick = {
                Log.d("test", feet.toInt().toString())
                Log.d("test", inches)
                val tingePerson = TingePerson(
                    firstName,
                    lastName,
                    123,
                    age.toInt(),
                    (feet.toInt() * 12) + inches.toInt(),
                    gender,
                    FirebaseAuth.getInstance().currentUser?.email
                )
                if (tingeViewModel.checkIfInDB()) {
                    Log.d("ProfileEditScreen", "Inside good one")
                    tingeViewModel.updatePerson(tingePerson)
                    navController.navigate(route = ListScreenSpec.route);
                    ProfileEditScreenSpec.SaveToast(
                        context
                    )
                } else {
                    Log.d("ProfileEditScreen", "Inside bad one")
                    tingeViewModel.addPerson(tingePerson)
                }
            }) {
                Icon(
                    //PLACEHOLDER ICON
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Save update"
                )
            }
        }
    }



//        TextField(
//            // TODO: Fix the values not changing. Maybe use a remember from MosterLab
//            placeholder = { Text(text = person.gender) },
//            value = gender,
//            onValueChange = { gender = it },
//            label = { Text(text = "Gender") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(4.dp, 4.dp, 4.dp, 4.dp),
//            maxLines = 1,
//            singleLine = true,
//        )

//        Button(onClick = {
//            // TODO fix gender and picture
//            Log.d("test", feet.toInt().toString())
//            Log.d("test", inches)
//            val tingePerson = TingePerson(
//                firstName,
//                lastName,
//                123,
//                age.toInt(),
//                (feet.toInt() * 12) + inches.toInt(),
//                gender,
//                FirebaseAuth.getInstance().currentUser?.email
//            )
//            if (tingeViewModel.checkIfInDB()) {
//                Log.d("ProfileEditScreen", "Inside good one")
//                tingeViewModel.updatePerson(tingePerson)
//                navController.navigate(route = ListScreenSpec.route);
//                ProfileEditScreenSpec.SaveToast(
//                    context
//                )
//            }
//            else {
//                Log.d("ProfileEditScreen", "Inside bad one")
//                tingeViewModel.addPerson(tingePerson)
//            }
//        }) {
//            Text(text = "Save")
//        }
}

@Preview
@Composable
fun PreviewProfileEditScreen() {
//    ProfileEditScreen(person = TingeRepo.getInstance(LocalContext.current).persons.last())
}