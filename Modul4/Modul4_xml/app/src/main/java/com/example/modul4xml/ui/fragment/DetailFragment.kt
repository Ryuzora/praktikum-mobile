package com.example.modul4xml.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.modul4xml.data.CardData
import com.example.modul4xml.databinding.FragmentDetailBinding
import timber.log.Timber

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardId = requireArguments().getInt(ARG_CARD_ID, -1)
        Timber.d("Detail opened with id=%d", cardId)

        val card = CardData.find { it.id == cardId }
        if (card == null) {
            Timber.w("Detail data not found for id=%d", cardId)
            Toast.makeText(requireContext(), "Data not found", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
            return
        }

        Timber.d("Detail data: %s", card)

        binding.ivDetailImage.setImageResource(card.image)
        binding.tvDetailTitle.text = card.title
        binding.tvDetailDescription.text = card.description
        binding.tvDetailContent.text = card.content
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_CARD_ID = "cardId"
    }
}
