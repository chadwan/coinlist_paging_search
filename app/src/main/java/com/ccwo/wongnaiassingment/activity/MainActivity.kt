package com.ccwo.wongnaiassingment.activity

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.ccwo.wongnaiassingment.R
import com.ccwo.wongnaiassingment.adapter.CoinAdapter
import com.ccwo.wongnaiassingment.data.NetworkState
import com.ccwo.wongnaiassingment.model.CoinModel
import com.ccwo.wongnaiassingment.viewmodel.CoinViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: CoinAdapter
    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefresh.setOnRefreshListener {
            Handler().postDelayed({
                searchView.setQuery("",false)
                viewModel.refresh()
                swipeRefresh.isRefreshing = false;
            }, 500)
        }

        initRecycleView()
        setSearchView()
        initAdapter()
    }

    private fun initRecycleView() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcvCoinList.layoutManager = linearLayoutManager
    }

    private fun setSearchView() {
        searchView.setOnClickListener { searchView.isIconified = false }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                performSearch(newText.toString())
                return false
            }
        })
    }

    private fun initAdapter() {
        viewModel = ViewModelProviders.of(this).get(CoinViewModel::class.java)

        adapter = CoinAdapter {
            viewModel.retry()
        }

        rcvCoinList.adapter = adapter

        viewModel.coinList.observe(this, Observer<PagedList<CoinModel>> {
            adapter.submitList(it)
        })

        viewModel.getNetworkState().observe(this, Observer<NetworkState> {
            adapter.setNetworkState(it)
        })

        performSearch("")
    }

    private fun performSearch(searchKey: String) {
        viewModel.filterTextAll.value = searchKey
        viewModel.coinList.observe(this, Observer<PagedList<CoinModel>> {
                fun onChanged(myItems: PagedList<CoinModel>) {
                    adapter.submitList(it)
                }
            })
    }
}
