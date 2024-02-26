package com.nasiat_muhib.classmate.core

object Constants {
    // Logcat Tag
    const val TAG = "ClassMate"

    // Firebase Firestore
    const val USERS_COLLECTION = "users"
    const val COURSES_COLLECTION = "courses"
    const val CLASS_DETAILS_COLLECTION = "class_details"

    // Labels
    const val EMAIL_LABEL = "Email"
    const val PASSWORD_LABEL = "Password"
    const val FIRSTNAME_LABEL = "Firstname"
    const val LASTNAME_LABEL = "Lastname"
    const val ROLE_LABEL = "Role"
    const val COURSE_CODE_LABEL = "Course Code"
    const val COURSE_PASSWORD_LABEL = "Course Password"

    // Buttons
    const val SIGN_IN_BUTTON = "Sign In"
    const val SIGN_UP_BUTTON = "Sign Up"
    const val CONFIRM_BUTTON = "Confirm"
    const val CANCEL_BUTTON = "Cancel"
    const val EDIT_PROFILE_BUTTON = "Edit Profile"
    const val GO_BACK_BUTTON = "Go Back"
    const val DONE_BUTTON = "Done"
    const val ENROLL_BUTTON = "Enroll"

    // Image Descriptions
    const val APP_LOGO_BACKGROUND = "App Logo Background"
    const val PROFILE_IMAGE = "Profile Image"

    // Image Picker
    const val SELECT_IMAGE_FROM_GALLERY = "image/*"
    const val UPLOAD_PHOTO = "Upload Photo"
    const val CHOOSE_FROM_GALLERY = "Choose From Gallery"


    // Icon Descriptions
    const val VISIBILITY_ICON = "Visibility Icon"
    const val CAMERA_ICON = "Camera Icon"
    const val PERSON_ICON = "Person Icon"
    const val PHOTO_LIBRARY_ICON = "Photo Library Icon"
    const val KEYBOARD_ARROW_UP_ICON = "Keyboard Arrow Up Icon"
    const val KEYBOARD_ARROW_DOWN_ICON = "Keyboard Arrow Down Icon"
    const val ARROW_DROPDOWN_ICON = "Arrow Dropdown Icon"

    // Clickable Text
    const val FORGOT_PASSWORD = "Forgot Password?"

    // Texts
    const val NEW_IN_CLASSMATE = "New In ClassMate?"
    const val ALREADY_USER = "Already A User?"

    // Roles
    const val SELECT_ROLE = "Select Role"
    const val TEACHER = "Teacher"
    const val CLASS_REPRESENTATIVE = "Class Representative"
    const val STUDENT = "Student"

    // User
    const val FIRST_NAME = "firstname"
    const val LAST_NAME = "lastname"
    const val ROLE = "role"
    const val DEPARTMENT = "department"
    const val SESSION = "session"
    const val BLOOD_GROUP = "blood_group"
    const val PHONE_NO = "phone_no"
    const val EMAIL = "email"
    const val PASSWORD = "password"
    const val AVATAR_URL = "avatar_url"
    const val CREATED_COURSE = "created_course"
    const val ENROLLED_COURSE = "enrolled_course"
    const val REQUESTED_COURSE = "requested_course"

    // Course
    const val CODE = "code"
    const val TITLE = "title"
    const val CREATOR = "creator"
    const val ENROLLED_STUDENTS = "enrolled_students"

    // Class Details
    const val WEEK_DAY = "week_day"
    const val TIME = "time"
    const val PLACE = "place"
    const val ACTIVE_STATUS = "active_status"

    // Title
    const val FIRST_NAME_TITLE = "First Name"
    const val LAST_NAME_TITLE = "Last Name"
    const val ROLE_TITLE = "Role"
    const val DEPARTMENT_TITLE = "Department"
    const val SESSION_TITLE = "Session"
    const val BLOOD_GROUP_TITLE = "Blood Group"
    const val PHONE_NO_TITLE = "Phone No"
    const val EMAIL_TITLE = "Email"
    const val PASSWORD_TITLE = "Password"



    // MenuItem
    const val PROFILE = "Profile"
    const val ROUTINE = "Routine"
    const val LOG_OUT = "Log Out"


    // WeekDays
    const val SUNDAY = "Sun"
    const val MONDAY = "Mon"
    const val TUESDAY = "Tue"
    const val WEDNESDAY = "Wed"
    const val THURSDAY = "Thu"
    const val FRIDAY = "Fri"
    const val SATURDAY = "Sat"

    // Am/Pm
    const val AM = "Am"
    const val PM = "Pm"



    // Error Messages

    // Authentication

    const val PLEASE_PROVIDE_ALL_THE_INFORMATION = "Please provide all the information"

    // User
    const val USER_DOES_NOT_EXIST = "User does not exist."
    const val UNABLE_TO_GET_USER = "Unable to get user."

    // Course
    const val COURSE_IS_ALREADY_CREATED_BY = "Course is already created by"
    const val UNABLE_TO_CREATE_COURSE = "Unable to create course"
    const val COURSE_ALREADY_EXISTS = "Course already exists"
    const val UNABLE_TO_UPDATE_COURSE = "Unable to update course"
    const val UNABLE_TO_GET_COURSE = "Unable to get course"
    const val YOU_ARE_NOT_THE_COURSE_CREATOR = "You are not the course creator"
    const val COURSE_DOES_NOT_EXIST = "Course does not exist"
    // Class Details

    const val UNABLE_TO_GET_CLASS_DETAILS = "Unable to get class details"
    const val UNABLE_TO_CREATE_CLASS_DETAILS = "Unable to create course details"
    const val CLASS_DETAILS_ALREADY_EXISTS = "Class details is already exists"
    const val UNABLE_TO_UPDATE_CLASS_DETAILS = "Unable to update class details"
    const val CLASS_DETAILS_DOES_NOT_EXIST = "Class details  does not exist"
}