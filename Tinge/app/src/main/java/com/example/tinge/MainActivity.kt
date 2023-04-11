package com.example.tinge

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.tinge.presentation.list.TingeListScreen
import com.example.tinge.presentation.navigation.TingeBottomBar
import com.example.tinge.presentation.navigation.TingeNavHost
import com.example.tinge.presentation.navigation.TingeTopBar
import com.example.tinge.presentation.viewmodel.ITingeViewModel
import com.example.tinge.presentation.viewmodel.TingeViewModel
import com.example.tinge.presentation.viewmodel.TingeViewModelFactory
import com.example.tinge.ui.theme.TingeTheme
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.firebase.ui.auth.data.model.PendingIntentRequiredException
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

//class MainActivity : ComponentActivity() {
class MainActivity : AppCompatActivity() {
    companion object {
        private const val LOG_TAG = "448.MainActivity"
    }

    private lateinit var mTingeViewModel: ITingeViewModel
    private lateinit var auth: FirebaseAuth
    lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate() called")

        val factory = TingeViewModelFactory(this)
        mTingeViewModel = ViewModelProvider(this, factory)[factory.getViewModelClass()]

        val signInLauncher = registerForActivityResult(
            FirebaseAuthUIActivityResultContract()
        ) { res ->
            onSignInResult(res)
        }

        storage = Firebase.storage

        setContent {
            createSignInIntent(signInLauncher)
            MainActivityContent(tingeViewModel = mTingeViewModel)
        }
    }
}
@Composable
private fun MainActivityContent(tingeViewModel: ITingeViewModel) {
    val navController = rememberNavController()
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
                TingeNavHost(Modifier.padding(it), navController = navController, context = context, tingeViewModel = tingeViewModel)
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
        AuthUI.IdpConfig.EmailBuilder().build())
    //AuthUI.IdpConfig.PhoneBuilder().build(),
    //AuthUI.IdpConfig.GoogleBuilder().build(),
    //AuthUI.IdpConfig.FacebookBuilder().build(),
    //AuthUI.IdpConfig.TwitterBuilder().build())

    // Create and launch sign-in intent
    Log.d("SIGNININTENT","Before signInIntent")
    val signInIntent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .build()
    Log.d("SIGNININTENT","After signInIntent")
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


