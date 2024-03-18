package com.nasiat_muhib.classmate.presentation.main.components.display_course

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.nasiat_muhib.classmate.R
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.ui.theme.SixtyHeight
import com.nasiat_muhib.classmate.ui.theme.TitleStyle

@Composable
fun BlackBoardContent(course: Course) {

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Image(painter = painterResource(id = R.drawable.blackboard), contentDescription = null)

        Column (modifier = Modifier
            .fillMaxWidth()
            .padding(start = SixtyHeight)) {
            BlackboardRow(title = "Department", text = course.courseDepartment)
            BlackboardRow(title = "Semester", text = course.courseSemester)
            BlackboardRow(title = "Code", text = course.courseCode)
            BlackboardRow(title = "Title", text = course.courseTitle)
            BlackboardRow(title = "Credit", text = course.courseCredit.toString())
            BlackboardRow(title = "Teacher", text = course.courseTeacher)
            BlackboardRow(title = "Class Representative", text = course.courseCreator)
            BlackboardRow(title = "Enrolled Students", text = (course.enrolledStudents.size + 1).toString())
        }
    }
}


@Composable
private fun BlackboardRow(title: String, text: String) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = "$title: ", style = TitleStyle, color = Color.White)
        Text(text = text, color = Color.White)
    }
}