package com.nasiat_muhib.classmate.navigation

import com.nasiat_muhib.classmate.R
import com.nasiat_muhib.classmate.core.Constants.LOG_OUT
import com.nasiat_muhib.classmate.core.Constants.PROFILE
import com.nasiat_muhib.classmate.core.Constants.ROUTINE

enum class MenuItem(val iconId: Int, val title: String) {

    Profile(R.drawable.profile, PROFILE),
    Routine(R.drawable.routine, ROUTINE),
    SignOut(R.drawable.log_out, LOG_OUT),
}