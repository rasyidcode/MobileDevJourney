package com.rasyidcode.remarsphotos_dotaheroes.herolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.rasyidcode.remarsphotos_dotaheroes.databinding.FragmentHeroListBinding

class HeroListFragment : Fragment() {

    private val viewModel: HeroListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHeroListBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.heroList.adapter = HeroListAdapter()

        return binding.root
    }

}