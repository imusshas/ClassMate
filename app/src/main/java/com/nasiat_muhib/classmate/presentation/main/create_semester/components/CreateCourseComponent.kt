package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nasiat_muhib.classmate.components.TitleBox
import com.nasiat_muhib.classmate.core.Constants
import com.nasiat_muhib.classmate.core.Constants.ALREADY_CREATED_TITLE
import com.nasiat_muhib.classmate.core.Constants.AM
import com.nasiat_muhib.classmate.core.Constants.CREATE_CLASS_BUTTON
import com.nasiat_muhib.classmate.core.Constants.DELETE_ICON
import com.nasiat_muhib.classmate.core.Constants.PM
import com.nasiat_muhib.classmate.core.Constants.TAG
import com.nasiat_muhib.classmate.core.UtilityVariables.DAYS_OF_WEEK
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.presentation.main.create_semester.CreateSemesterViewModel
import com.nasiat_muhib.classmate.ui.theme.ButtonBoldStyle
import com.nasiat_muhib.classmate.ui.theme.MediumButtonShape
import java.time.LocalTime


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateCourseComponent(
    createSemesterViewModel: CreateSemesterViewModel? = null,
    user: User? = null,
    onCloseClick: () -> Unit = {},
    onCheckClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {

    var showDialog by rememberSaveable { mutableStateOf(false) }

    var code by rememberSaveable { mutableStateOf("") }
    var title by rememberSaveable { mutableStateOf("") }
    var credit by rememberSaveable { mutableStateOf("") }
    var teacher by rememberSaveable { mutableStateOf("") }

    val time = LocalTime.now()
    val currentHour = if (time.hour > 12) { time.hour - 12 } else if (time.hour == 0) { 12 } else { time.hour }
    val dayNight = time.hour <= 12


    var classroom by rememberSaveable { mutableStateOf("") }
    var weekDay by rememberSaveable { mutableStateOf(DAYS_OF_WEEK[0]) }
    var hour by rememberSaveable { mutableStateOf(currentHour.toString()) }
    var minute by rememberSaveable { mutableStateOf(time.minute.toString()) }
    var shift by rememberSaveable { mutableStateOf(if(time.hour > 12) PM else AM) }


    val classDetailsList = rememberSaveable { mutableSetOf<ClassDetails>() }
    var editDetails by remember { mutableStateOf(ClassDetails()) }
    var fromEditDetails by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        CreateCourseTopBar(onCloseClick = onCloseClick, onCheckClick = {
            val course = Course(
                code = code,
                title = title,
                credit = credit,
//                creator = user.email,
                teacher = teacher,
            )
//            createSemesterViewModel.createCourse(course)
//            createSemesterViewModel.createClassDetails(code, classDetailsList)
            onCheckClick.invoke()
        })

        EditCourseSection(
            code = code,
            onCodeChange = { code = it },
            title = title,
            onTitleChange = { title = it },
            teacher = teacher,
            onTeacherChange = { teacher = it },
            credit = credit,
            onCreditChange = { credit = it },
            enabled = classDetailsList.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(40.dp))

        ElevatedButton(
            onClick = {
                showDialog = true
                weekDay = DAYS_OF_WEEK[0]
                hour = currentHour.toString()
                minute = time.minute.toString()
                classroom = ""
                      },
            enabled = code.isNotEmpty() && title.isNotEmpty() && credit.isNotEmpty() && teacher.isNotEmpty(),
            shape = MediumButtonShape
        ) {
            Text(text = CREATE_CLASS_BUTTON, style = ButtonBoldStyle)
        }

        Spacer(modifier = Modifier.height(48.dp))

        TitleBox(
            title = ALREADY_CREATED_TITLE,
            titleColor = Color.White,
            modifier = Modifier
                .clip(MediumButtonShape)
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .background(Color.Black)
                .height(48.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            classDetailsList.forEach {
                Card(
                    shape = MediumButtonShape,
                    elevation = CardDefaults.cardElevation(15.dp),
                    modifier = Modifier.height(48.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = it.weekDay, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                        Text(text = it.hour.toString(), textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                        Text(text = it.minute.toString(), textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                        Text(text = it.shift, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                        Text(text = it.classroom, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = Constants.EDIT_ICON,
                            modifier = Modifier
                                .fillMaxHeight()
                                .weight(1f)
                                .border(
                                    BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                    MediumButtonShape
                                )
                                .padding(8.dp)
                                .clickable {
                                    weekDay = it.weekDay
                                    hour = it.hour.toString()
                                    minute = it.minute.toString()
                                    shift = it.shift
                                    classroom = it.classroom

                                    editDetails = it
                                    fromEditDetails = true
                                    showDialog = true
                                }
                        )
                    }
                }
            }
        }
    }


    if (showDialog) {
        CreateClassSection(
            onDismissRequest = { showDialog = false },
            classroom = classroom,
            onClassroomChange = { classroom = it },
            weekDay = weekDay,
            onWeekDayChange = { weekDay = it },
            hour = hour,
            onHourChange = { hour = it },
            minute = minute,
            onMinuteChange = { minute = it },
            dayNight = dayNight,
            onAmClick = { shift = AM },
            onPmClick = { shift = PM },
            onOkClick = {
                val currentTime = LocalTime.now()

                if (fromEditDetails) {
                    classDetailsList.remove(editDetails)
                    fromEditDetails = false
                }

                Log.d(TAG, "CreateCourseComponent: $classDetailsList")
                val details = ClassDetails(
                    classroom = classroom,
                    weekDay = weekDay,
                    hour = if (hour.isEmpty()) currentTime.hour else hour.toInt(),
                    minute = if (minute.isEmpty()) currentTime.minute else minute.toInt(),
                    shift = shift,
                )
                if(classroom.isNotEmpty()) {
                    classDetailsList.add(details)
                    showDialog = false
                }
            },
            onCancelClick = {
                showDialog = false
            }
        )
    }

}