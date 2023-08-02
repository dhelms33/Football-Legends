package edu.msudenver.cs3013.project03


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel() {
    // TODO: Create a private val _location variable that is a MutableLiveData<String>
    private val _location = MutableLiveData<String>()

    // TODO: Create a public val location variable that is a LiveData<String>
    val location: LiveData<String> get() = _location

    init {
        // TODO: Initialize the _location variable to a default value with the postValue function in the init block
        _location.postValue("Default value")
    }

    // TODO: Create a function called updateLocation that takes a String and updates the _location variable with the postValue function
    fun updateLocation(newLocation: String) {
        _location.postValue(newLocation)
    }
}
