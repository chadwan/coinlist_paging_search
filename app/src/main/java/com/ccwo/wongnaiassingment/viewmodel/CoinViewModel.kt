package com.ccwo.wongnaiassingment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ccwo.wongnaiassingment.data.CoinDataFactory
import com.ccwo.wongnaiassingment.data.CoinDataSource
import com.ccwo.wongnaiassingment.data.NetworkState
import com.ccwo.wongnaiassingment.model.CoinModel

class CoinViewModel : ViewModel() {
    var coinList: LiveData<PagedList<CoinModel>>
    private val pageSize = 10
    private var sourceFactory = CoinDataFactory("")

    var filterTextAll = MutableLiveData<String>()

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(true)
            .build()

//        coinList = LivePagedListBuilder<Int, CoinModel>(sourceFactory, config).build()

        coinList = Transformations.switchMap(filterTextAll) { input: String ->
            sourceFactory = CoinDataFactory(input);
            LivePagedListBuilder<Int, CoinModel>(sourceFactory, config).build()
        }
    }

    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<CoinDataSource, NetworkState>(
        sourceFactory.liveData) {
        it.networkState
    }

    fun refresh() {
        sourceFactory.liveData.value?.invalidate()
    }

    fun retry() {
    }
}