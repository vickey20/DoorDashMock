package com.vikram.doordashlite.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vikram.doordashlite.*
import com.vikram.doordashlite.model.Store
import com.vikram.doordashlite.model.StoreDetail
import com.vikram.doordashlite.model.StoreFeed
import com.vikram.doordashlite.repo.DoorDashRepository
import io.reactivex.rxjava3.core.Single
import org.junit.Assert.assertEquals
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations.initMocks

/**
 * Created by vikram.gupta on 4/4/21.
 */
class MainViewModelTest {

    @Mock
    private lateinit var repository: DoorDashRepository

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    private var mockedStores = mutableListOf<Store>()

    @Before
    fun setUp() {
        initMocks(this)
        viewModel = MainViewModel(repository)

        setupSchedulers()
        mockedStores = getMockedStores()
    }

    private fun getMockedStores(): MutableList<Store> {
        val stores = mutableListOf<Store>()
        for (i in 0 until 10) {
            stores.add(mock(Store::class.java))
        }
        return stores
    }

    @Test
    fun shouldUpdateLoadingLiveDataWhenLoading() {
        `when`(repository.getStoreFeed(
            any(Double::class.java),
            any(Double::class.java),
            any(Int::class.java),
            any(Int::class.java),
        )).thenReturn(
            Single.just(StoreFeed(mockedStores))
        )

        val loadingObserver = viewModel.loadingLiveData.testObserver()
        viewModel.getStoreFeed(DEFAULT_LAT, DEFAULT_LNG, DEFAULT_OFFSET, DEFAULT_LIMIT)
        assertEquals(listOf(true, false), loadingObserver.observedValues)
    }

    @Test
    fun shouldUpdateStoreFeedLiveDataWhenOnSuccess() {
        `when`(repository.getStoreFeed(
            any(Double::class.java),
            any(Double::class.java),
            any(Int::class.java),
            any(Int::class.java),
        )).thenReturn(
            Single.just(StoreFeed(mockedStores))
        )

        val storeFeedObserver = viewModel.storesLiveData.testObserver()
        viewModel.getStoreFeed(DEFAULT_LAT, DEFAULT_LNG, DEFAULT_OFFSET, DEFAULT_LIMIT)
        assertEquals(listOf(mockedStores), storeFeedObserver.observedValues)
    }

    @Test
    fun shouldUpdateErrorLiveDataWhenOnFailure() {
        `when`(repository.getStoreFeed(
            any(Double::class.java),
            any(Double::class.java),
            any(Int::class.java),
            any(Int::class.java),
        )).thenReturn(
            Single.error(Throwable())
        )

        val errorObserver = viewModel.errorLiveData.testObserver()
        viewModel.getStoreFeed(DEFAULT_LAT, DEFAULT_LNG, DEFAULT_OFFSET, DEFAULT_LIMIT)
        assertEquals(listOf(R.string.error_store_feed), errorObserver.observedValues)
    }

    @Test
    fun shouldUpdateStoreDetailLiveDataOnSuccess() {
        val mockedStoreDetail = mock(StoreDetail::class.java)
        `when`(repository.getStoreDetail(
                any(Int::class.java),
        )).thenReturn(
                Single.just(mockedStoreDetail)
        )

        val storeDetailObserver = viewModel.storeDetailLiveData.testObserver()
        viewModel.getStoreDetail(1001)
        assertEquals(listOf(mockedStoreDetail), storeDetailObserver.observedValues)
    }

    @Test
    fun shouldUpdateStoreDetailLoadingLiveDataWhenLoading() {
        val mockedStoreDetail = mock(StoreDetail::class.java)
        `when`(repository.getStoreDetail(
                any(Int::class.java),
        )).thenReturn(
                Single.just(mockedStoreDetail)
        )

        val storeDetailLoadingObserver = viewModel.storeDetailLoadingLiveData.testObserver()
        viewModel.getStoreDetail(1001)
        assertEquals(listOf(true, false), storeDetailLoadingObserver.observedValues)
    }

    @Test
    fun shouldUpdateStoreDetailErrorLiveDataOnFailure() {
        `when`(repository.getStoreDetail(
                any(Int::class.java),
        )).thenReturn(
                Single.error(Throwable())
        )

        val storeDetailErrorObserver = viewModel.storeDetailErrorLiveData.testObserver()
        viewModel.getStoreDetail(1001)
        assertEquals(listOf(R.string.error_store_detail), storeDetailErrorObserver.observedValues)
    }

    @Test
    fun shouldReturnCorrectStoreId() {
        `when`(repository.getStoreFeed(
                any(Double::class.java),
                any(Double::class.java),
                any(Int::class.java),
                any(Int::class.java),
        )).thenReturn(
                Single.just(StoreFeed(mockedStores))
        )

        val testPosition = 5
        viewModel.currentPosition = testPosition
        viewModel.getStoreFeed(DEFAULT_LAT, DEFAULT_LNG, DEFAULT_OFFSET, DEFAULT_LIMIT)

        assertEquals(mockedStores[testPosition].id, viewModel.getCurrentStoreId())
    }

    @Test
    fun shouldReturnCorrectCurrentStore() {
        `when`(repository.getStoreFeed(
                any(Double::class.java),
                any(Double::class.java),
                any(Int::class.java),
                any(Int::class.java),
        )).thenReturn(
                Single.just(StoreFeed(mockedStores))
        )

        val testPosition = 5
        viewModel.currentPosition = testPosition
        viewModel.getStoreFeed(DEFAULT_LAT, DEFAULT_LNG, DEFAULT_OFFSET, DEFAULT_LIMIT)

        assertEquals(mockedStores[testPosition], viewModel.getCurrentStore())
    }
}