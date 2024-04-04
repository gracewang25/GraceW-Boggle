package com.example.gracew_boggle



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class GameFragment : Fragment() {

    private var selectedLetters = mutableListOf<Button>()
    private lateinit var gameActions: GameActions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameActions = activity as? GameActions ?: throw ClassCastException("Hosting activity must implement GameActions")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        setupBoard(view)
        return view
    }
    private fun generateRandomLetters(): List<Char> {
        val vowels = listOf('A', 'E', 'I', 'O', 'U')
        val consonants = ('A'..'Z').filterNot { it in vowels }
        return (List(8) { vowels.random() } + List(8) { consonants.random() }).shuffled()
    }

    private fun setupBoard(view: View) {
        val gridLayout = view.findViewById<GridLayout>(R.id.gridLayout)
        val letters = generateRandomLetters()

        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            val letter = letters[i].toString()
            button.text = letter

            button.setOnClickListener {
                val row = i / gridLayout.columnCount
                val col = i % gridLayout.columnCount
                handleLetterClick(button, row, col)
            }

        }
    }
    private fun handleLetterClick(button: Button, row: Int, col: Int) {
        if (selectedLetters.isEmpty() || isValidAdjacentButton(button, row, col)) {
            selectedLetters.add(button)
            button.isEnabled = false
            button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.selected_letter))

            // Update the currently selected word and display it
            val selectedWord = selectedLetters.joinToString(separator = "") { it.text.toString() }
            gameActions.updateSelectedWord(selectedWord)
        } else {
            Toast.makeText(context, "You may only select connected letters", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidAdjacentButton(button: Button, row: Int, col: Int): Boolean {
        val lastButton = selectedLetters.last()
        val (lastRow, lastCol) = getPositionInGrid(lastButton)

        // Check if the current button is adjacent to the last selected button
        return (Math.abs(lastRow - row) <= 1) && (Math.abs(lastCol - col) <= 1)
    }

    private fun getPositionInGrid(button: Button): Pair<Int, Int> {
        val gridLayout = view?.findViewById<GridLayout>(R.id.gridLayout) ?: return Pair(-1, -1)
        val index = gridLayout.indexOfChild(button)
        return Pair(index / gridLayout.columnCount, index % gridLayout.columnCount)
    }

    fun clearSelection() {
        selectedLetters.forEach { it.isEnabled = true }
        selectedLetters.clear()
        gameActions.updateSelectedWord("")
    }

    companion object {
        fun newInstance() = GameFragment()
    }
}
