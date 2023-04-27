package com.rasyidcode.bluromatic

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
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

        // Observe work status, added in onCreate()
        viewModel.outputWorkInfos.observe(this, workInfosObserver())

        // Setup view output image file button
        binding.btnSeeFile.setOnClickListener {
            viewModel.outputUri?.let { currentUri ->
                val actionView = Intent(Intent.ACTION_VIEW, currentUri)
                actionView.resolveActivity(packageManager)?.run {
                    startActivity(actionView)
                }
            }
        }
    }

    private fun workInfosObserver(): Observer<List<WorkInfo>> {
        return Observer { listOfWorkInfo ->

            // Note that these next few lines grab a single WorkInfo if it exists
            // This code could be in a Transformation in the ViewModel; they are included here
            // so that the entire process of displaying a WorkInfo is in one location

            // If there are no matching work info, do nothing
            if (listOfWorkInfo.isEmpty()) {
                return@Observer
            }

            // We only care about the one output status.
            // Every continuation has only one worker tagged TAG_OUTPUT
            val workInfo = listOfWorkInfo[0]
            if (workInfo.state.isFinished) {
                showWorkFinished()

                // Normally this processing, which is not directly related to drawing views on
                // screen would be in the ViewModel. For simplicity we are keeping it here.
                val outputImageUri = workInfo.outputData.getString(KEY_IMAGE_URI)

                // If there is an output file show "See File" button
                if (!outputImageUri.isNullOrEmpty()) {
                    viewModel.setOutputUri(outputImageUri)
                    binding.btnSeeFile.visibility = View.VISIBLE
                }
            } else {
                showWorkInProgress()
            }

            // Hookup the Cancel button
            binding.btnCancel.setOnClickListener { viewModel.cancelWork() }
        }
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