package com.nasiat_muhib.classmate

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.nasiat_muhib.classmate.components.AutoCompleteField
import com.nasiat_muhib.classmate.core.Constants
import com.nasiat_muhib.classmate.core.Constants.TAG
import com.nasiat_muhib.classmate.core.DocumentSnapshotToObjectFunctions
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.domain.model.ResponseState
import com.nasiat_muhib.classmate.navigation.ClassMateApp
import com.nasiat_muhib.classmate.ui.theme.ClassMateTheme
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            ClassMateTheme {
                ClassMateApp()
//                Demo()

//                AutoComplete()

            }
        }
    }
}


@Composable
fun Demo() {

    val classDetails = listOf(
        ClassDetails("Monday", "10:00 AM", "Sylhet Science City", true),
        ClassDetails("Tuesday", "11:00 AM", "G1", false),
        ClassDetails("Sunday", "11:00 AM", "G2", true),
    )

// Replace with your document reference
    val firestoreRef = Firebase.firestore
    firestoreRef.collection("demo").document("details").get().addOnSuccessListener { classDetailsSnapshot ->
        if(classDetailsSnapshot.exists()) {
            val details = DocumentSnapshotToObjectFunctions.getClassDetailsFromDocumentSnapshot(
                classDetailsSnapshot
            )
            val mutableDetails = mutableListOf<ClassDetails>()
            details.forEach {
                mutableDetails.add(it)
            }

            details.forEach { detail ->
                classDetails.forEach {
                    if(it.weekDay == detail.weekDay && it.time == detail.time) {
                        mutableDetails.remove(detail)
                    }
                }
            }

            classDetails.forEach {
                mutableDetails.add(it)
            }

            firestoreRef.collection("demo").document("details").update(
                DocumentSnapshotToObjectFunctions.getMapFromClassDetails(mutableDetails)
            )
                .addOnSuccessListener {
                    Log.d(TAG, "Success: $mutableDetails")
                }.addOnFailureListener {
                    Log.d(TAG, "Failure: ${it.localizedMessage}")
                }
        } else {
            Log.d(TAG, "Failure: Does not exist")
        }
    }.addOnFailureListener {
        Log.d(TAG, "Failure: ${it.localizedMessage}")
    }
}