package com.sherry.nyttopstories.ui.stories

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sherry.nyttopstories.databinding.ItemTopStoryBinding
import com.sherry.nyttopstories.model.StoryResult
import com.sherry.nyttopstories.utils.loadImageFromURL

class StoryAdapter(private val click: (StoryResult) -> Unit) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    private var storyList: List<StoryResult> = emptyList()

    fun setList(stories: List<StoryResult>) {
        storyList = stories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = storyList[position]
        holder.bind(item, click)
    }

    override fun getItemCount(): Int = storyList.size

    class ViewHolder private constructor(private val binding: ItemTopStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StoryResult, click: (StoryResult) -> Unit) {
            with(binding) {
                tvStoryTitle.text = item.title
                tvAuthorName.text = item.byline
                item.multimedia?.let { media ->
                    loadImageFromURL(root.context, media[0].url, ivStory)
                }
                root.setOnClickListener {
                    click.invoke(item)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTopStoryBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}