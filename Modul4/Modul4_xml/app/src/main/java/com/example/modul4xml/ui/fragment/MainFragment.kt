package com.example.modul4xml.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modul4xml.R
import com.example.modul4xml.data.ContentCard
import com.example.modul4xml.databinding.FragmentMainBinding
import com.example.modul4xml.ui.adapter.CardAdapter
import com.example.modul4xml.viewmodel.CardViewModel
import com.example.modul4xml.viewmodel.CardViewModelFactory
import com.example.modul4xml.viewmodel.UiEvent
import kotlinx.coroutines.launch
import timber.log.Timber

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelFactory = CardViewModelFactory("MainFragment")
        val viewModel = ViewModelProvider(this, viewModelFactory)[CardViewModel::class.java]

        val adapter = CardAdapter(
            onDetailClick = viewModel::onDetailClick,
            onUrlClick = viewModel::onUrlClick
        )

        var latestItems: List<ContentCard> = emptyList()

        binding.rvFeatured.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvAllContent.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvFeatured.adapter = adapter
        binding.rvAllContent.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.items.collect { items ->
                        latestItems = items
                        Timber.d("List submitted: count=%d", items.size)
                        adapter.updateData(items)
                    }
                }
                launch {
                    viewModel.event.collect { event ->
                        when (event) {
                            is UiEvent.OpenDetail -> {
                                val selected = latestItems.firstOrNull { it.id == event.cardId }
                                Timber.d("Navigate detail: id=%d, data=%s", event.cardId, selected)
                                findNavController().navigate(
                                    R.id.action_mainFragment_to_detailFragment,
                                    bundleOf("cardId" to event.cardId)
                                )
                                viewModel.clearEvent()
                            }
                            is UiEvent.OpenUrl -> {
                                Timber.d("Open url: %s", event.url)
                                val browserIntent = Intent(Intent.ACTION_VIEW, event.url.toUri())
                                startActivity(browserIntent)
                                viewModel.clearEvent()
                            }
                            null -> Unit
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}