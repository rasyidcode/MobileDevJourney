package com.rasyidcode.wordapp4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.rasyidcode.wordapp4.databinding.ListItemBinding

class LetterAdapter : RecyclerView.Adapter<LetterAdapter.LetterViewHolder>() {

    private val list = ('A').rangeTo('Z').toList()

    class LetterViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(letter: Char) {
            with(binding) {
                btnItem.text = letter.toString()
                btnItem.setOnClickListener {
                    val action =
                        LetterListFragmentDirections.actionLetterListFragmentToWordListFragment(
                            letter = letter.toString()
                        )

                    itemView.findNavController().navigate(action)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
        val adapterLayout =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        adapterLayout.root.accessibilityDelegate = Accessibility

        return LetterViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        holder.bind(list[position])
    }

    companion object Accessibility : View.AccessibilityDelegate() {
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