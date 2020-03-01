package com.graytsar.sensorapp

import android.graphics.Color
import android.hardware.Sensor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.charts.LineChart
import com.graytsar.sensorapp.databinding.ItemDetailBinding
import kotlinx.android.synthetic.main.item_detail.view.*
import kotlinx.android.synthetic.main.toolbar.*

class AdapterDetail(private val activity:FragmentActivity):ListAdapter<ModelDetail, ViewHolderDetail>(DiffCallbackDetail()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDetail {
        val binding = DataBindingUtil.inflate<ItemDetailBinding>(LayoutInflater.from(activity), R.layout.item_detail, parent, false)
        return ViewHolderDetail(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ViewHolderDetail, position: Int) {
        holder.binding.lifecycleOwner = activity
        holder.binding.detailModel = getItem(position)

        if(getItem(position).sensorValuesCount == 1){
            holder.yValDetail.visibility = View.GONE
            holder.zValDetail.visibility = View.GONE
        }

        activity.toolbarBackdrop.setImageBitmap(getItem(position).icon)

        val toolbar =  activity.collapsingToolbarLayout
        toolbar.setExpandedTitleColor(Color.WHITE)
        toolbar.setCollapsedTitleTextColor(Color.WHITE)
        toolbar.title = getItem(position).title

        getItem(position).mChart = holder.chart

        when(getItem(position).sensor.type){
            Sensor.TYPE_ACCELEROMETER -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorRed))
                activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.colorRed)
            }
            Sensor.TYPE_MAGNETIC_FIELD -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPink))
                activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.colorPink)
            }
            Sensor.TYPE_GRAVITY -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPurple))
                activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.colorPurple)
            }
            Sensor.TYPE_GYROSCOPE -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorDeepPurple))
                activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.colorDeepPurple)
            }
            Sensor.TYPE_LINEAR_ACCELERATION -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorIndigo))
                activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.colorIndigo)
            }
            Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorBlue))
                activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.colorBlue)
            }
            Sensor.TYPE_LIGHT -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorLightBlue))
                activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.colorLightBlue)
            }
            Sensor.TYPE_PRESSURE -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorCyan))
                activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.colorCyan)
            }
            Sensor.TYPE_RELATIVE_HUMIDITY -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorTeal))
                activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.colorTeal)
            }
            Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorGreen))
                activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.colorGreen)
            }
            Sensor.TYPE_PROXIMITY -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorLightGreen))
                activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.colorLightGreen)
            }
            Sensor.TYPE_STEP_COUNTER -> {
                toolbar.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorLime))
                activity.window.statusBarColor = ContextCompat.getColor(activity, R.color.colorLime)
            }
        }
    }
}

class ViewHolderDetail(view:View, val binding: ItemDetailBinding): RecyclerView.ViewHolder(view){
    var yValDetail:View = view.yValDetail
    var zValDetail:View = view.zValDetail
    var chart:LineChart = view.chart
}

class DiffCallbackDetail: DiffUtil.ItemCallback<ModelDetail>(){
    override fun areItemsTheSame(oldItem: ModelDetail, newItem: ModelDetail): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: ModelDetail, newItem: ModelDetail): Boolean {
        return false
    }
}
