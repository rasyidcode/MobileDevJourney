package com.rasyidcode.movieapp.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rasyidcode.movieapp.databinding.FragmentNowPlayingBinding
import com.rasyidcode.movieapp.databinding.FragmentTopRatedBinding

class FragmentTopRated : Fragment() {

    private var _binding: FragmentTopRatedBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopRatedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}