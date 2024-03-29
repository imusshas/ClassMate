package com.nasiat_muhib.classmate.utils

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.nasiat_muhib.classmate.core.Constants.TAG

@Composable
fun CustomToast(message: String) {
    val context = LocalContext.current
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}