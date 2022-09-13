package com.sherry.nyttopstories.ui.stories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sherry.nyttopstories.R
import com.sherry.nyttopstories.databinding.FragmentTopStoriesBinding
import com.sherry.nyttopstories.model.StoryResult
import com.sherry.nyttopstories.ui.StoriesViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * [TopStoriesFragment] To load all top stories
 * */
@AndroidEntryPoint
class TopStoriesFragment : Fragment() {

    private val storiesViewModel: StoriesViewModel by activityViewModels()
    private lateinit var binding: FragmentTopStoriesBinding
    private lateinit var adapter: StoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTopStoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initObserver()
    }

    private fun initUI(){
        adapter = StoryAdapter {
            storiesViewModel.setStoryDetailsData(it)
            findNavController().navigate(R.id.action_storiesFragment_to_storyDetailsFragment)
        }
        binding.recyclerView.adapter = adapter
    }

    private fun initObserver() {
        with(storiesViewModel) {
            storiesMutableList.observe(viewLifecycleOwner, observeStationListData)

            getIsLoadingLiveData().observe(viewLifecycleOwner) {
                if (it) binding.pb.visibility = View.VISIBLE else binding.pb.visibility = View.GONE
            }

            getDisplayErrorLiveData().observe(viewLifecycleOwner) { error ->
                Toast.makeText(
                    requireContext(), error,
                    Toast.LENGTH_LONG
                ).show()
            }

            networkError().observe(viewLifecycleOwner) {
                if (it) Toast.makeText(
                    requireContext(), requireContext().resources.getString(R.string.network_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private val observeStationListData = Observer<List<StoryResult>> { result ->
        this.adapter.setList(result)
    }

}