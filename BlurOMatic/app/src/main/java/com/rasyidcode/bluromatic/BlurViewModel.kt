package com.rasyidcode.bluromatic

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.constraintlayout.solver.state.State.Constraint
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.rasyidcode.bluromatic.workers.BlurWorker
import com.rasyidcode.bluromatic.workers.CleanupWorker
import com.rasyidcode.bluromatic.workers.SaveImageToFileWorker

class BlurViewModel(application: Application) : ViewModel() {

    private val workManager = WorkManager.getInstance(application)

    internal var imageUri: Uri? = null
    internal var outputUri: Uri? = null

    internal val outputWorkInfos: LiveData<List<WorkInfo>>

    init {
        imageUri = getImageUri(application.applicationContext)
        // This transformation makes sure that whenever the current work Id changes the WorkInfo
        // the UI is listening to changes
        outputWorkInfos = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
    }

    /**
     * Create the WorkRequest to apply the blur and save the resulting image
     * @param blurLevel The amount to blur the image
     */
    internal fun applyBlur(blurLevel: Int) {
        // Add WorkerRequest to Cleanup temporary image
//        var continuation = workManager
//            .beginWith(OneTimeWorkRequest.from(CleanupWorker::class.java))
        var continuation = workManager
            .beginUniqueWork(
                IMAGE_MANIPULATION_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(CleanupWorker::class.java)
            )

        // Add WorkRequest to blur the image the number of time requested
        for (i in 0 until blurLevel) {
            val blurBuilder = OneTimeWorkRequestBuilder<BlurWorker>()

            if (i == 0) {
                blurBuilder.setInputData(createInputDataForUri())
            }

            continuation = continuation.then(blurBuilder.build())
        }

        // Create charging constraint
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        // Add WorkerRequest to save the image to the filesystem
        val save = OneTimeWorkRequest
            .Builder(SaveImageToFileWorker::class.java)
            .setConstraints(constraints = constraints)
            .addTag(TAG_OUTPUT)
            .build()

        continuation = continuation.then(save)

        continuation.enqueue()
    }

    internal fun setOutputUri(outputImageUri: String?) {
        outputUri = uriOrNull(outputImageUri)
    }

    internal fun cancelWork() {
        workManager.cancelUniqueWork(IMAGE_MANIPULATION_WORK_NAME)
    }

    private fun uriOrNull(uriString: String?): Uri? {
        return if (!uriString.isNullOrEmpty()) {
            Uri.parse(uriString)
        } else {
            null
        }
    }

    private fun getImageUri(context: Context): Uri {
        val resources = context.resources

        return Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(R.drawable.android_cupcake))
            .appendPath(resources.getResourceTypeName(R.drawable.android_cupcake))
            .appendPath(resources.getResourceEntryName(R.drawable.android_cupcake))
            .build()
    }

    /**
     * Creates the input data bundle which includes the Uri to operate on
     * @return Data which contains the Image Uri as a String
     */
    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        imageUri?.let {
            builder.putString(KEY_IMAGE_URI, imageUri.toString())
        }
        return builder.build()
    }

    class BlurViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(BlurViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                BlurViewModel(application) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

}