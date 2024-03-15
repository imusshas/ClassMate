package com.nasiat_muhib.classmate.presentation.main.components.display_course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasiat_muhib.classmate.core.Constants.EVENTS
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.Event
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.event.CourseDetailsDisplayUIEvent
import com.nasiat_muhib.classmate.domain.event.CreateClassUIEvent
import com.nasiat_muhib.classmate.domain.event.CreateTermTestUIEvent
import com.nasiat_muhib.classmate.domain.repository.ClassDetailsRepository
import com.nasiat_muhib.classmate.domain.repository.EventRepository
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import com.nasiat_muhib.classmate.domain.rules.CreateCourseValidator
import com.nasiat_muhib.classmate.domain.rules.DisplayCourseValidator
import com.nasiat_muhib.classmate.domain.state.CreateClassUIState
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.domain.state.EventUIState
import com.nasiat_muhib.classmate.navigation.ClassMateAppRouter
import com.nasiat_muhib.classmate.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseDetailsDisplayViewModel @Inject constructor(
    private val classRepo: ClassDetailsRepository,
    private val eventRepo: EventRepository,
    private val userRepo: UserRepository,
) : ViewModel() {


    private val _currentUser = MutableStateFlow<DataState<User>>(DataState.Success(User()))
    val currentUser = _currentUser.asStateFlow()

    private val _currentCourse = MutableStateFlow(Course())
    val currentCourse = _currentCourse.asStateFlow()


    /************************* Class ****************************/
    private val _classes = MutableStateFlow<List<ClassDetails>>(listOf())
    val classes = _classes.asStateFlow()

    private val _createClassDialogState = MutableStateFlow(false)
    val createClassDialogState = _createClassDialogState.asStateFlow()

    private val _createClassUIState = MutableStateFlow(CreateClassUIState())
    val createClassUIState = _createClassUIState.asStateFlow()

    private val _createClassValidationPassed = MutableStateFlow(false)
    val createClassValidationPassed = _createClassValidationPassed.asStateFlow()
    /************************* Class ****************************/

    /************************* TermTest ****************************/
    private val _termTests = MutableStateFlow<List<Event>>(emptyList())
    val termTests = _termTests.asStateFlow()

    private val _createTermTestDialogState = MutableStateFlow(false)
    val createTermTestDialogState = _createTermTestDialogState.asStateFlow()

    private val _termTestUIState = MutableStateFlow(EventUIState(type = EVENTS[0]))
    val termTestUIState = _termTestUIState.asStateFlow()

    private val _termTestValidationPassed = MutableStateFlow(false)
    val termTestValidationPassed = _termTestValidationPassed.asStateFlow()
    /************************* TermTest ****************************/

    /************************* Assignment ****************************/
    private val _assignments = MutableStateFlow<List<Event>>(emptyList())
    val assignments = _assignments.asStateFlow()

    private val _createAssignmentDialogState = MutableStateFlow(false)
    val createAssignmentDialogState = _createAssignmentDialogState.asStateFlow()

    private val _assignmentUIState = MutableStateFlow(EventUIState(type = EVENTS[1]))
    val assignmentUIState = _assignmentUIState.asStateFlow()

    private val _assignmentValidationPassed = MutableStateFlow(false)
    val assignmentValidationPassed = _assignmentValidationPassed.asStateFlow()

    /************************* Assignment ****************************/


    init {
        val currentUser = userRepo.currentUser

        if (currentUser.email != null) {
            getUser(currentUser.email!!)
        }
    }


    private fun getUser(email: String) = viewModelScope.launch {
        userRepo.getUser(email).collectLatest {
            _currentUser.value = it
        }
    }


    fun setCurrentCourse(course: Course) {
        _currentCourse.value = course
    }

    fun getClassDetailsList() = viewModelScope.launch {
        val courseId = "${currentCourse.value.courseDepartment}:${currentCourse.value.courseCode}"
        classRepo.getClasses(courseId)
            .collectLatest {
                _classes.value = it
            }
    }

    fun getTermTestsList() = viewModelScope.launch {
        val courseId = "${currentCourse.value.courseDepartment}:${currentCourse.value.courseCode}"
        eventRepo.getEventList(courseId, EVENTS[0]).collectLatest {
            _termTests.value = it
        }
    }


    fun onDisplayEvent(event: CourseDetailsDisplayUIEvent) {

        when (event) {
            CourseDetailsDisplayUIEvent.CourseDetailsDisplayTopBarBackButtonClicked -> {
                ClassMateAppRouter.navigateTo(Screen.CreateSemesterScreen)
            }

            CourseDetailsDisplayUIEvent.ClassTitleClicked -> {
                currentUser.value.data?.let {
                    if (it.email == currentCourse.value.courseCreator || it.email == currentCourse.value.courseTeacher) {
                        _createClassDialogState.value = true
                    }
                }
            }

            is CourseDetailsDisplayUIEvent.ClassDeleteSwipe -> {
                deleteClass(event.classDetails)
            }

            CourseDetailsDisplayUIEvent.TermTestTitleClicked -> {
                currentUser.value.data?.let {
                    if (it.email == currentCourse.value.courseCreator || it.email == currentCourse.value.courseTeacher) {
                        _createTermTestDialogState.value = true
                    }
                }
            }

            is CourseDetailsDisplayUIEvent.EventDeleteSwipe -> {
                eventRepo.deleteEvent(event.event)
            }
        }
    }

    private fun deleteClass(classDetails: ClassDetails) = viewModelScope.launch {
        classRepo.deleteClass(classDetails).collectLatest {

        }
    }

    fun onCreateClassEvent(event: CreateClassUIEvent) {

        when (event) {
            CreateClassUIEvent.CancelButtonClick -> {
                _createClassDialogState.value = false
            }

            is CreateClassUIEvent.ClassRoomChanged -> {
                _createClassUIState.value =
                    createClassUIState.value.copy(classroom = event.classroom)
            }

            CreateClassUIEvent.CreateButtonClick -> {
                createClass()
            }

            is CreateClassUIEvent.EndHourChanged -> {
                val hour = if (event.endHour == "") -1 else event.endHour.toInt()
                _createClassUIState.value =
                    createClassUIState.value.copy(endHour = hour)
            }

            is CreateClassUIEvent.EndMinuteChanged -> {
                val minute = if (event.endMinute == "") -1 else event.endMinute.toInt()
                _createClassUIState.value =
                    createClassUIState.value.copy(endMinute = minute)
            }

            is CreateClassUIEvent.EndShiftChanged -> {
                _createClassUIState.value = createClassUIState.value.copy(endShift = event.endShift)
            }

            is CreateClassUIEvent.SectionChanged -> {
                _createClassUIState.value = createClassUIState.value.copy(section = event.section)
            }

            is CreateClassUIEvent.StartHourChanged -> {
                val hour = if (event.startHour == "") -1 else event.startHour.toInt()
                _createClassUIState.value =
                    createClassUIState.value.copy(startHour = hour)
            }

            is CreateClassUIEvent.StartMinuteChanged -> {
                val minute = if (event.startMinute == "") -1 else event.startMinute.toInt()
                _createClassUIState.value =
                    createClassUIState.value.copy(startMinute = minute)
            }

            is CreateClassUIEvent.StartShiftChanged -> {
                _createClassUIState.value =
                    createClassUIState.value.copy(startShift = event.startShift)
            }

            is CreateClassUIEvent.WeekDayChanged -> {
                _createClassUIState.value = createClassUIState.value.copy(weekDay = event.weekDay)
            }
        }
    }

    private fun createClass() = viewModelScope.launch {
        validateCreateClassUIDataWithRules()
        if (createClassValidationPassed.value) {
            _createClassDialogState.value = false
            val lastClass =
                if (classes.value.isEmpty()) ClassDetails() else classes.value[classes.value.size - 1]
            val details = ClassDetails(
                classNo = lastClass.classNo + 1,
                classDepartment = currentCourse.value.courseDepartment,
                classCourseCode = currentCourse.value.courseCode,
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

            classRepo.createClass(details).collectLatest {

            }
//            Log.d(TAG, "createClass: ${createClassDetailsDataList.value}")
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
            courseCode = currentCourse.value.courseCode,
            courseCredit = currentCourse.value.courseCredit,
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

        _createClassValidationPassed.value =
            classRoomResult.message == null &&
                    sectionResult.message == null &&
                    startHourResult.message == null &&
                    endHourResult.message == null &&
                    startMinuteResult.message == null &&
                    endMinuteResult.message == null &&
                    durationResult.message == null

    }

    fun onCreateTermTestEvent(event: CreateTermTestUIEvent) {

        when (event) {
            CreateTermTestUIEvent.TermTestCancelButtonClick -> {
                _createTermTestDialogState.value = false
            }

            is CreateTermTestUIEvent.TermTestClassroomChanged -> {
                _termTestUIState.value = termTestUIState.value.copy(classroom = event.classroom)
            }

            CreateTermTestUIEvent.TermTestCreateButtonClick -> {
                createTermTest()
            }

            is CreateTermTestUIEvent.TermTestDayChanged -> {
                val day = if (event.day == "") -1 else event.day.toInt()
                _termTestUIState.value = termTestUIState.value.copy(day = day)
            }

            is CreateTermTestUIEvent.TermTestHourChanged -> {
                val hour = if (event.hour == "") -1 else event.hour.toInt()
                _termTestUIState.value = termTestUIState.value.copy(hour = hour)
            }

            is CreateTermTestUIEvent.TermTestMinuteChanged -> {
                val minute = if (event.minute == "") -1 else event.minute.toInt()
                _termTestUIState.value = termTestUIState.value.copy(minute = minute)
            }

            is CreateTermTestUIEvent.TermTestMonthChanged -> {
                _termTestUIState.value = termTestUIState.value.copy(month = event.month)
            }

            is CreateTermTestUIEvent.TermTestShiftChanged -> {
                _termTestUIState.value = termTestUIState.value.copy(shift = event.shift)
            }

            is CreateTermTestUIEvent.TermTestYearChanged -> {
                val year = if (event.year == "") -1 else event.year.toInt()
                _termTestUIState.value = termTestUIState.value.copy(year = year)
            }
        }
    }

    private fun createTermTest() = viewModelScope.launch {
        validateTermTestUIDataWithRules()
        if (termTestValidationPassed.value) {
            _createTermTestDialogState.value = false
            val lastTermTest =
                if (termTests.value.isEmpty()) Event() else termTests.value[termTests.value.size - 1]
            val termTest = Event(
                type = EVENTS[0],
                eventNo = lastTermTest.eventNo + 1,
                department = currentCourse.value.courseDepartment,
                courseCode = currentCourse.value.courseCode,
                classroom = termTestUIState.value.classroom,
                day = termTestUIState.value.day,
                month = termTestUIState.value.month,
                year = termTestUIState.value.year,
                hour = termTestUIState.value.hour,
                minute = termTestUIState.value.minute,
                shift = termTestUIState.value.shift
            )

            eventRepo.createEvent(termTest).collectLatest {

            }
        }
    }


    private fun validateTermTestUIDataWithRules() {
        val day = if (termTestUIState.value.day == -1) "" else termTestUIState.value.day.toString()
        val year =
            if (termTestUIState.value.year == -1) "" else termTestUIState.value.year.toString()
        val hour =
            if (termTestUIState.value.hour == -1) "" else termTestUIState.value.hour.toString()
        val minute =
            if (termTestUIState.value.minute == -1) "" else termTestUIState.value.minute.toString()

        val classroomResult =
            DisplayCourseValidator.validateClassroom(termTestUIState.value.classroom)
        val dateResult = DisplayCourseValidator.validateDate(day, termTestUIState.value.month, year)
        val hourResult = DisplayCourseValidator.validateHour(hour)
        val minuteResult = DisplayCourseValidator.validateMinute(minute)

        _termTestUIState.value = termTestUIState.value.copy(
            classroomError = classroomResult.message,
            dateError = dateResult.message,
            timeError = hourResult.message ?: minuteResult.message
        )

        _termTestValidationPassed.value = classroomResult.message == null &&
                dateResult.message == null && hourResult.message == null && minuteResult.message == null
    }


}