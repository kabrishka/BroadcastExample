package com.kabrishka.broadcastexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar

    private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(this)
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                ACTION_LOADED -> {
                    val percent = intent.getIntExtra(PERCENT_LOADED, 0)
                    progressBar.progress = percent
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)

        registerLoadingReceiver()
        startLoadingService()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterLoadingReceiver()
    }

    private fun registerLoadingReceiver() {
        val intentFilter = IntentFilter().apply {
            addAction(ACTION_LOADED)
        }
        localBroadcastManager.registerReceiver(receiver, intentFilter)
    }

    private fun unregisterLoadingReceiver() {
        localBroadcastManager.unregisterReceiver(receiver)
    }

    private fun startLoadingService() {
        Intent(this, LoadingService::class.java).apply {
            startService(this)
        }
    }

    companion object {
        const val ACTION_LOADED = "ACTION_LOADED"
        const val PERCENT_LOADED = "PERCENT_LOADED"
    }
}