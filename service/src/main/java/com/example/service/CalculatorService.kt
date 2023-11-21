package com.example.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import com.example.sdk.ICalculator
import com.example.sdk.bean.Sample

class CalculatorService : Service() {

    private val iCalculatorStub: ICalculator.Stub = object : ICalculator.Stub() {

        override fun add(a: Int, b: Int): Int = a + b

        override fun subtract(a: Int, b: Int): Int {
            return a - b
        }

        override fun multiply(a: Int, b: Int): Int {
            return a * b
        }

        override fun divide(a: Int, b: Int): Int {
            return a / b
        }

        override fun optionParcel(sample: Sample): Sample {
            return Sample(sample.num + 1)
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return iCalculatorStub
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
//        startServiceForeground()
    }

    private val channelIdString = "134"
    private val channelId = 123

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startServiceForeground() {
        val notificationManager: NotificationManager? = getSystemService()
        val channel = NotificationChannel(
            channelIdString, "Service", NotificationManager.IMPORTANCE_LOW
        )
        notificationManager?.createNotificationChannel(channel)
        val notification = Notification.Builder(this, channelIdString)
            .build()
        startForeground(channelId, notification)
    }
}