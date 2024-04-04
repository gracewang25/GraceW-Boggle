package com.example.gracew_boggle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    // LiveData property to hold the score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> = _score

    // Initialize the score with a default value
    init {
        _score.value = 0
    }

    // Function to update the score
    fun updateScore(newScore: Int) {
        _score.value = newScore
    }

    // ViewModel lifecycle method
    override fun onCleared() {
        super.onCleared()
        // Clean up resources here if needed
    }
}

