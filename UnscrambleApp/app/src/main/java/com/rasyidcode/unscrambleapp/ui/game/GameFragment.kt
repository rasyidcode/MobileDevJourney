package com.rasyidcode.unscrambleapp.ui.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        Log.d("GameFragment", "GameFragment created/re-created!")
        Log.d(
            "GameFragment", "Word: ${viewModel.currentScrambledWord} " +
                    "Score: ${viewModel.score} WordCount: ${viewModel.currentWordCount}"
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gameViewModel = viewModel
        binding.maxNoOfWords = MAX_NO_OF_WORDS
        binding.lifecycleOwner = viewLifecycleOwner

        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }

//        viewModel.currentScrambledWord.observe(viewLifecycleOwner) { newWord ->
//            binding.unscrambleWord.text = newWord
//        }
//        viewModel.score.observe(viewLifecycleOwner) { newScore ->
//            binding.score.text = getString(R.string.score, newScore)
//        }
//        viewModel.currentWordCount.observe(viewLifecycleOwner) { newWordCount ->
//            binding.wordCount.text = getString(R.string.word_count, newWordCount, MAX_NO_OF_WORDS)
//        }
    }

    private fun onSkipWord() {
        if (viewModel.nextWord()) {
            setErrorTextField(false)
        } else {
            showFinalScoreDialog()
        }
    }

    private fun onSubmitWord() {
        val playerWord = binding.textInputEdittext.text.toString()

        if (viewModel.isUserWordCorrect(playerWord)) {
            setErrorTextField(false)

            if (!viewModel.nextWord()) {
                showFinalScoreDialog()
            }
        } else {
            setErrorTextField(true)
        }
    }

    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textInputLayout.isErrorEnabled = true
            binding.textInputLayout.error = getString(R.string.try_again)
        } else {
            binding.textInputLayout.isErrorEnabled = false
            binding.textInputEdittext.text = null
        }
    }

    private fun restartGame() {
        viewModel.reinitializeData()
        setErrorTextField(false)
    }

    private fun exitGame() {
        activity?.finish()
    }

    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.you_scored, viewModel.score.value))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)) { _, _ ->
                exitGame()
            }
            .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                restartGame()
            }
            .show()
    }

    override fun onDetach() {
        super.onDetach()

        Log.d("GameFragment", "GameFragment destroyed!")
    }

}