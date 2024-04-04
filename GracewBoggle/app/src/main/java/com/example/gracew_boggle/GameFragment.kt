package com.example.gracew_boggle

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast

class GameFragment : Fragment() {

    private var selectedLetters = mutableListOf<Button>()
    private lateinit var gameActions: GameActions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameActions = activity as GameActions
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        setupBoard(view)
        return view
    }

    private fun setupBoard(view: View) {
        val gridLayout = view.findViewById<GridLayout>(R.id.gridLayout)
        val letters = generateRandomLetters()

        var buttonIndex = 0
        for (i in 0 until gridLayout.rowCount) {
            for (j in 0 until gridLayout.columnCount) {
                val button = gridLayout.getChildAt(buttonIndex) as? Button
                button?.text = letters[i * gridLayout.columnCount + j].toString()
                button?.setOnClickListener { handleLetterClick(button, i, j) }
                buttonIndex++
            }
        }
    }


    private fun handleLetterClick(button: Button, row: Int, col: Int) {
        if (selectedLetters.isEmpty() || isValidAdjacentButton(button, row, col)) {
            selectedLetters.add(button)
            button.isEnabled = false  // Disable the button to prevent re-selection
            updateSelectedWord()
        } else {
            Toast.makeText(context, "Letters must be adjacent", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidAdjacentButton(button: Button, row: Int, col: Int): Boolean {
        if (selectedLetters.contains(button)) return false  // Prevent reusing the same letter

        val lastButton = selectedLetters.last()
        val lastPos = getPositionInGrid(lastButton)
        val currentPos = Pair(row, col)

        // Check if the current button is adjacent to the last selected button
        return Math.abs(lastPos.first - currentPos.first) <= 1 && Math.abs(lastPos.second - currentPos.second) <= 1
    }

    private fun getPositionInGrid(button: Button): Pair<Int, Int> {
        val gridLayout = view?.findViewById<GridLayout>(R.id.gridLayout) ?: return Pair(-1, -1)
        val index = gridLayout.indexOfChild(button)
        return Pair(index / gridLayout.columnCount, index % gridLayout.columnCount)
    }

    private fun generateRandomLetters(): List<Char> {
        val vowels = listOf('A', 'E', 'I', 'O', 'U')
        val consonants = ('A'..'Z').filterNot { it in vowels }
        return (List(8) { vowels.random() } + List(8) { consonants.random() }).shuffled()
    }
    private fun updateSelectedWord() {
        val selectedWord = selectedLetters.joinToString(separator = "") { it.text.toString() }
        gameActions.updateSelectedWord(selectedWord)
    }

    fun clearSelection() {
        selectedLetters.forEach { it.isEnabled = true }
        selectedLetters.clear()
        updateSelectedWord()
    }

    companion object {
        fun newInstance() = GameFragment()
    }
}