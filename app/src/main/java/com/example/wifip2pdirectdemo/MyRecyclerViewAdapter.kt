package com.example.wifip2pdirectdemo

import android.net.wifi.p2p.WifiP2pDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyRecyclerViewAdapter(private val dataSet: MutableList<WifiP2pDevice>) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>() {

    private fun getDeviceStatus(deviceStatus: Int): String {
        when (deviceStatus) {
            WifiP2pDevice.AVAILABLE -> {
                return "Available"
            }

            WifiP2pDevice.INVITED -> {
                return "Invited"
            }

            WifiP2pDevice.CONNECTED -> {
                return "Connected"
            }

            WifiP2pDevice.FAILED -> {
                return "Failed"
            }

            WifiP2pDevice.UNAVAILABLE -> {
                return "Unavailable"
            }

            else -> {
                return "Unknown"
            }
        }
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDeviceName: TextView
        val tvDeviceDetails: TextView

        init {
            // Define click listener for the ViewHolder's View.
            tvDeviceName = view.findViewById(R.id.device_name)
            tvDeviceDetails = view.findViewById(R.id.device_details)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_devices, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvDeviceName.text = dataSet[position].deviceName
        holder.tvDeviceDetails.text = getDeviceStatus(dataSet[position].status)
    }

}