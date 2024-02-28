package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.components.TitleBox
import com.nasiat_muhib.classmate.core.Constants.ALREADY_CREATED_TITLE
import com.nasiat_muhib.classmate.core.Constants.CREATE_COURSE_BUTTON
import com.nasiat_muhib.classmate.navigation.TabItem
import com.nasiat_muhib.classmate.presentation.main.ClassMateAppScreen
import com.nasiat_muhib.classmate.ui.theme.ButtonBoldStyle
import com.nasiat_muhib.classmate.ui.theme.MediumButtonShape

@Composable
fun CreateCourseContent(
    navigateToTab: (TabItem) -> Unit,
    onCreateCourseClick: () -> Unit
) {


    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ClassMateAppScreen(
            tab = TabItem.CreateSemester,
            navigateToTab = { tabItem -> navigateToTab.invoke(tabItem) }
        )

        // TODO: Implement routine section

        ElevatedButton(onClick = onCreateCourseClick, shape = MediumButtonShape) {
            Text(text = CREATE_COURSE_BUTTON, style = ButtonBoldStyle)
        }
        Spacer(modifier = Modifier.height(48.dp))
        TitleBox(
            title = ALREADY_CREATED_TITLE,
            titleColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .background(Color.Black)
                .height(48.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Create Course Content")
        }
    }



}