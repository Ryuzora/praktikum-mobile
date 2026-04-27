package com.example.modul3xml

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modul3xml.databinding.ItemCardBinding

class CardAdapter(
    private val data: List<ContentCard>,
    private val onDetailClick: (Int) -> Unit,
    private val onUrlClick: (String) -> Unit
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    inner class CardViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(card: ContentCard) {
            binding.ivImage.setImageResource(card.image)
            binding.tvTitle.text = card.title
            binding.tvAge.text = binding.root.context.getString(R.string.age_years, card.age)

            val agencyPrefix = binding.root.context.getString(R.string.agency)
            binding.tvAgency.text = "$agencyPrefix: ${card.description}"

            binding.btnDetail.setOnClickListener {
                onDetailClick(card.id)
            }

            binding.btnUrl.setOnClickListener {
                onUrlClick(card.link)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size
}