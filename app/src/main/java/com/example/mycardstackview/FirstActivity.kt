package com.example.mycardstackview

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import kotlinx.android.synthetic.main.activity_first.*
import kotlinx.android.synthetic.main.activity_first.frameCard
import kotlinx.android.synthetic.main.activity_second.*

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        frameCard.setOnClickListener {
            val parentHeight = parentContainer.measuredHeight
            val cardHeight = dpToPx(160)
            val centerParent = (parentHeight / 2) - (cardHeight / 2)

            frameCard.animate().translationY(-centerParent).setDuration(500).setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    openSecondActivity()
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationStart(animation: Animator?) {
                }
            })
        }
    }

    fun openSecondActivity() {
        Intent(this, SecondActivity::class.java).apply {
            val transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
                this@FirstActivity, frameCard, "cardAnim")
            startActivityForResult(this, 123, transitionActivityOptions.toBundle())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123) {
            frameCard.animate().translationY(0f).setDuration(500).setListener(null).start()
        }
    }

    fun dpToPx(dp: Int): Float {
        val density = resources.displayMetrics.density
        return (dp * density).toFloat()
    }
}
