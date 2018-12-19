package com.example.rxpermissiontest

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.tbruyelle.rxpermissions2.RxPermissions

class MainActivity : AppCompatActivity() {

    private val viewModel: BlankViewModel by lazy {
        ViewModelProviders.of(this).get(BlankViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("+++", "onCreate MainActivity")

        lifecycle.addObserver(viewModel)
        viewModel.init(RxPermissions(this))

        viewModel.errorEvent.observeForever {
            Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.permissionEvent.observeForever {
            if (!it)
                finish()
        }

    }
}
