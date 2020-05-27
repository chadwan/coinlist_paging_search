package com.ccwo.wongnaiassingment.adapter

import androidx.appcompat.app.AppCompatActivity
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ccwo.wongnaiassingment.R
import com.ccwo.wongnaiassingment.model.CoinModel
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.adapter_coin.view.*

class CoinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup): CoinViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.adapter_coin, parent, false)
            return CoinViewHolder(view)
        }
    }

    fun bindTo(item : CoinModel?, pos : Int) {
        // calculate each 5 row display name and icon at right
        if((pos+1)%5 == 0){
            itemView.contentLeft.visibility = View.GONE;
            itemView.contentRight.visibility = View.VISIBLE;
            itemView.txvCoinNameRight.text = item?.name
            GlideToVectorYou.justLoadImage(itemView.context as AppCompatActivity, Uri.parse(item?.iconUrl), itemView.imvCoinIconRight)
        }else{
            itemView.contentLeft.visibility = View.VISIBLE;
            itemView.contentRight.visibility = View.GONE;
            itemView.txvCoinName.text = item?.name
            itemView.txvCoinDesc.text = item?.desc
            GlideToVectorYou.justLoadImage(itemView.context as AppCompatActivity, Uri.parse(item?.iconUrl), itemView.imvCoinIcon)
        }
    }
}