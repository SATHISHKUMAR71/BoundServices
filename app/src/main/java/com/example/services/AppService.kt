package com.example.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class AppService:Service() {
    private var randomNumber=0
    private var isGenerate=true
    private val binder = ServiceBinder()
    inner class ServiceBinder : Binder(){
        fun getService(): AppService=this@AppService
    }
    override fun onBind(p0: Intent?): IBinder {
        println("On Bind")
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Thread{
            startRandomGenerator()
        }.start()
        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRandomGenerator()
        println("Service Stopped")
    }

    private fun startRandomGenerator(){
        while (isGenerate){
            Thread.sleep(1000)
            randomNumber = (Math.random()*100).toInt()
            println("Random Number Form Service: $randomNumber")
        }
    }
    fun stopRandomGenerator(){
        isGenerate = false
    }

    fun getRandomNumber():Int{
        return randomNumber
    }

    override fun onUnbind(intent: Intent?): Boolean {
        println("On Unbind")
        return super.onUnbind(intent)
    }
}