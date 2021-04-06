package com.vikram.doordashlite.ui.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.vikram.doordashlite.databinding.StoreItemViewBinding
import com.vikram.doordashlite.model.Store

/**
 *   Created by vikram.gupta on 4/4/21.
 */
class StoreFeedAdapter(private val onItemClick: (Int) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val stores = mutableListOf<Store>()

    fun updateList(@NonNull stores: List<Store>) {
        this.stores.clear()
        this.stores.addAll(stores)
        notifyDataSetChanged()
    }

    inner class StoreViewHolder(val binding: StoreItemViewBinding, onItemClick: (Int) -> Unit): RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StoreViewHolder(StoreItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)) {
            onItemClick(it)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as StoreViewHolder).binding.store = stores[position]
    }

    override fun getItemCount(): Int {
        return stores.size
    }
}