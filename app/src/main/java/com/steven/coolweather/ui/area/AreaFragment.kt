package com.steven.coolweather.ui.area


import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.steven.coolweather.R
import com.steven.coolweather.databinding.AreaItemBinding
import com.steven.coolweather.databinding.FragmentAreaBinding
import com.steven.coolweather.ui.MainActivity
import com.steven.coolweather.ui.weather.WeatherActivity
import com.steven.coolweather.util.InjectorUtil
import com.steven.coolweather.util.LEVEL_CITY
import com.steven.coolweather.util.LEVEL_COUNTY
import com.steven.coolweather.util.LEVEL_PROVINCE
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.fragment_area.*

@Suppress("DEPRECATION")
class AreaFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = AreaFragment()
    }

    private lateinit var mViewModel: ChooseAreaViewModel
    private lateinit var mAdapter: ArrayAdapter<String>
    private var progressDialog: ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_area, container, false)
        mViewModel = ViewModelProviders.of(this, InjectorUtil.getChooseAreaModelFactory(context!!))
            .get(ChooseAreaViewModel::class.java)
        DataBindingUtil.bind<FragmentAreaBinding>(view).apply {
            this!!.viewModel = mViewModel
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = AreaAdapter(context!!, R.layout.area_item, mViewModel.data)
        listView.adapter = mAdapter
        setup()
    }

    private fun setup() {
        mViewModel.currentLevel.observe(this, Observer {
            when (it) {
                LEVEL_PROVINCE -> {
                    titleText.text = "中国"
                    backButton.visibility = View.GONE
                }
                LEVEL_CITY -> {
                    titleText.text = mViewModel.selectedProvince?.name
                    backButton.visibility = View.VISIBLE
                }
                LEVEL_COUNTY -> {
                    titleText.text = mViewModel.selectedCity?.name
                    backButton.visibility = View.VISIBLE
                }
            }
        })

        mViewModel.dataChanged.observe(this, Observer {
            mAdapter.notifyDataSetChanged()
            listView.setSelection(0)
            closeProgressDialog()
        })

        mViewModel.isLoading.observe(this, Observer {
            if (it) showProgressDialog() else closeProgressDialog()
        })

        mViewModel.areaSelected.observe(this, Observer {
            if (it && mViewModel.selectedCountry != null) {
                if (activity is MainActivity) {
                    startActivity(Intent(activity, WeatherActivity::class.java).apply {
                        putExtra("weather_id", mViewModel.selectedCountry!!.weatherId)
                    })
                    activity?.finish()
                } else if (activity is WeatherActivity) {
                    val weatherActivity = activity as WeatherActivity
                    weatherActivity.drawerLayout.closeDrawers()
                    weatherActivity.mViewModel.weatherId = mViewModel.selectedCountry!!.weatherId
                    weatherActivity.mViewModel.refreshWeather()

                }
                mViewModel.areaSelected.value = false
            }
        })

        if (mViewModel.data.isEmpty()) {
            mViewModel.getProvinces()
        }
    }

    private fun showProgressDialog() {
        progressDialog ?: ProgressDialog(activity).apply {
            setMessage("正在加载...")
            setCanceledOnTouchOutside(false)
        }.also { progressDialog = it }
        progressDialog?.show()
    }

    private fun closeProgressDialog() {
        progressDialog?.dismiss()
    }


    inner class AreaAdapter(context: Context, private val resId: Int, private val data: List<String>) :
        ArrayAdapter<String>(context, resId, data) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val binding: AreaItemBinding?
            val view = if (convertView == null) {
                val v = LayoutInflater.from(context).inflate(resId, parent, false)
                binding = DataBindingUtil.bind(v)
                v.tag = binding
                v
            } else {
                binding = convertView.tag as AreaItemBinding
                convertView
            }
            binding?.data = data[position]
            return view
        }
    }
}
