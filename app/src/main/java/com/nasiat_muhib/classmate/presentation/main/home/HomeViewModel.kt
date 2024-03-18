package com.nasiat_muhib.classmate.presentation.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.data.model.Course
import com.nasiat_muhib.classmate.data.model.Post
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.event.HomeUIEvent
import com.nasiat_muhib.classmate.domain.event.PostUIEvent
import com.nasiat_muhib.classmate.domain.repository.ClassDetailsRepository
import com.nasiat_muhib.classmate.domain.repository.CourseRepository
import com.nasiat_muhib.classmate.domain.repository.PostRepository
import com.nasiat_muhib.classmate.domain.repository.UserRepository
import com.nasiat_muhib.classmate.domain.rules.CreatePostValidator
import com.nasiat_muhib.classmate.domain.state.CreatePostUIState
import com.nasiat_muhib.classmate.domain.state.DataState
import com.nasiat_muhib.classmate.navigation.ClassMateAppRouter
import com.nasiat_muhib.classmate.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val courseRepo: CourseRepository,
    private val classDetailsRepo: ClassDetailsRepository,
    private val postRepo: PostRepository,
) : ViewModel() {

    private val currentUser = userRepo.currentUser

    private val _userState = MutableStateFlow<DataState<User>>(DataState.Success(User()))
    val userState = _userState.asStateFlow()

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses = _courses.asStateFlow()

    private val _requestedCourses = MutableStateFlow<List<Course>>(emptyList())
    val requestedCourses = _requestedCourses.asStateFlow()

    private val _classes = MutableStateFlow<List<ClassDetails>>(emptyList())
    val classes = _classes.asStateFlow()

    private val _todayClasses = MutableStateFlow<List<ClassDetails>>(emptyList())
    val todayClasses = _todayClasses.asStateFlow()

    private val _tomorrowClasses = MutableStateFlow<List<ClassDetails>>(emptyList())
    val tomorrowClasses = _tomorrowClasses.asStateFlow()

    private val _allPosts = MutableStateFlow<List<Post>>(emptyList())
    val allPosts = _allPosts.asStateFlow()

    private val _userPost = MutableStateFlow<List<Post>>(emptyList())
    val userPost = _userPost.asStateFlow()

    private val _createPostUIState = MutableStateFlow(CreatePostUIState())
    val createPostUIState = _createPostUIState.asStateFlow()

    private val _createPostDialogState = MutableStateFlow(false)
    val createPostDialogState = _createPostDialogState.asStateFlow()

    private val _allCreatePostValidationPassed = MutableStateFlow(false)
    private val allCreatePostValidationPassed = _allCreatePostValidationPassed.asStateFlow()


    init {
        if (currentUser.email != null) {
            getUser(currentUser.email!!)
        }
    }


    private fun getUser(email: String) = viewModelScope.launch(Dispatchers.IO) {
        userRepo.getUser(email).collectLatest {
            _userState.value = it
//            Log.d(TAG, "getUser: $email: ${it.data}")
        }
    }


    fun getCourseList(courseIds: List<String>) = viewModelScope.launch(Dispatchers.IO) {
        courseRepo.getCourses(courseIds).collectLatest {
            _courses.value = it
        }
    }


    fun getRequestedCourseList(courseIds: List<String>) = viewModelScope.launch(Dispatchers.IO) {
        courseRepo.getRequestedCourses(courseIds).collectLatest {
            _requestedCourses.value = it
        }
    }

    fun getClassDetails(courseIds: List<String>) = viewModelScope.launch(Dispatchers.IO) {
        classDetailsRepo.getClassesForMultipleCourse(courseIds).collectLatest {
            _classes.value = it
        }
    }

    fun getTodayAndTomorrowClassesClasses() = viewModelScope.launch(Dispatchers.IO) {

        val todayClassList = mutableListOf<ClassDetails>()
        val today = LocalDate.now().dayOfWeek.toString()

        val tomorrowClassList = mutableListOf<ClassDetails>()
        val tomorrow = LocalDate.now().dayOfWeek.plus(1).toString()

        classes.value.forEach {
            if (today.contains(it.weekDay, ignoreCase = true)) {
                todayClassList.add(it)
            }
            if (tomorrow.contains(it.weekDay, ignoreCase = true)) {
                tomorrowClassList.add(it)
            }
        }
        _todayClasses.value = todayClassList.sortedWith { class1, class2 ->
            val shiftComparison =
                class1.startShift.uppercase().compareTo(class2.startShift.uppercase())
            if (shiftComparison != 0) {
                shiftComparison
            } else if (class1.startHour != class2.startHour) {
                class1.startHour - class2.startHour
            } else {
                class1.startMinute - class2.startMinute
            }
        }
        _tomorrowClasses.value = tomorrowClassList.sortedWith { class1, class2 ->
            val shiftComparison =
                class1.startShift.uppercase().compareTo(class2.startShift.uppercase())
            if (shiftComparison != 0) {
                shiftComparison
            } else if (class1.startHour != class2.startHour) {
                class1.startHour - class2.startHour
            } else {
                class1.startMinute - class2.startMinute
            }
        }
    }

    fun getAllPosts() = viewModelScope.launch {
        postRepo.getPosts().collectLatest { postList ->
            _allPosts.value = postList.sortedByDescending { post -> post.timestamp }
        }
    }

    fun getUserPosts() {
        val postsSet = mutableSetOf<Post>()
        courses.value.forEach { course ->
            allPosts.value.forEach { post ->
                if ((post.creator == course.courseCreator || post.creator == course.courseTeacher) && post.courseCode == course.courseCode) {
                    postsSet.add(post)
                }
            }
        }
        _userPost.value = postsSet.toList()
    }


    fun onHomeEvent(event: HomeUIEvent) {

        when (event) {
            is HomeUIEvent.ClassStatusChange -> {
                userState.value.data?.let { user ->
                    courses.value.forEach { course ->
                        if (
                            event.classDetails.classDepartment == course.courseDepartment &&
                            event.classDetails.classCourseCode == course.courseCode &&
                            (course.courseTeacher == user.email || course.courseCreator == user.email)
                        ) {
                            Log.d(TAG, "onHomeEvent: $course")
                            changeActiveStatus(event.classDetails, event.activeStatus)
                            getTodayAndTomorrowClassesClasses()
                        }

                    }

                }
            }

            is HomeUIEvent.AcceptCourseRequest -> {
                acceptCourseRequest(event.course)
            }

            is HomeUIEvent.DeleteCourseRequest -> {
                deleteCourseRequest(event.course)
            }

            is HomeUIEvent.DisplayCourse -> {
                ClassMateAppRouter.navigateTo(
                    Screen.CourseDetailsDisplay(
                        event.course,
                        event.screen
                    )
                )
            }

            HomeUIEvent.PostButtonClicked -> {
                _createPostDialogState.value = true
            }

            is HomeUIEvent.DeletePostSwipe -> {
                deletePost(event.post)
            }
        }
    }


    private fun deleteCourseRequest(course: Course) = viewModelScope.launch {
        courseRepo.deleteCourse(course).collectLatest {

        }
    }

    private fun acceptCourseRequest(course: Course) = viewModelScope.launch {
        courseRepo.acceptCourse(course).collectLatest {

        }
    }


    private fun changeActiveStatus(classDetails: ClassDetails, status: Boolean) =
        viewModelScope.launch {
            classDetailsRepo.changeActiveStatus(classDetails, status).collectLatest {

            }
        }

    private fun deletePost(post: Post) = viewModelScope.launch {
        postRepo.deletePost(post).collectLatest {

        }
    }


    fun onPostEvent(event: PostUIEvent) {

        when (event) {

            is PostUIEvent.CourseCodeChanged -> {
                _createPostUIState.value =
                    createPostUIState.value.copy(courseCode = event.courseCode)
            }

            is PostUIEvent.DescriptionChanged -> {
                _createPostUIState.value =
                    createPostUIState.value.copy(description = event.description)
            }

            PostUIEvent.DiscardButtonClicked -> {
                _createPostDialogState.value = false
            }

            is PostUIEvent.PostButtonClicked -> {
                onPost(
                    timestamp = event.timestamp,
                    creator = event.creator,
                    firstName = event.firstName,
                    lastName = event.lastName
                )
                getAllPosts()
            }
        }
    }


    private fun onPost(
        timestamp: Long,
        creator: String,
        firstName: String,
        lastName: String,
    ) =
        viewModelScope.launch {
            validateAllCreatePostUIDataWithRules()
            if (allCreatePostValidationPassed.value) {
                _createPostDialogState.value = false
                val post = Post(
                    courseCode = createPostUIState.value.courseCode,
                    description = createPostUIState.value.description,
                    timestamp = timestamp,
                    creator = creator,
                    firstName = firstName,
                    lastName = lastName
                )
                Log.d(TAG, "onPost: $post")
                postRepo.createPost(post).collectLatest {

                }
            }
        }

    private fun validateAllCreatePostUIDataWithRules() {
        val createdOrTeacher = mutableListOf<String>()
        courses.value.forEach { course ->
            if (course.courseCreator == userState.value.data?.email || course.courseTeacher == userState.value.data?.email) {
                createdOrTeacher.add(course.courseCode)
            }

        }

        val courseCodeResult = CreatePostValidator.validateCourseCode(
            createPostUIState.value.courseCode,
            createdOrTeacher
        )
        val descriptionResult =
            CreatePostValidator.validateDescription(createPostUIState.value.description)

        _createPostUIState.value = createPostUIState.value.copy(
            courseCodeError = courseCodeResult.message,
            descriptionError = descriptionResult.message
        )

        _allCreatePostValidationPassed.value =
            courseCodeResult.message == null && descriptionResult.message == null
    }

    companion object {
        const val TAG = "HomeScreenViewModel"
    }
}