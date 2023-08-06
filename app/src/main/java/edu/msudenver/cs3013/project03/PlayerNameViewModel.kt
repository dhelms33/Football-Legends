package edu.msudenver.cs3013.project03


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PlayerNameViewModel(private val playerStore: PlayerStore) : ViewModel() {

    private val _textLiveData = MutableLiveData<String>()
    val textLiveData: LiveData<String> = _textLiveData //initiate data as livedata

    init {
        viewModelScope.launch {
            playerStore.text.collect {
                _textLiveData.value = it //gather the data so that it will persist using playerstore
            }
        }
    }

    fun saveText(text: String) {
        viewModelScope.launch {
            playerStore.saveText(text) //save the data so that it will persist using playerstore
        }
    }
//    fun isChecked(isChecked: Boolean) {
//        viewModelScope.launch {
//            playerStore.saveText(text)
//        }
    }



