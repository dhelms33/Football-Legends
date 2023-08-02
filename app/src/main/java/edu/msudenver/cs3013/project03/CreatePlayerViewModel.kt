package edu.msudenver.cs3013.project03
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.launch
//class CreatePlayerViewModel(private val PlayerStore: PlayerStore) : ViewModel() {
//
//
//
//        private val _textLiveData = MutableLiveData<String>()
//        val textLiveData: LiveData<String> = _textLiveData
//
//        init {
//            viewModelScope.launch {
//                PlayerStore.text.collect {
//                    _textLiveData.value = it
//                }
//            }
//        }
//
//        fun saveText(text: String) {
//            viewModelScope.launch {
//                PlayerStore.saveText(text)
//            }
//        }
//    }
