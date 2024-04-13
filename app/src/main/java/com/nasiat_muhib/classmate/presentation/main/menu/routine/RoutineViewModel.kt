package com.nasiat_muhib.classmate.presentation.main.menu.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasiat_muhib.classmate.core.Constants.WEEK_DAYS
import com.nasiat_muhib.classmate.data.model.ClassDetails
import com.nasiat_muhib.classmate.domain.event.RoutineUIEvent
import com.nasiat_muhib.classmate.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val searchRepo: SearchRepository,
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _weekDay = MutableStateFlow(WEEK_DAYS[0])
    val weekDay = _weekDay.asStateFlow()

    private val _classes = MutableStateFlow<Set<ClassDetails>>(emptySet())

    private val _weekDayClasses = weekDay
        .combine(_classes) { weekDay, classList ->
            classList.filter {
                it.weekDay == weekDay
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _classes.value
        )


    val classes = searchText
        .combine(_weekDayClasses) { text, classList ->
            if (text.isBlank()) {
                classList
            } else {
                classList.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _weekDayClasses.value
        )

    init {
        getAllClasses()
    }

    private fun getAllClasses() = viewModelScope.launch {
        searchRepo.getAllClasses().collectLatest {
            _classes.value = it
        }
    }

    fun onRoutineEvent(event: RoutineUIEvent) {
        when (event) {
            is RoutineUIEvent.SearchTextChanged -> {
                _searchText.value = event.text
            }
            is RoutineUIEvent.WeekDayChanged -> {
                _weekDay.value = event.weekDay
//                Log.d(TAG, "onRoutineEvent: ${event.weekDay}")
            }
        }
    }

    companion object {
        const val TAG = "RoutineViewModel"
    }
}