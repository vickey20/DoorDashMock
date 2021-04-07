package com.vikram.doordashlite.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vikram.doordashlite.databinding.MenuHeaderLayoutBinding
import com.vikram.doordashlite.databinding.MenuItemLayoutBinding
import com.vikram.doordashlite.databinding.StoreDetailLayoutBinding
import com.vikram.doordashlite.model.StoreDetail
import com.vikram.doordashlite.model.StoreMenuItem

/**
 *   Created by vikram.gupta on 4/6/21.
 */
class StoreDetailAdapter(val onMenuItemClick: (menuItem: StoreMenuItem?) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val STORE_DETAIL = 0
        private const val MENU_HEADER = 1
        private const val MENU_ITEM = 2
    }
    private var storeDetail: StoreDetail? = null
    private var menuItems = emptyList<StoreMenuItem>()

    fun updateList(storeDetail: StoreDetail, menuItems: List<StoreMenuItem>) {
        this.storeDetail = storeDetail
        this.menuItems = menuItems
        notifyDataSetChanged()
    }

    inner class StoreDetailViewHolder(val binding: StoreDetailLayoutBinding): RecyclerView.ViewHolder(binding.root)
    inner class MenuHeaderViewHolder(val binding: MenuHeaderLayoutBinding): RecyclerView.ViewHolder(binding.root)

    inner class MenuItemViewHolder(val binding: MenuItemLayoutBinding, onItemClick: (Int) -> Unit): RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition - 2)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            STORE_DETAIL -> {
                return StoreDetailViewHolder(
                        StoreDetailLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            MENU_HEADER -> {
                return MenuHeaderViewHolder(
                        MenuHeaderLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            MENU_ITEM -> {
                return MenuItemViewHolder(
                        MenuItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ) {
                    onMenuItemClick(menuItems[it])
                }
            }
        }
        return super.createViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0) {
            (holder as StoreDetailViewHolder).binding.storeDetail = storeDetail
        } else if (position >= 2) {
            (holder as MenuItemViewHolder).binding.menuItem = menuItems[position - 2]
        }
    }

    override fun getItemCount(): Int {
        return menuItems.size + 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> {
                STORE_DETAIL
            }
            1 -> {
                MENU_HEADER
            }
            else -> {
                MENU_ITEM
            }
        }
    }
}