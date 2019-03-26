package com.steven.coolweather.ui.area

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.steven.coolweather.data.PlaceRepository

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/26
 */
class ChooseAreaModelFactory(private val repository:PlaceRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChooseAreaViewModel(repository) as T
    }

}