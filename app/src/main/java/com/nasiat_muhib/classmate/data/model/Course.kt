package com.nasiat_muhib.classmate.data.model

data class Course(
    var code: String = "",
    var name: String = "",
    var teacher: String = "",
    var password: String = "",
    var enrolled: String = "",
    var classDetails: List<ClassDetails> = emptyList(),
)
