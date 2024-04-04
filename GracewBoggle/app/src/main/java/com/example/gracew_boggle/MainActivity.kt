package com.example.gracew_boggle


import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import com.example.gracew_boggle.GameFragment
import com.example.gracew_boggle.ScoreFragment


class MainActivity : AppCompatActivity(), GameActions {

    private var currentScore = 0
    private lateinit var wordList: Set<String>
    private var selectedWordTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        selectedWordTextView = findViewById(R.id.currentWordTextView)
        wordList = loadDictionaryWords()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.gameFragmentContainer, GameFragment.newInstance())
                .add(R.id.scoreFragmentContainer, ScoreFragment.newInstance())
                .commit()
        }
    }

    override fun onSubmitWord(word: String) {
        if (wordList.contains(word.toLowerCase()) && isValidWord(word)) {
            val score = calculateScore(word)
            currentScore += score
            Toast.makeText(this, "That's correct, +$score", Toast.LENGTH_SHORT).show()
        } else {
            currentScore -= 10
            Toast.makeText(this, "That's incorrect, -10", Toast.LENGTH_SHORT).show()
        }
        updateScoreFragment()
    }

    override fun onClearWord() {
        val gameFragment = supportFragmentManager.findFragmentById(R.id.gameFragmentContainer) as? GameFragment
        gameFragment?.clearSelection()
    }

    override fun onNewGame() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.gameFragmentContainer, GameFragment.newInstance())
            .commit()
        currentScore = 0
        updateScoreFragment()
    }

    override fun updateSelectedWord(word: String) {
        selectedWordTextView?.text = word
    }

    private fun loadDictionaryWords(): Set<String> {
        val words = mutableSetOf<String>()
        val reader = BufferedReader(InputStreamReader(values.open("words.txt")))
        var line = reader.readLine()
        while (line != null) {
            words.add(line.trim())
            line = reader.readLine()
        }
        reader.close()
        return words
    }

    private fun isValidWord(word: String): Boolean {
        // Additional validation based on the game's criteria can be implemented here
        return word.length >= 4 && word.count { it in listOf('A', 'E', 'I', 'O', 'U') } >= 2
    }

    private fun calculateScore(word: String): Int {
        var score = word.count { it !in listOf('A', 'E', 'I', 'O', 'U') } +
                word.count { it in listOf('A', 'E', 'I', 'O', 'U') } * 5
        if (word.any { it in listOf('S', 'Z', 'P', 'X', 'Q') }) score *= 2
        return score
    }

    private fun updateScoreFragment() {
        val scoreFragment = supportFragmentManager.findFragmentById(R.id.scoreFragmentContainer) as? ScoreFragment
        scoreFragment?.updateScore(currentScore)
    }
}

interface GameActions {
    fun onSubmitWord(word: String)
    fun onClearWord()
    fun onNewGame()
    fun updateSelectedWord(word: String)
}
