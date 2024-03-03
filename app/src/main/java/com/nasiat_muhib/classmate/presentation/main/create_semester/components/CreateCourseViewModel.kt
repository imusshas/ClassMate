package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import android.util.Log
import androidx.lifecycle.ViewModel
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.domain.event.CreateClassUIEvent
import com.nasiat_muhib.classmate.domain.event.CreateCourseUIEvent
import com.nasiat_muhib.classmate.domain.rules.CreateCourseValidator
import com.nasiat_muhib.classmate.domain.state.CreateClassUIState
import com.nasiat_muhib.classmate.domain.state.CreateCourseUIState
import com.nasiat_muhib.classmate.navigation.ClassMateAppRouter
import com.nasiat_muhib.classmate.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateCourseViewModel @Inject constructor(
    /* TODO: create course repo & inject here */
) : ViewModel() {


    private val _createCourseUIState = MutableStateFlow(CreateCourseUIState())
    val createCourseUIState = _createCourseUIState.asStateFlow()

    private val _createClassUIState = MutableStateFlow(CreateClassUIState())
    val createClassUIState = _createClassUIState.asStateFlow()

    private val _allCreateCourseValidationPassed = MutableStateFlow(false)
    private val allCreateCourseValidationPassed = _allCreateCourseValidationPassed.asStateFlow()

    private val _allCreateClassValidationPassed = MutableStateFlow(false)
    private val allCreateClassValidationPassed = _allCreateClassValidationPassed.asStateFlow()

    private val _createClassDialogState = MutableStateFlow(false)
    val createCourseDialogState = _createClassDialogState.asStateFlow()

    private val _classDetailsDataList = MutableStateFlow<MutableSet<ClassDetails>>(mutableSetOf())
    val classDetailsDataList = _classDetailsDataList.asStateFlow()

    private val _classDetailsListValidationPassed = MutableStateFlow(false)
    private val classDetailsListValidationPassed = _classDetailsListValidationPassed.asStateFlow()


    // Create Course
    fun onCreateCourse(event: CreateCourseUIEvent) {

        when (event) {
            is CreateCourseUIEvent.CourseCodeChanged -> {
                _createCourseUIState.value =
                    _createCourseUIState.value.copy(courseCode = event.courseCode)
            }

            is CreateCourseUIEvent.CourseCreditChanged -> {
                val credit = if (event.courseCredit.isBlank()) 0f else event.courseCredit.toFloat()
                _createCourseUIState.value =
                    _createCourseUIState.value.copy(
                        courseCredit = credit
                    )
            }

            is CreateCourseUIEvent.CourseDepartmentChanged -> {
                _createCourseUIState.value =
                    _createCourseUIState.value.copy(courseDepartment = event.courseDepartment)
            }

            is CreateCourseUIEvent.CourseSemesterChanged -> {
                _createCourseUIState.value =
                    _createCourseUIState.value.copy(courseSemester = event.courseSemester)
            }

            is CreateCourseUIEvent.CourseTeacherEmailChanged -> {
                _createCourseUIState.value =
                    _createCourseUIState.value.copy(courseTeacherEmail = event.courseTeacherEmail)
            }

            is CreateCourseUIEvent.CourseTitleChanged -> {
                _createCourseUIState.value =
                    _createCourseUIState.value.copy(courseTitle = event.courseTitle)
            }

            CreateCourseUIEvent.BackButtonClick -> {
                _classDetailsDataList.value.clear()
                ClassMateAppRouter.navigateTo(Screen.CreateSemesterScreen)
            }

            CreateCourseUIEvent.CreateClassButtonClick -> {
                createCourse()
//                Log.d(TAG, "onCreateCourse: ${createCourseUIState.value}")
            }
        }
    }

    private fun createCourse() {
        validateCreateCourseUIDataWithRules()
        if (allCreateCourseValidationPassed.value) {
            _createClassDialogState.value = true
            validateClassDetailsListDataWithRules()
            if (classDetailsListValidationPassed.value) {
                /* TODO: Call Create Course function here */
            }
        }
    }

    private fun validateCreateCourseUIDataWithRules() {

        val credit: String =
            if (createCourseUIState.value.courseCredit == 0f) "" else createCourseUIState.value.courseCredit.toString()

        val courseCodeResult =
            CreateCourseValidator.validateCourseCode(createCourseUIState.value.courseCode)
        val courseCreditResult =
            CreateCourseValidator.validateCourseCredit(credit)
        val courseDepartmentResult =
            CreateCourseValidator.validateCourseDepartment(createCourseUIState.value.courseDepartment)
        val courseTitleResult =
            CreateCourseValidator.validateCourseTitle(createCourseUIState.value.courseTitle)
        val courseTeacherEmailResult =
            CreateCourseValidator.validateCourseTeacherEmail(createCourseUIState.value.courseTeacherEmail)

        _createCourseUIState.value = _createCourseUIState.value.copy(
            courseCodeError = courseCodeResult.message,
            courseDepartmentError = courseDepartmentResult.message,
            courseCreditError = courseCreditResult.message,
            courseTitleError = courseTitleResult.message,
            courseTeacherEmailError = courseTeacherEmailResult.message,
        )

//        Log.d(TAG, "validateCreateCourseUIDataWithRules: ${createCourseUIState.value}")

        _allCreateCourseValidationPassed.value =
            courseCodeResult.message == null &&
                    courseDepartmentResult.message == null &&
                    courseTitleResult.message == null &&
                    courseCreditResult.message == null &&
                    courseTeacherEmailResult.message == null
    }

    private fun validateClassDetailsListDataWithRules() {
        val createClassResult =
            CreateCourseValidator.validateClassDetails(classDetailsDataList.value.toList())

        _createCourseUIState.value = _createCourseUIState.value.copy(
            createClassError = createClassResult.message
        )

        _classDetailsListValidationPassed.value = createClassResult.message == null
    }


    // Create Class
    fun onCreateClass(event: CreateClassUIEvent) {

        when (event) {
            is CreateClassUIEvent.ClassRoomChanged -> {
                _createClassUIState.value = _createClassUIState.value.copy(
                    classroom = event.classroom
                )
//                Log.d(TAG, "onCreateClass: classroom: ${createClassUIState.value.classroom}")
            }

            is CreateClassUIEvent.EndHourChanged -> {
                val hour = if (event.endHour.isBlank()) -1 else event.endHour.toInt()
                _createClassUIState.value = _createClassUIState.value.copy(
                    endHour = hour
                )
//                Log.d(TAG, "onCreateClass: endHour: ${createClassUIState.value.endHour}")
            }

            is CreateClassUIEvent.EndMinuteChanged -> {
                val minute = if (event.endMinute.isBlank()) -1 else event.endMinute.toInt()
                _createClassUIState.value = _createClassUIState.value.copy(
                    endMinute = minute
                )
//                Log.d(TAG, "onCreateClass: endMinute ${createClassUIState.value.endMinute}")
            }
            is CreateClassUIEvent.EndShiftChanged -> {
                _createClassUIState.value = _createClassUIState.value.copy(
                    endShift = event.endShift
                )
//                Log.d(TAG, "onCreateClass: endShift ${createClassUIState.value.endShift}")
            }

            is CreateClassUIEvent.SectionChanged -> {
                _createClassUIState.value = _createClassUIState.value.copy(
                    section = event.section
                )
//                Log.d(TAG, "onCreateClass: section ${createClassUIState.value.section}")
            }


            is CreateClassUIEvent.StartHourChanged -> {
                val hour: Int = if (event.startHour.isBlank()) -1 else event.startHour.toInt()
                _createClassUIState.value = _createClassUIState.value.copy(
                    startHour = hour
                )
//                Log.d(TAG, "onCreateClass: startHour ${createClassUIState.value.startHour}")
            }

            is CreateClassUIEvent.StartMinuteChanged -> {
                val minute = if (event.startMinute.isBlank()) -1 else event.startMinute.toInt()
                _createClassUIState.value = _createClassUIState.value.copy(
                    startMinute = minute
                )
//                Log.d(TAG, "onCreateClass: startMinute ${createClassUIState.value.startMinute}")
            }

            is CreateClassUIEvent.StartShiftChanged -> {
                _createClassUIState.value = _createClassUIState.value.copy(
                    startShift = event.startShift
                )
//                Log.d(TAG, "onCreateClass: startShift ${createClassUIState.value.startShift}")
            }

            is CreateClassUIEvent.WeekDayChanged -> {
                _createClassUIState.value = _createClassUIState.value.copy(
                    weekDay = event.weekDay
                )
            }

            CreateClassUIEvent.CancelButtonClick -> {
                _createClassDialogState.value = false
            }

            CreateClassUIEvent.CreateButtonClick -> {
                validateCreateClassUIDataWithRules()
//                Log.d(TAG, "onCreateClass: ${createClassUIState.value}")
                createClass()
                if(allCreateClassValidationPassed.value) {
                    _createClassDialogState.value = false
                }
            }
        }
    }

    private fun createClass() {
        validateCreateClassUIDataWithRules()
        if (allCreateClassValidationPassed.value) {
            _createClassDialogState.value = false
            val details = ClassDetails(
                weekDay = _createClassUIState.value.weekDay,
                classroom = _createClassUIState.value.classroom,
                section = _createClassUIState.value.section,
                startHour = _createClassUIState.value.startHour,
                startMinute = _createClassUIState.value.startMinute,
                startShift = _createClassUIState.value.startShift,
                endHour = _createClassUIState.value.endHour,
                endMinute = _createClassUIState.value.endMinute,
                endShift = _createClassUIState.value.endShift,
            )
            _classDetailsDataList.value.add(details)
        }
    }

    private fun validateCreateClassUIDataWithRules() {
        val sHour =
            if (createClassUIState.value.startHour == -1) "" else createClassUIState.value.startHour.toString()
        val sMin =
            if (createClassUIState.value.startMinute == -1) "" else createClassUIState.value.startMinute.toString()
        val eHour =
            if (createClassUIState.value.endHour == -1) "" else createClassUIState.value.endHour.toString()
        val eMin =
            if (createClassUIState.value.endMinute == -1) "" else createClassUIState.value.endMinute.toString()

        val classRoomResult =
            CreateCourseValidator.validateClassroom(createClassUIState.value.classroom)
        val sectionResult =
            CreateCourseValidator.validateSection(createClassUIState.value.section)

        val startHourResult = CreateCourseValidator.validateHour(sHour)
        val endHourResult = CreateCourseValidator.validateHour(eHour)
        val startMinuteResult = CreateCourseValidator.validateMinute(sMin)
        val endMinuteResult = CreateCourseValidator.validateMinute(eMin)

        val durationResult = CreateCourseValidator.validateDuration(
            courseCode = createCourseUIState.value.courseCode,
            courseCredit = createCourseUIState.value.courseCredit,
            startHour = createClassUIState.value.startHour,
            startMinute = createClassUIState.value.startMinute,
            startShift = createClassUIState.value.startShift,
            endHour = createClassUIState.value.endHour,
            endMinute = createClassUIState.value.endMinute,
            endShift = createClassUIState.value.endShift
        )

        _createClassUIState.value = _createClassUIState.value.copy(
            classroomError = classRoomResult.message,
            sectionError = sectionResult.message,
            timeError = (startHourResult.message
                ?: (endHourResult.message ?: (startMinuteResult.message
                    ?: (endMinuteResult.message ?: durationResult.message))))
        )

//        Log.d(TAG, "validateCreateClassUIDataWithRules: ${createClassUIState.value}")

        _allCreateClassValidationPassed.value =
            classRoomResult.message == null &&
                    sectionResult.message == null &&
                    startHourResult.message == null &&
                    endHourResult.message == null &&
                    startMinuteResult.message == null &&
                    endMinuteResult.message == null &&
                    durationResult.message == null

    }

    companion object {
        private const val TAG = "CreateCourseViewModel"
    }
}