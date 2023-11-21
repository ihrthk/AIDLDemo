package com.example.aidldemo

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aidldemo.databinding.ActivityMainBinding
import com.example.sdk.ICalculator
import com.example.sdk.bean.Sample

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    lateinit var calculator: ICalculator

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            calculator = ICalculator.Stub.asInterface(service)
            Toast.makeText(this@MainActivity, "onServiceConnected", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }
    }

    private val mainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindService()
        setContentView(mainBinding.root)
        mainBinding.tv1.setOnClickListener {
            val ret = calculator.add(1, 2)
            Toast.makeText(this, ret.toString(), Toast.LENGTH_SHORT).show()
        }
        mainBinding.tv2.setOnClickListener {
            val ret = calculator.subtract(2, 1)
            Toast.makeText(this, ret.toString(), Toast.LENGTH_SHORT).show()
        }
        mainBinding.tv3.setOnClickListener {
            val ret = calculator.multiply(3, 5)
            Toast.makeText(this, ret.toString(), Toast.LENGTH_SHORT).show()
        }
        mainBinding.tv4.setOnClickListener {
            val ret = calculator.divide(6, 2)
            Toast.makeText(this, ret.toString(), Toast.LENGTH_SHORT).show()
        }
        mainBinding.tv5.setOnClickListener {
            val ret = calculator.optionParcel(Sample(100))
            Toast.makeText(this, ret.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun bindService() {
        val intent = Intent()
        intent.action = "com.zls.aidl.Calculator"
        intent.component = ComponentName(
            "com.example.service",
            "com.example.service.CalculatorService"
        )
        val bindService = bindService(intent, serviceConnection, BIND_AUTO_CREATE)
//        Toast.makeText(this, "bindService", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }
}