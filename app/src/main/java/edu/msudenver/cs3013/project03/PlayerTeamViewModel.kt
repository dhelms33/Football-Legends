package edu.msudenver.cs3013.project03

import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlayerTeamViewModel(private val playerStore: PlayerStore) : ViewModel() { //viewmodel that does the same as player name, just with the playerteam
    private val _textLiveData = MutableLiveData<String>()
    val textLiveData: LiveData<String> = _textLiveData //initialize as live data

    init {
        viewModelScope.launch {
            playerStore.text.collect {
                _textLiveData.value = it //gather data using playerstore
            }
        }
    }

    fun saveText(text: String) {
        viewModelScope.launch {
            playerStore.saveTeam(text) //save data using playerstore
        }
    }
}

