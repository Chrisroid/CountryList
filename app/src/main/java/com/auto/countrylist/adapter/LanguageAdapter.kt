package com.auto.countrylist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.auto.countrylist.domain.model.LanguageModel
import com.auto.countrylist.databinding.LanguageListItemBinding


class LanguageAdapter(private val onItemClicked: (LanguageModel) -> Unit) :
    ListAdapter<LanguageModel , LanguageAdapter.LanguageViewHolder>(DiffCallback)
{

    private var lastCheckedPosition = -1

    class LanguageViewHolder(private val binding: LanguageListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
    {
       fun bind(languageModel: LanguageModel , selected: Boolean)
        {
            binding.languageName.text = languageModel.languageName
            binding.languageChecked.isChecked = selected
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder
    {
        return LanguageViewHolder(
            LanguageListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: LanguageViewHolder , position: Int)
    {
        val current = getItem(position)
        holder.bind(current , position == lastCheckedPosition)
        holder.itemView.setOnClickListener {
            lastCheckedPosition = holder.adapterPosition
            notifyDataSetChanged()
        }
    }
    companion object
    {
        private val DiffCallback = object : DiffUtil.ItemCallback<LanguageModel>()
        {
            override fun areItemsTheSame(oldItem: LanguageModel , newItem: LanguageModel): Boolean
            {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: LanguageModel ,
                newItem: LanguageModel ,
            ): Boolean
            {
                return oldItem.languageName == newItem.languageName
            }
        }
    }
}