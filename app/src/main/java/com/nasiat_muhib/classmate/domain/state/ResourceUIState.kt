package com.nasiat_muhib.classmate.domain.state

data class ResourceUIState(
    val title: String = "",
    val link: String = "",

    val titleError: String? = null,
    val linkError: String? = null
)
