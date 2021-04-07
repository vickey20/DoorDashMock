package com.vikram.doordashlite.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vikram.doordashlite.MainActivity
import com.vikram.doordashlite.databinding.MainFragmentBinding
import com.vikram.doordashlite.network.DoorDashApi
import com.vikram.doordashlite.network.NetworkClient
import com.vikram.doordashlite.repo.DoorDashRepository

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: MainFragmentBinding

    private lateinit var viewModel: MainViewModel
    private val repository = DoorDashRepository(NetworkClient.getApiServiceFor(DoorDashApi::class.java))

    private val adapter = StoreFeedAdapter {
        viewModel.currentPosition = it
        (activity as MainActivity).showDetailView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), MainViewModelFactory(repository)).get(MainViewModel::class.java)
        if (savedInstanceState == null) {
            viewModel.getStoreFeed()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding =  MainFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setToolbar(binding.toolbar)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        setupRecyclerView()

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.storesLiveData.observe(viewLifecycleOwner) { adapter.updateList(it) }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, getString(it), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setupRecyclerView() {
        if (binding.recyclerView.layoutManager == null) {
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
        }

        if (binding.recyclerView.adapter != adapter) {
            binding.recyclerView.adapter = adapter
        }
    }
}