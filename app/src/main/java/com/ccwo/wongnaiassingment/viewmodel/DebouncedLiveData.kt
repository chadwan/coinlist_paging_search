package com.ccwo.wongnaiassingment.viewmodel

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData


class DebouncedLiveData<T>(private val mSource: LiveData<T>, private val mDuration: Int) :
    MediatorLiveData<T>() {
    private val debounceRunnable = Runnable { postValue(mSource.value) }
    private val handler = Handler()

    init {
        addSource(mSource) {
            handler.removeCallbacks(debounceRunnable)
            handler.postDelayed(debounceRunnable, mDuration.toLong())
        }
    }
}