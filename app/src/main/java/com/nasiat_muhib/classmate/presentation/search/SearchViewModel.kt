package com.nasiat_muhib.classmate.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasiat_muhib.classmate.data.model.User
import com.nasiat_muhib.classmate.domain.event.SearchUIEvent
import com.nasiat_muhib.classmate.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepo: SearchRepository,
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _users = MutableStateFlow<Set<User>>(emptySet())

    @OptIn(FlowPreview::class)
    val users = searchText
        .debounce(500L)
        .onEach { _isSearching.update { true } }
        .combine(_users) { text, usersList ->
            if (text.isBlank()) {
                usersList
            } else {
                usersList.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }.onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _users.value
        )


    init {
        getAllTeachers()
    }

    fun onSearch(event: SearchUIEvent) {
        when (event) {
            is SearchUIEvent.SearchTextChanged -> {
                _searchText.value = event.searchText
            }
        }
    }

    fun onSearch(text: String) {
        _searchText.value = text
    }

    private fun getAllTeachers() = viewModelScope.launch(Dispatchers.IO) {
        searchRepo.getAllTeachers().collectLatest {
            Log.d(TAG, "getAllTeachers: $it")
            _users.value = it
        }
    }

    companion object {
        const val TAG = "SearchViewModel"
    }
}


private val allUsers = listOf(
    User(firstName = "Md. Abid Ullah", lastName = "Muhib"),
    User(firstName = "Rayhanul Amin", lastName = "Tanvir"),
    User(firstName = "Nasiat Hasan", lastName = "Fahim"),
    User(firstName = "Rezaul Karim", lastName = "Sumon"),
    User(firstName = "Niloy Sarkar", lastName = "Tonmoy"),
    User(firstName = "Mohammad Ridowan", lastName = "Sikder"),
    User(firstName = "Khalid Bin", lastName = "Selim"),
    User(firstName = "Gazi Mujtaba", lastName = "Rafid"),
    User(firstName = "Al Fahad", lastName = "Rakib"),
    User(firstName = "Iqbal Mahmood", lastName = "Sajid"),
)