package com.ccwo.wongnaiassingment.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.ccwo.wongnaiassingment.api.CoinApi
import com.ccwo.wongnaiassingment.api.CoinApiResponse
import com.ccwo.wongnaiassingment.model.CoinModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class CoinDataSource : PageKeyedDataSource<Int, CoinModel> {
    private var searchKey: String = ""
    var startPage = 0
    var pageSize = 10
    val networkState = MutableLiveData<NetworkState>()
    private var apiService = CoinApi.create()

    constructor(searchKey: String){
        this.searchKey = searchKey
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, CoinModel>) {
        var call : Call<CoinApiResponse> = apiService.getCoins(startPage, pageSize)
        if(searchKey.isNotEmpty()){
            call = apiService.getCoinsSearch(searchKey)
        }

        call.enqueue(object : Callback<CoinApiResponse>{
            override fun onResponse(call: Call<CoinApiResponse>, response: Response<CoinApiResponse>) {
                if (response.isSuccessful) {
                    var res = response.body()
                    if(res?.status?.toLowerCase() == "success"){
                        val coinList: MutableList<CoinModel> = ArrayList()
                        coinList.addAll(res.data.coinList)

                        callback.onResult(coinList, null, startPage+1)
                    }else{
                        //handle error
                    }
                } else {
                    networkState.postValue(NetworkState.error(response.message()))
                }
            }
            override fun onFailure(call: Call<CoinApiResponse>, t: Throwable) {
                networkState.postValue(NetworkState.error(t.message))
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, CoinModel>) {
        if(searchKey.isEmpty()){
            var call : Call<CoinApiResponse> = apiService.getCoins(params.key*pageSize, pageSize)
            call.enqueue(object : Callback<CoinApiResponse>{
                override fun onResponse(call: Call<CoinApiResponse>, response: Response<CoinApiResponse>) {
                    if (response.isSuccessful) {
                        var res = response.body()
                        if(res?.status?.toLowerCase() == "success"){
                            val coinList: MutableList<CoinModel> = ArrayList()
                            coinList.addAll(res.data.coinList)

                            callback.onResult(coinList, params.key+1)
                        }else{
                            //handle error
                        }
                    } else {
                        networkState.postValue(NetworkState.error(response.message()))
                    }
                }
                override fun onFailure(call: Call<CoinApiResponse>, t: Throwable) {
                    networkState.postValue(NetworkState.error(t.message))
                }
            })
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, CoinModel>) {
    }
}