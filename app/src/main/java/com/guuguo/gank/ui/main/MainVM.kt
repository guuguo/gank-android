package com.guuguo.gank.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainVM :ViewModel(){
    val selectedId = MutableLiveData<Int>()
}