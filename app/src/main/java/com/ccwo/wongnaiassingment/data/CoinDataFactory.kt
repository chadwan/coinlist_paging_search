package com.ccwo.wongnaiassingment.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ccwo.wongnaiassingment.model.CoinModel

class CoinDataFactory : DataSource.Factory<Int, CoinModel> {
    var liveData = MutableLiveData<CoinDataSource>()
    var searchKey: String = ""

    constructor(searchKey: String){
        this.searchKey = searchKey
    }

    override fun create(): DataSource<Int, CoinModel> {
        val dataSource = CoinDataSource(searchKey)
        liveData.postValue(dataSource)
        return dataSource
    }
}