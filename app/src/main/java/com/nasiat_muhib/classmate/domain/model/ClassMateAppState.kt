package com.nasiat_muhib.classmate.domain.model

sealed class ClassMateAppState<out T> {
    data object SignInRequired: ClassMateAppState<Nothing>()
    data object SignedIn: ClassMateAppState<Nothing>()
}