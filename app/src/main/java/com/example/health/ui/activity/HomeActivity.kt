package com.example.health.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.health.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initListener()
    }

    private fun initListener() {
        ivMenu.setOnClickListener {

        }
        ivContact.setOnClickListener {

        }
        ivShopping.setOnClickListener {

        }

    }
}
