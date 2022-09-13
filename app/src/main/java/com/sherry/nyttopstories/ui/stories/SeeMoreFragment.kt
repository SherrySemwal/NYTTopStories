package com.sherry.nyttopstories.ui.stories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.sherry.nyttopstories.R
import com.sherry.nyttopstories.databinding.FragmentSeeMoreBinding
import com.sherry.nyttopstories.ui.StoriesViewModel

/**
 * [SeeMoreFragment] To load the webview
 */
class SeeMoreFragment : Fragment() {

    private val storyViewModel: StoriesViewModel by activityViewModels()
    private lateinit var binding: FragmentSeeMoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSeeMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        storyViewModel.webViewLink?.let { binding.webView.loadUrl(it) }
    }

}