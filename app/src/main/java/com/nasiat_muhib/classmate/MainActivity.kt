package com.nasiat_muhib.classmate

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.navigation.ClassMateSecondVersion
import com.nasiat_muhib.classmate.ui.theme.ClassMateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            ClassMateTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ClassMateSecondVersion()
                }
            }
        }
    }
}


fun addClassDetailsListToFirebase() {

    val db = Firebase.firestore

    val TAG = "CheckingFirestore"

    val classDetailsList = listOf(
        ClassDetails("CSE", "CSE: 252", 0, "Mon", "g2", "both", 10, 0, "am", 11, 0, "am").toMap(),
        ClassDetails("CSE", "CSE: 252", 1, "Sun", "329", "a", 2, 0, "pm", 3, 0, "pm").toMap()
    )

    db.collection("classDetails").document("classes")
        .set(
            hashMapOf<String, Any>(
                "classDetailsList" to classDetailsList
            )
        ).addOnCompleteListener {
            Log.d(TAG, "addClassDetailsListToFirebase: ${it.isSuccessful}")
        }.addOnFailureListener {
            Log.d(TAG, "addClassDetailsListToFirebase: ${it.localizedMessage}")
        }

//    db.collection("classDetails").document("classes")
//        .get().addOnSuccessListener {
//            Log.d(TAG, "addClassDetailsListToFirebase: ${it.data}")
//        }.addOnFailureListener {
//            Log.d(TAG, "addClassDetailsListToFirebase: ${it.localizedMessage}")
//        }

}