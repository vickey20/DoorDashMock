package com.vikram.doordashlite.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vikram.doordashlite.R
import com.vikram.doordashlite.model.Store
import com.vikram.doordashlite.model.StoreDetail
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

    val storeDetailLiveData = MutableLiveData<StoreDetail>()
    val storeDetailLoadingLiveData = MutableLiveData<Boolean>()
    val storeDetailErrorLiveData = MutableLiveData<Int>()

    var selectedPosition: Int? = null
    fun getCurrentStoreId(): Int? {
        return storesLiveData.value?.let { stores -> selectedPosition?.let { stores[it].id } }
    }

    fun getStoreFeed(
        lat: Double = DEFAULT_LAT,
        lng: Double = DEFAULT_LNG,
        offset: Int = DEFAULT_OFFSET,
        limit: Int = DEFAULT_LIMIT
    ) {
        disposables.add(
            repository.getStoreFeed(lat, lng, offset, limit)
                .doOnSubscribe {
                    loadingLiveData.postValue(true)
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

    fun getStoreDetail(id: Int) {
        disposables.add(
            repository.getStoreDetail(id)
                .doOnSubscribe {
                    storeDetailLoadingLiveData.postValue(true)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    storeDetailLoadingLiveData.value = false
                    storeDetailLiveData.value = processStoreDetail(it)
                }, {
                    storeDetailLoadingLiveData.value = false
                    storeDetailErrorLiveData.value = R.string.error_store_feed
                })
        )
    }

    private fun processStoreDetail(storeDetail: StoreDetail): StoreDetail {
        storesLiveData.value?.let { stores ->
            selectedPosition?.let { position ->
                storeDetail.name = stores[position].name
                storeDetail.menus = stores[position].menus
                storeDetail.distance = stores[position].getDistanceString()
                if (storeDetail.headerImgUrl.isNullOrBlank()) {
                    if (stores[position].headerImgUrl.isNullOrBlank()) {
                        storeDetail.headerImgUrl = stores[position].coverImgUrl
                    } else {
                        storeDetail.headerImgUrl = stores[position].headerImgUrl
                    }
                }
            }
        }
        return storeDetail
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}