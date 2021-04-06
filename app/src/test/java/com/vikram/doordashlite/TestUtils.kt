package com.vikram.doordashlite

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.mockito.Mockito

/**
 *   Created by vikram.gupta on 4/4/21.
 */
internal const val DEFAULT_LAT = 37.422740
internal const val DEFAULT_LNG = -122.139956
internal const val DEFAULT_OFFSET = 0
internal const val DEFAULT_LIMIT = 50

internal fun <T> any(type: Class<T>): T = Mockito.any(type)

internal fun <T> LiveData<T>.testObserver() = TestObserver<T>().also { observeForever(it) }

internal class TestObserver<T> : Observer<T> {
    val observedValues = mutableListOf<T?>()
    override fun onChanged(value: T?) {
        observedValues.add(value)
    }
}

internal fun setupSchedulers() {
    RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
}