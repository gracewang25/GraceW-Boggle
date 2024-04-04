package com.example.gracew_boggle



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class ScoreFragment : Fragment() {

    private var scoreTextView: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_score, container, false)
        scoreTextView = view.findViewById(R.id.scoreTextView)
        return view
    }

    fun updateScore(score: Int) {
        scoreTextView?.text = "Score: $score"
    }

    companion object {
        fun newInstance() = ScoreFragment()
    }
}
