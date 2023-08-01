package com.example.wifip2pdirectdemo

import android.net.wifi.p2p.WifiP2pDevice
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.wifip2pdirectdemo.databinding.FragmentDeviceListBinding

class DeviceListFragment : Fragment() {
    private var peers = mutableListOf<WifiP2pDevice>()
    private var progressBar: ProgressBar? = null
    private var device: WifiP2pDevice? = null

    private var _binding: FragmentDeviceListBinding? = null
    private val binding get() = _binding!!

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeviceListBinding.inflate(inflater, container, false)
        return binding.root
    }

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

    private fun clearPeers() {
        peers.clear()
    }

    fun updateThisDevice(device: WifiP2pDevice?) {
        this.device = device!!
        binding.myName.text = device.deviceName
        binding.myStatus.text = getDeviceStatus(device.status)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}