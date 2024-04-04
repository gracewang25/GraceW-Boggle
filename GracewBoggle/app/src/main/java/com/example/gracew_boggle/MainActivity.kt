package com.example.gracew_boggle

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    override fun onSubmitWord(word: String) {
        val scoreFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as? ScoreFragment
        scoreFragment?.validateWord(word)
    }

    override fun onClearWord() {
        val gameFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as? GameFragment
        gameFragment?.clearSelection()
    }

    override fun onNewGame() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, GameFragment.newInstance())
            .commitNow()

    }
    override fun updateSelectedWord(word: String) {
        // Update the UI or pass the word to the ScoreFragment for validation
        // For example, if you have a TextView that displays the current word:
        val wordTextView = findViewById<TextView>(R.id.currentWordTextView)
        wordTextView.text = word
    }
}

interface GameActions {
    fun onSubmitWord(word: String)
    fun onClearWord()
    fun onNewGame()
    fun updateSelectedWord(word: String)
}
