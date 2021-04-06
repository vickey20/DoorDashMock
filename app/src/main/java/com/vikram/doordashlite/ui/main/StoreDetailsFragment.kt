package com.vikram.doordashlite.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.vikram.doordashlite.MainActivity
import com.vikram.doordashlite.databinding.StoreDetailsFragmentBinding

/**
 *   Created by vikram.gupta on 4/5/21.
 */
class StoreDetailsFragment: Fragment() {

    companion object {
        fun newInstance() = StoreDetailsFragment()
    }

    private lateinit var binding: StoreDetailsFragmentBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        if (savedInstanceState == null) {
            viewModel.getCurrentStoreId()?.let {
                viewModel.getStoreDetail(it)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = StoreDetailsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as MainActivity).setToolbar(binding.storeDetailToolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.storeDetailErrorLiveData.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, getString(it), Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
                (activity as MainActivity).onBackPressed()
            }
        }
        return true
    }
}