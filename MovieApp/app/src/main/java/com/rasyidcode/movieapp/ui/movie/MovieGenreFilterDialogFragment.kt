package com.rasyidcode.movieapp.ui.movie

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.rasyidcode.movieapp.R
import com.rasyidcode.movieapp.databinding.FragmentMovieGenreFilterDialogBinding

class MovieGenreFilterDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = FragmentMovieGenreFilterDialogBinding.inflate(layoutInflater)

        return activity?.let {
            val builder = AlertDialog.Builder(it)
                .setTitle(R.string.filter_genre)
                .setView(binding.root)
                .setPositiveButton("Filter") { _, _ ->

                }
                .setNegativeButton("Cancel") { _, _ -> }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}