package com.nasiat_muhib.classmate.presentation.main.create_semester.components

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.event.CreateClassUIEvent
import com.nasiat_muhib.classmate.domain.event.CreateCourseUIEvent
import com.nasiat_muhib.classmate.domain.repository.CourseRepository
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import com.nasiat_muhib.classmate.domain.rules.CreateCourseValidator
import com.nasiat_muhib.classmate.domain.state.CreateClassUIState
import com.nasiat_muhib.classmate.domain.state.CreateCourseUIState
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateCourseViewModel @Inject constructor(
    private val courseRepo: CourseRepository,
    private val userRepo: UserRepository,
    private val auth: FirebaseAuth
) : ViewModel() {


    // Create Course UI State
    private val _createCourseUIState = MutableStateFlow(CreateCourseUIState())
    val createCourseUIState = _createCourseUIState.asStateFlow()

    // Create Class UI State
    private val _createClassUIState = MutableStateFlow(CreateClassUIState())
    val createClassUIState = _createClassUIState.asStateFlow()

    // Create Course UI Validation
    private val _allCreateCourseValidationPassed = MutableStateFlow(false)
    val allCreateCourseValidationPassed = _allCreateCourseValidationPassed.asStateFlow()

    // Create Class Dialog Validation
    private val _allCreateClassValidationPassed = MutableStateFlow(false)
    val allCreateClassValidationPassed = _allCreateClassValidationPassed.asStateFlow()

    // Create Class Dialog State
    private val _createClassDialogState = MutableStateFlow(false)
    val createCourseDialogState = _createClassDialogState.asStateFlow()

    // Create Class Details List Data State
    private val _createClassDetailsDataList = MutableStateFlow<MutableSet<ClassDetails>>(mutableSetOf())
    val createClassDetailsDataList = _createClassDetailsDataList.asStateFlow()

    // Create Class Details List Validation
    private val _createClassDetailsListValidationPassed = MutableStateFlow(false)
    val createClassDetailsListValidationPassed = _createClassDetailsListValidationPassed.asStateFlow()

    // Current User
    private val _userState = MutableStateFlow<DataState<User>>(DataState.Success(User()))
    private val userState = _userState.asStateFlow()



    fun getUser() = viewModelScope.launch(Dispatchers.IO) {
        auth.currentUser?.email?.let { email ->
            userRepo.getCurrentUser(email).collectLatest {
                _userState.value = it
            }
        }
    }


    // Create Course
    fun onCreateCourse(event: CreateCourseUIEvent) {

        when (event) {
            // Course Code Change
            is CreateCourseUIEvent.CourseCodeChanged -> {
                _createCourseUIState.value =
                    _createCourseUIState.value.copy(courseCode = event.courseCode)
            }

            // Course Credit Change
            is CreateCourseUIEvent.CourseCreditChanged -> {
                val credit = if (event.courseCredit.isBlank()) 0.0 else event.courseCredit.toDouble()
                _createCourseUIState.value =
                    _createCourseUIState.value.copy(
                        courseCredit = credit
                    )
            }

            // Course Semester Change
            is CreateCourseUIEvent.CourseSemesterChanged -> {
                _createCourseUIState.value =
                    _createCourseUIState.value.copy(courseSemester = event.courseSemester)
            }

            // Course Title Change
            is CreateCourseUIEvent.CourseTitleChanged -> {
                _createCourseUIState.value =
                    _createCourseUIState.value.copy(courseTitle = event.courseTitle)
            }

            // Back Button
            CreateCourseUIEvent.BackButtonClick -> {
                _createCourseUIState.value = CreateCourseUIState()
                _createClassDetailsDataList.value.clear()
            }

            // Create Class
            CreateCourseUIEvent.CreateClassButtonClick -> {
                createCourse()
            }

            // Select Teacher Teacher
            is CreateCourseUIEvent.SearchUIRequestButtonClick -> {
                _createCourseUIState.value =
                    _createCourseUIState.value.copy(
                        courseTeacherEmail = event.courseTeacherEmail
                    )
            }

            // Create Course
            is CreateCourseUIEvent.CreateClick -> {
                onCreate()
            }

            is CreateCourseUIEvent.ClassDetailsDeleteSwipe -> {
                _createClassDetailsDataList.value.remove(event.classDetails)
            }
        }
    }

    private fun createCourse() = viewModelScope.launch(Dispatchers.IO) {
        validateCreateCourseUIDataWithRules()
        if (allCreateCourseValidationPassed.value) {
            _createClassDialogState.value = true
        }
    }

    private fun onCreate() = viewModelScope.launch(Dispatchers.IO) {
        validateCreateClassDetailsListDataWithRules()
        if (allCreateCourseValidationPassed.value && allCreateClassValidationPassed.value && createClassDetailsListValidationPassed.value) {

            userState.value.data?.let { user ->
                val courseClasses = mutableListOf<String>()
                createClassDetailsDataList.value.forEachIndexed { index, _ ->
                    courseClasses.add("$index")
                }
                val course = Course(
                    courseCreator = user.email,
                    courseDepartment = user.department,
                    courseCode = createCourseUIState.value.courseCode,
                    courseTitle = createCourseUIState.value.courseTitle,
                    courseTeacher = createCourseUIState.value.courseTeacherEmail,
                    courseCredit = createCourseUIState.value.courseCredit,
                    courseSemester = createCourseUIState.value.courseSemester,
                    courseClasses = courseClasses
                )
//                Log.d(TAG, "onCreate: $course")
                courseRepo.createCourse(course, createClassDetailsDataList.value).collectLatest {
//                    Log.d(TAG, "onCreate: courseRepo: course: ${it.first.data}")
//                    Log.d(TAG, "onCreate: courseRepo: classes: ${it.second.data}")
                }

                _createClassDetailsDataList.value.clear()
                _createCourseUIState.value = CreateCourseUIState()
                _createClassUIState.value = CreateClassUIState()
            }
        }

//        Log.d(TAG, "onCreate: createCourse: ${allCreateCourseValidationPassed.value}: createClass: ${allCreateCourseValidationPassed.value}: classDetails: ${classDetailsListValidationPassed.value}")
    }

    private fun validateCreateCourseUIDataWithRules() {

        val credit: String =
            if (createCourseUIState.value.courseCredit == 0.0) "" else createCourseUIState.value.courseCredit.toString()

        val courseCodeResult =
            CreateCourseValidator.validateCourseCode(createCourseUIState.value.courseCode)
        val courseCreditResult =
            CreateCourseValidator.validateCourseCredit(credit)
        val courseTitleResult =
            CreateCourseValidator.validateCourseTitle(createCourseUIState.value.courseTitle)
        val courseTeacherEmailResult =
            CreateCourseValidator.validateCourseTeacherEmail(createCourseUIState.value.courseTeacherEmail)

        _createCourseUIState.value = _createCourseUIState.value.copy(
            courseCodeError = courseCodeResult.message,
            courseCreditError = courseCreditResult.message,
            courseTitleError = courseTitleResult.message,
            courseTeacherEmailError = courseTeacherEmailResult.message,
        )

//        Log.d(TAG, "validateCreateCourseUIDataWithRules: ${createCourseUIState.value}")

        _allCreateCourseValidationPassed.value =
            courseCodeResult.message == null &&
                    courseTitleResult.message == null &&
                    courseCreditResult.message == null &&
                    courseTeacherEmailResult.message == null
    }

    private fun validateCreateClassDetailsListDataWithRules() {
        val createClassResult =
            CreateCourseValidator.validateClassDetails(createClassDetailsDataList.value.toList())

        _createCourseUIState.value = _createCourseUIState.value.copy(
            createClassError = createClassResult.message
        )

        _createClassDetailsListValidationPassed.value = createClassResult.message == null
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
//                Log.d(TAG, "onCreateClass: ${createClassUIState.value}")
                createClass()
            }
        }
    }

    // Checks Create Class Validation Validation and Adds Created Class to the existing list
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
            
            _createClassDetailsDataList.value.add(details)
//            Log.d(TAG, "createClass: ${createClassDetailsDataList.value}")
        }
    }

    // Validates create class UI data
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