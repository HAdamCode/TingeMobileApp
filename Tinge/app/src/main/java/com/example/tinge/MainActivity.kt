package com.example.tinge

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.tinge.presentation.viewmodel.TingeViewModel
import com.example.tinge.presentation.viewmodel.TingeViewModelFactory
import com.example.tinge.ui.theme.TingeTheme

class MainActivity : ComponentActivity() {
    companion object {
        private const val LOG_TAG = "448.MainActivity"
    }

    private lateinit var mTingeViewModel: TingeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate() called")

        val factory = TingeViewModelFactory(this)
        mTingeViewModel = ViewModelProvider(this, factory)[factory.getViewModelClass()]

        setContent {
            //MainActivityContent(samodelkinViewModel = mSamodelkinViewModel as SamodelkinViewModel)
        }
    }
}

@Composable
private fun MainActivityContent(tingeViewModel: TingeViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current

    TingeTheme() {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

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