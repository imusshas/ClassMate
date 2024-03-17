package com.nasiat_muhib.classmate.navigation

import com.nasiat_muhib.classmate.R
import com.nasiat_muhib.classmate.strings.PROFILE_BUTTON
import com.nasiat_muhib.classmate.strings.ROUTINE_BUTTON
import com.nasiat_muhib.classmate.strings.SIGN_OUT_BUTTON

enum class MenuItem(val iconId: Int, val title: String) {

    Profile(R.drawable.profile, PROFILE_BUTTON),
    Routine(R.drawable.routine, ROUTINE_BUTTON),
    SignOut(R.drawable.log_out, SIGN_OUT_BUTTON),
}