package com.rasyidcode.wordsapp2

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.rasyidcode.wordsapp2.databinding.ListItemBinding

class WordAdapter(
    private val letterId: String,
    private val context: Context
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    private val filteredWords: List<String>

    init {
        val words = context.resources.getStringArray(R.array.words).toList()

        filteredWords = words
            .filter { it.startsWith(letterId, ignoreCase = true) }
            .shuffled()
            .take(5)
            .sorted()
    }

    class WordViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(word: String) {
            with(binding) {
                btnItem.text = word
                btnItem.setOnClickListener {
                    val queryUrl: Uri = Uri.parse("${WordListFragment.SEARCH_PREFIX}$word")
                    val intent = Intent(Intent.ACTION_VIEW, queryUrl)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val adapterLayout =
            ListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        adapterLayout.root.accessibilityDelegate = Accessibility

        return WordViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int = filteredWords.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(filteredWords[position])
    }

    companion object Accessibility : View.AccessibilityDelegate() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(host, info)

            val customString = host.context?.getString(R.string.look_up_words)
            val customClick = AccessibilityNodeInfo.AccessibilityAction(
                AccessibilityNodeInfo.ACTION_CLICK,
                customString
            )

            info.addAction(customClick)
        }
    }

}