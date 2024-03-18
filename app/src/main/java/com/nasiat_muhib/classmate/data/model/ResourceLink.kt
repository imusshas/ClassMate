package com.nasiat_muhib.classmate.data.model

import android.graphics.Bitmap
import com.nasiat_muhib.classmate.strings.RESOURCE_COURSE_CODE
import com.nasiat_muhib.classmate.strings.RESOURCE_DEPARTMENT
import com.nasiat_muhib.classmate.strings.RESOURCE_LINK
import com.nasiat_muhib.classmate.strings.RESOURCE_NO
import com.nasiat_muhib.classmate.strings.RESOURCE_TITLE

data class ResourceLink(
    val resourceDepartment: String = "",
    val resourceCourseCode: String = "",
    val resourceNo: Int = -1,
    val title: String = "",
    val link: String = "",
) {
    fun toMap(): Map<String, Any> = mapOf(
        RESOURCE_DEPARTMENT to resourceDepartment,
        RESOURCE_COURSE_CODE to resourceCourseCode,
        RESOURCE_NO to resourceNo,
        RESOURCE_TITLE to title,
        RESOURCE_LINK to link,
    )
}
