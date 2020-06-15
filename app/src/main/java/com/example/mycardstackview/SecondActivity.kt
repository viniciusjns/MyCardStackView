package com.example.mycardstackview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val bgColor = intent.getIntExtra("bgColor", android.R.color.black)
        frameCard.setBackgroundColor(ContextCompat.getColor(this, bgColor))
    }
}
