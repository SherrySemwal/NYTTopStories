package com.sherry.nyttopstories.ui.stories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sherry.nyttopstories.R
import com.sherry.nyttopstories.databinding.FragmentStoryDetailsBinding
import com.sherry.nyttopstories.ui.StoriesViewModel
import com.sherry.nyttopstories.utils.loadImageFromURL

/**
 * [StoryDetailsFragment] Fragment to show the story details
 */
class StoryDetailsFragment : Fragment() {

    private val storyViewModel: StoriesViewModel by activityViewModels()
    private lateinit var binding: FragmentStoryDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
    }

    private fun initObserver(){
        storyViewModel.storyDetailsData.observe(viewLifecycleOwner){ story ->
            with(binding){
                tvStoryTitle.text = story.title
                tvDescription.text = story._abstract
                tvAuthorName.text = story.byline
                story.multimedia?.let { media ->
                    loadImageFromURL(root.context, media[0].url, ivStory)
                }
                tvSeeMore.setOnClickListener {
                    storyViewModel.webViewLink = story.url
                    findNavController().navigate(R.id.action_storyDetailsFragment_to_seeMoreFragment)
                }
            }
        }
    }
}