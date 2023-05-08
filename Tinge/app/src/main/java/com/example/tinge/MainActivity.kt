package com.example.tinge

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.tinge.presentation.navigation.TingeBottomBar
import com.example.tinge.presentation.navigation.TingeNavHost
import com.example.tinge.presentation.navigation.TingeTopBar
import com.example.tinge.presentation.viewmodel.ITingeViewModel
import com.example.tinge.presentation.viewmodel.TingeViewModelFactory
import com.example.tinge.ui.theme.TingeTheme
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tinge.presentation.navigation.specs.ProfileEditScreenSpec
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.location.LocationSettingsStates
import com.google.firebase.auth.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.io.FileDescriptor
import java.io.IOException
import java.nio.charset.Charset

// DO NOT DELETE PLEASE
//    val db = Firebase.firestore
//    val collectionRef = db.collection("TingePerson")
//    val data = TingePerson(firstName = "Hunter", lastName = "Adam", imageId = 123, age = 21, height = 63, gender = "Male")
//    val documentRef = collectionRef.document()
//    documentRef.set(data)
class MainActivity : AppCompatActivity() {
    companion object {
        private const val LOG_TAG = "448.MainActivity"
    }

    private lateinit var locationUtility: LocationUtility
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var locationLauncher: ActivityResultLauncher<IntentSenderRequest>

    fun uriToBitmap(selectedFileUri: Uri): String {
        try {
            // use the new bitmap instead of the decoded bitmap
//            val inputStream = contentResolver.openInputStream(selectedFileUri)
//            val imageBytes = inputStream?.readBytes()
//            inputStream?.close()
//
//            val base64Image = Base64.encodeToString(imageBytes, Base64.NO_WRAP)
//            Log.d("Encoded Length:", base64Image.length.toString())
//            return base64Image
            val MAX_IMAGE_SIZE = 1200
            val inputStream = contentResolver.openInputStream(selectedFileUri)
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()

            var ratio = 1
            if (options.outHeight > MAX_IMAGE_SIZE || options.outWidth > MAX_IMAGE_SIZE) {
                val heightRatio = Math.round(options.outHeight.toFloat() / MAX_IMAGE_SIZE.toFloat())
                val widthRatio = Math.round(options.outWidth.toFloat() / MAX_IMAGE_SIZE.toFloat())
                ratio = if (heightRatio < widthRatio) heightRatio else widthRatio
            }

            val compressedOptions = BitmapFactory.Options()
            compressedOptions.inSampleSize = ratio
            compressedOptions.inPreferredConfig = Bitmap.Config.RGB_565
            val compressedImage = BitmapFactory.decodeStream(contentResolver.openInputStream(selectedFileUri), null, compressedOptions)
            val byteArrayOutputStream = ByteArrayOutputStream()
            compressedImage!!.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            return Base64.encodeToString(byteArray, Base64.NO_WRAP)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

    private lateinit var mTingeViewModel: ITingeViewModel
    private lateinit var auth: FirebaseAuth
    lateinit var storage: FirebaseStorage

    var imageUri : Uri? = null
    var base64: String = ""

    val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        //hande URI here
        imageUri = uri
        base64 = uriToBitmap(imageUri!!)
        //uriToBitmap(uri!!)?.let { mTingeViewModel.updateImage(it) }
        mTingeViewModel.updateImage(base64)
    }
    fun launchImagePicker(){
        Log.d(LOG_TAG, "Launch Image Picker Called")
        pickImage.launch("image/")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate() called")
        locationUtility = LocationUtility(this)



        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                // process if permissions were granted.
                locationUtility.checkPermissionAndGetLocation(this@MainActivity, permissionLauncher = permissionLauncher)
            }
        locationLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { data ->
                    val states = LocationSettingsStates.fromIntent(data)
                    locationUtility.verifyLocationSettingsStates(states)
                }
            }
        }
        val factory = TingeViewModelFactory(this)
        mTingeViewModel = ViewModelProvider(this, factory)[factory.getViewModelClass()]

        val signInLauncher = registerForActivityResult(
            FirebaseAuthUIActivityResultContract()
        ) { res ->
            onSignInResult(res)
        }

        storage = Firebase.storage
        createSignInIntent(signInLauncher)
        setContent {
            val locationState = locationUtility.currentLocationStateFlow.collectAsStateWithLifecycle(lifecycle = this@MainActivity.lifecycle)
            val addressState = locationUtility.currentAddressStateFlow.collectAsStateWithLifecycle(lifecycle = this@MainActivity.lifecycle)
            val isLocationAvailable = locationUtility.currentIsLocationAvailableFlow.collectAsStateWithLifecycle(lifecycle = this@MainActivity.lifecycle)
            LaunchedEffect(locationState.value) {
                locationUtility.getAddress(locationState.value)
            }
            MainActivityContent(tingeViewModel = mTingeViewModel, this@MainActivity,locationUtility,permissionLauncher)
        }
    }
}

@Composable
private fun MainActivityContent(tingeViewModel: ITingeViewModel, mainActivity: MainActivity,locationUtility: LocationUtility,
                                permissionLauncher: ActivityResultLauncher<Array<String>>) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    TingeTheme() {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = {
                    TingeTopBar(tingeViewModel, navController, context)
                },
                bottomBar = {
                    TingeBottomBar(tingeViewModel, navController, context)
                }
            ) {
                TingeNavHost(
                    Modifier.padding(it),
                    navController = navController,
                    context = context,
                    coroutineScope = coroutineScope,
                    tingeViewModel = tingeViewModel,
                    mainActivity = mainActivity,
                    locationUtility = locationUtility,
                    permissionLauncher = permissionLauncher
                )
            }
        }
    }
}

//@Composable
//fun Greeting(name: String) {
//    Text(text = "Hello $name!")
//}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    TingeTheme {
//        Greeting("Android")
//    }
//}


private fun createSignInIntent(signInLauncher: ActivityResultLauncher<Intent>) {

    // [START auth_fui_create_intent]
    // Choose authentication providers
    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build()
    )
    //AuthUI.IdpConfig.PhoneBuilder().build(),
    //AuthUI.IdpConfig.GoogleBuilder().build(),
    //AuthUI.IdpConfig.FacebookBuilder().build(),
    //AuthUI.IdpConfig.TwitterBuilder().build())

    // Create and launch sign-in intent
    Log.d("SIGNININTENT", "Before signInIntent")
    val signInIntent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .build()
    Log.d("SIGNININTENT", "After signInIntent")
    //signInIntent.addFlags(Intent.FLAG)
    signInLauncher.launch(signInIntent)

    // [END auth_fui_create_intent]
}

private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult): FirebaseUser? {
    val response = result.idpResponse
    if (result.resultCode == AppCompatActivity.RESULT_OK) {
        // Successfully signed in
        val user = FirebaseAuth.getInstance().currentUser
        // ...
        return user
    } else {
        // Sign in failed. If response is null the user canceled the
        // sign-in flow using the back button. Otherwise check
        // response.getError().getErrorCode() and handle the error.
        // ...
        return null
    }
}
// [END auth_fui_result]


