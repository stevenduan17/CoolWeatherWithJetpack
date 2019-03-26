package com.steven.coolweather.ui.area

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.steven.coolweather.App
import com.steven.coolweather.data.PlaceRepository
import com.steven.coolweather.data.db.bean.place.City
import com.steven.coolweather.data.db.bean.place.Country
import com.steven.coolweather.data.db.bean.place.Province
import com.steven.coolweather.util.LEVEL_CITY
import com.steven.coolweather.util.LEVEL_COUNTY
import com.steven.coolweather.util.LEVEL_PROVINCE
import com.steven.coolweather.util.toast
import kotlinx.coroutines.launch

/**
 * @author Steven Duan
 * @version 1.0
 * @since 2019/3/26
 */
class ChooseAreaViewModel(private val repository: PlaceRepository) : ViewModel() {

    val currentLevel = MutableLiveData<Int>()

    val dataChanged = MutableLiveData<Int>()

    val isLoading = MutableLiveData<Boolean>()

    val areaSelected = MutableLiveData<Boolean>()

    var selectedProvince: Province? = null

    var selectedCity: City? = null

    var selectedCountry: Country? = null

    lateinit var provinces: MutableList<Province>

    lateinit var cities: MutableList<City>

    lateinit var countries: MutableList<Country>

    val data = arrayListOf<String>()

    fun getProvinces() {
        currentLevel.value = LEVEL_PROVINCE
        launch {
            provinces = repository.getProvinces()
            data.addAll(provinces.map { it.name })
        }
    }

    private fun getCities() = selectedProvince?.let {
        currentLevel.value = LEVEL_CITY
        launch {
            cities = repository.getCities(it.id)
            data.addAll(cities.map { it.name })
        }
    }

    private fun getCountries() = selectedCity?.let {
        currentLevel.value = LEVEL_COUNTY
        launch {
            countries = repository.getCountries(it.provinceId, it.id)
            data.addAll(countries.map { it.name })
        }

    }

    fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        when (currentLevel.value) {
            LEVEL_PROVINCE -> {
                selectedProvince = provinces[position]
                getCities()
            }
            LEVEL_CITY -> {
                selectedCity = cities[position]
                getCountries()
            }
            LEVEL_COUNTY -> {
                selectedCountry = countries[position]
                areaSelected.value = true
            }
        }
    }

    fun onBack() {
        if (currentLevel.value == LEVEL_COUNTY) {
            getCities()
        } else if (currentLevel.value == LEVEL_CITY) {
            getProvinces()
        }
    }

    private fun launch(f: suspend () -> Unit) = viewModelScope.launch {
        try {
            isLoading.value = true
            data.clear()
            f()
            dataChanged.value = dataChanged.value?.plus(1)
            isLoading.value = false
        } catch (t: Throwable) {
            t.printStackTrace()
            App.getInstance().toast(t.message)
            dataChanged.value = dataChanged.value?.plus(1)
            isLoading.value = false
        }
    }
}