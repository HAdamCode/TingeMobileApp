package com.example.tinge.presentation.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import com.example.tinge.data.TingePerson
import com.example.tinge.presentation.navigation.specs.ListScreenSpec
import com.example.tinge.presentation.navigation.specs.ProfileEditScreenSpec
import com.example.tinge.presentation.viewmodel.ITingeViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.Base64
import kotlin.math.floor

@Composable
fun ProfileEditScreen(
    person: TingePerson,
    tingeViewModel: ITingeViewModel,
    navController: NavHostController,
    context: Context,
    onButton: () -> Unit
) {
    var firstName by remember { mutableStateOf(person.firstName) }
    var lastName by remember { mutableStateOf(person.lastName) }
    var feet by remember { mutableStateOf(floor((person.height / 12.0)).toInt().toString()) }
    var inches by remember { mutableStateOf((person.height % 12).toString()) }
    var age by remember { mutableStateOf(person.age.toString()) }
    var gender by remember { mutableStateOf(person.gender) }
    var preference by remember { mutableStateOf(person.preference) }

    fun loadImageFromBase64(base64String: String): Bitmap? {
        try {
            Log.d("Decoding: ", base64String.length.toString())
            val decodedBytes = Base64.getDecoder().decode(base64String)
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        return null
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Current Profile Image: ")
        if (person.imageId != "") {
            val image = loadImageFromBase64(person.imageId)
            if (image != null) {
                Image(
                    image.asImageBitmap(),
                    "image",
                    modifier = Modifier.size(100.dp)
                )
            }
        }
        Button(
            onClick = onButton
        ) {
            Text("Get Image")
        }
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
        Row {
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
        val genderItems = listOf("Male", "Female", "Non-Binary")
        var expandedGender by remember { mutableStateOf(false) }
        var expandedPreference by remember { mutableStateOf(false) }
        var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
        val icon = if (expandedGender)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown
        val icon2 = if (expandedPreference)
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
                        Modifier.clickable { expandedGender = !expandedGender })
                },
                readOnly = true
            )
            DropdownMenu(
                expanded = expandedGender, onDismissRequest = { expandedGender = !expandedGender },
                modifier = Modifier.width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
            ) {
                genderItems.forEach { genderItem ->
                    DropdownMenuItem(text = { Text(text = genderItem) }, onClick = {
                        gender = genderItem
                        expandedGender = false
                    })
                }
            }
        }
        Column {
            Text(text = "Preference:")
            OutlinedTextField(value = preference, onValueChange = { preference = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        mTextFieldSize = coordinates.size.toSize()
                    }, trailingIcon = {
                    Icon(icon2, "contentDescription",
                        Modifier.clickable { expandedPreference = !expandedPreference })
                },
                readOnly = true
            )
            DropdownMenu(
                expanded = expandedPreference,
                onDismissRequest = { expandedPreference = !expandedPreference },
                modifier = Modifier.width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
            ) {
                genderItems.forEach { genderItem ->
                    DropdownMenuItem(text = { Text(text = genderItem) }, onClick = {
                        preference = genderItem
                        expandedPreference = false
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
                    "",
                    age.toInt(),
                    (feet.toInt() * 12) + inches.toInt(),
                    gender,
                    FirebaseAuth.getInstance().currentUser?.email,
                    preference,
                    person.lat,
                    person.lon
                )
                if (tingeViewModel.checkIfInDB()) {
                    Log.d("ProfileEditScreen", "Inside good one")
                    tingeViewModel.updatePerson(tingePerson, tingeViewModel.currentImageState.value)
                    navController.navigate(route = ListScreenSpec.route)
                    ProfileEditScreenSpec.SaveToast(
                        context
                    )
                } else {
                    Log.d("ProfileEditScreen", "Inside bad one")
                    tingeViewModel.addPerson(tingePerson)
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Save update"
                )
            }
        }
    }
}