package com.nasiat_muhib.classmate

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.nasiat_muhib.classmate.core.Constants.TAG
import com.nasiat_muhib.classmate.navigation.ClassMateApp
import com.nasiat_muhib.classmate.ui.theme.ClassMateTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            ClassMateTheme {
                ClassMateApp()
//                Demo()
            }
        }
    }
}


@Composable
fun Demo() {
    val fruits = listOf(
        "Mango",
        "Apple",
        "Banana"
    )

    val document = Firebase.firestore.collection("demo").document("demo")

    document.set(mapOf("fruits" to fruits))

    document.addSnapshotListener { value, error ->

        Log.d(TAG, "Demo: ${value?.data?.values}")
        val data = value?.get("fruits")!! as List<*>
        Log.d(TAG, "Data: $data")
    }
}