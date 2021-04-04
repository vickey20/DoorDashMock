package com.vikram.doordashlite.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vikram.doordashlite.R
import com.vikram.doordashlite.model.Store
import com.vikram.doordashlite.repo.DoorDashRepository
import com.vikram.doordashlite.repo.DoorDashRepository.Companion.DEFAULT_LAT
import com.vikram.doordashlite.repo.DoorDashRepository.Companion.DEFAULT_LIMIT
import com.vikram.doordashlite.repo.DoorDashRepository.Companion.DEFAULT_LNG
import com.vikram.doordashlite.repo.DoorDashRepository.Companion.DEFAULT_OFFSET
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(private val repository: DoorDashRepository) : ViewModel() {

    private val disposables = CompositeDisposable()

    val storesLiveData = MutableLiveData<List<Store>>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<Int>()

    fun getStoreFeed(
        lat: Double = DEFAULT_LAT,
        lng: Double = DEFAULT_LNG,
        offset: Int = DEFAULT_OFFSET,
        limit: Int = DEFAULT_LIMIT
    ) {
        disposables.add(
            repository.getStoreFeed(lat, lng, offset, limit)
                .doOnSubscribe {
                    loadingLiveData.value = true
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    loadingLiveData.value = false
                    storesLiveData.value = it.stores
                }, {
                    loadingLiveData.value = false
                    errorLiveData.value = R.string.error_store_feed
                })
        )
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}