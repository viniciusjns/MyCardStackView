package com.example.mycardstackview

import android.animation.Animator
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

        frameCard.animate().alpha(0f).setDuration(200).setStartDelay(500).start()
    }

    override fun onBackPressed() {
        frameCard.animate().alpha(1f).setDuration(200).setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) { }

            override fun onAnimationEnd(animation: Animator?) {
                goBack()
            }

            override fun onAnimationCancel(animation: Animator?) { }

            override fun onAnimationStart(animation: Animator?) { }

        }).start()
    }

    private fun goBack() {
        super.onBackPressed()
    }
}
