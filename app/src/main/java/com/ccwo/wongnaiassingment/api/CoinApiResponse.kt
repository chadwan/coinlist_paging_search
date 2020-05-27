package com.ccwo.wongnaiassingment.api

import com.ccwo.wongnaiassingment.model.CoinModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CoinApiResponse {
    @SerializedName("status")
    var status: String = ""
    @SerializedName("data")
    var data: Data = Data()

    class Data{
        @SerializedName("coins")
        var coinList : List<CoinModel> = ArrayList()
    }
}