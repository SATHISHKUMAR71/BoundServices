package com.example.services

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var service:Intent
    private var isServiceBound = false
    private lateinit var appService:AppService
    private var serviceConnection = object :ServiceConnection{
        override fun onServiceConnected(componentName: ComponentName?, binder: IBinder?) {
            val serviceBinder = binder as AppService.ServiceBinder
            appService = serviceBinder.getService()
            isServiceBound = true
        }
        override fun onServiceDisconnected(p0: ComponentName?) {
            isServiceBound = false
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        service = Intent(this,AppService::class.java)

//        Listener Functions
        findViewById<Button>(R.id.button).setOnClickListener {
            startService(service)
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            stopService(service)
        }
        findViewById<Button>(R.id.bindService).setOnClickListener {
            bindService(service,serviceConnection,Context.BIND_AUTO_CREATE)
            isServiceBound = true
        }
        findViewById<Button>(R.id.unbindService).setOnClickListener{
            if(isServiceBound){
                unbindService(serviceConnection)
                isServiceBound = false
            }
        }
        findViewById<Button>(R.id.generateRandomNumber).setOnClickListener{
            if(isServiceBound){
                findViewById<TextView>(R.id.textView).text = appService.getRandomNumber().toString()
            }
            else{
                findViewById<TextView>(R.id.textView).text = "No Bound Services Found"
            }
        }
    }

    override fun onStop() {
        super.onStop()
//        stopService(service)
    }
}