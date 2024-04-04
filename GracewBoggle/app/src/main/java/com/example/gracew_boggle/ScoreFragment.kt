package com.example.gracew_boggle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import android.widget.Button
import android.widget.TextView


class ScoreFragment : Fragment() {

    private var score = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_score, container, false)

        view.findViewById<Button>(R.id.submitButton).setOnClickListener {
            // Submit word
        }

        view.findViewById<Button>(R.id.clearButton).setOnClickListener {
            // Clear selection
        }

        view.findViewById<Button>(R.id.newGameButton).setOnClickListener {
            // Start new game
        }

        return view
    }

    fun validateWord(word: String) {
        // Validate word and update score
    }

    companion object {
        fun newInstance() = ScoreFragment()
    }
}