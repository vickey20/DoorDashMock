package com.vikram.doordashlite.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vikram.doordashlite.R
import com.vikram.doordashlite.network.DoorDashApi
import com.vikram.doordashlite.network.NetworkClient
import com.vikram.doordashlite.repo.DoorDashRepository

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private val repository = DoorDashRepository(NetworkClient.getApiServiceFor(DoorDashApi::class.java))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}