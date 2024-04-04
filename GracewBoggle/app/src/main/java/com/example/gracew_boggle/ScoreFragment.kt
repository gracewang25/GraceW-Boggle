package com.example.gracew_boggle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class ScoreFragment : Fragment() {

    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        // Perform word validation by checking against a dictionary file

        val isValid = checkWordValidity(word)
        if (isValid) {
            // Word is valid, update score
            val wordScore = calculateWordScore(word)
            score += wordScore
            updateScoreTextView()
        } else {
            // Word is invalid, display error message
            requireActivity().runOnUiThread {
                Toast.makeText(requireContext(), "Invalid word", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun checkWordValidity(word: String): Boolean {
        // Load dictionary file and check if the word is present
        val dictionaryURL = "https://raw.githubusercontent.com/dwyl/english-words/master/words.txt"
        var inputStream: InputStream? = null
        var bufferedReader: BufferedReader? = null
        var connection: HttpsURLConnection? = null
        var wordFound = false

        try {
            val url = URL(dictionaryURL)
            connection = url.openConnection() as HttpsURLConnection
            inputStream = connection.inputStream
            bufferedReader = BufferedReader(InputStreamReader(inputStream))

            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                if (line == word) {
                    wordFound = true
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            bufferedReader?.close()
            inputStream?.close()
            connection?.disconnect()
        }

        return wordFound
    }

    private fun calculateWordScore(word: String): Int {
        var score = 0

        for (letter in word) {
            if (letter.isConsonant()) {
                score += 1 // Every consonant is worth one point
                if (letter in listOf('S', 'Z', 'P', 'X', 'Q')) {
                    score += 1 // If the consonant is one of 'S', 'Z', 'P', 'X', or 'Q', double the score
                }
            } else if (letter.isVowel()) {
                score += 5 // Every vowel is worth five points
            }
        }

        return score
    }

    private fun updateScoreTextView() {
        // Update score TextView implementation goes here
        // For example:
        val scoreTextView = view?.findViewById<TextView>(R.id.scoreTextView)
        scoreTextView?.text = "Score: $score"
    }

    private fun Char.isVowel() = this in listOf('A', 'E', 'I', 'O', 'U') || this in listOf('a', 'e', 'i', 'o', 'u')

    private fun Char.isConsonant() = this.isLetter() && !this.isVowel()

    companion object {
        fun newInstance() = ScoreFragment()
    }
}
