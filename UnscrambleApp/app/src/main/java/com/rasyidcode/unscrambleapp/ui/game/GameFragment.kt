package com.rasyidcode.unscrambleapp.ui.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rasyidcode.unscrambleapp.R
import com.rasyidcode.unscrambleapp.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)

        Log.d("GameFragment", "GameFragment created/re-created!")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }

        updateNextWordOnScreen()

        binding.score.text = getString(R.string.score, viewModel.score)
        binding.wordCount.text = getString(R.string.word_count, 0, MAX_NO_OF_WORDS)
    }

    private fun updateNextWordOnScreen() {
        binding.unscrambleWord.text = viewModel.currentScrambledWord
    }

    private fun onSkipWord() {

    }

    private fun onSubmitWord() {

    }

    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textInputLayout.isErrorEnabled = true
            binding.textInputLayout.error = getString(R.string.try_again)
        } else {
            binding.textInputLayout.isErrorEnabled = false
            binding.textInputLayout.error = null
        }
    }

    private fun getNextScrambledWord(): String {
        val tempWord = allWordsList.random().toCharArray()
        tempWord.shuffle()
        return String(tempWord)
    }

    private fun restartGame() {
        setErrorTextField(false)
        updateNextWordOnScreen()
    }

    private fun exitGame() {
        activity?.finish()
    }

    override fun onDetach() {
        super.onDetach()

        Log.d("GameFragment", "GameFragment destroyed!")
    }

}