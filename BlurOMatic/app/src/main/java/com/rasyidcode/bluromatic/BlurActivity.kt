package com.rasyidcode.bluromatic

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rasyidcode.bluromatic.databinding.ActivityBlurBinding

class BlurActivity : AppCompatActivity() {

    private val viewModel by viewModels<BlurViewModel> {
        BlurViewModel.BlurViewModelFactory(
            application = application
        )
    }

    private lateinit var binding: ActivityBlurBinding

    private val blurLevel: Int
        get() = when (binding.radioBlurGroup.checkedRadioButtonId) {
            R.id.radio_blur_v1 -> 1
            R.id.radio_blur_v2 -> 2
            R.id.radio_blur_v3 -> 3
            else -> 1
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBlurBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGo.setOnClickListener { viewModel.applyBlur(blurLevel = blurLevel) }
    }

    /**
     * Shows and hides views for when the Activity is processing an image
     */
    private fun showWorkInProgress() {
        with(binding) {
            progressBar.visibility = View.VISIBLE
            btnCancel.visibility = View.VISIBLE
            btnGo.visibility = View.GONE
            btnSeeFile.visibility = View.GONE
        }
    }

    /**
     * Shows and hides views for when the Activity is done processing an image
     */
    private fun showWorkFinished() {
        with(binding) {
            progressBar.visibility = View.GONE
            btnCancel.visibility = View.GONE
            btnGo.visibility = View.VISIBLE
        }
    }

}