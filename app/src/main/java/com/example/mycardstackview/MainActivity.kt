package com.example.mycardstackview

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mycardstackview.cardStackView.CardStackView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TestStackAdapter.OnAnimationEnd {

    private var colors = arrayOf(
        R.color.color_1,
        R.color.color_2,
        R.color.color_3,
        R.color.color_4,
        R.color.color_5
//        R.color.color_6,
//        R.color.color_7,
//        R.color.color_8,
//        R.color.color_9,
//        R.color.color_10,
//        R.color.color_11,
//        R.color.color_12,
//        R.color.color_13,
//        R.color.color_14,
//        R.color.color_15,
//        R.color.color_16,
//        R.color.color_17,
//        R.color.color_18,
//        R.color.color_19,
//        R.color.color_20,
//        R.color.color_21,
//        R.color.color_22,
//        R.color.color_23,
//        R.color.color_24,
//        R.color.color_25,
//        R.color.color_26
    ).toMutableList()

    private var selectedPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val testStackAdapter = TestStackAdapter(this, this)
        stackview_main.setAdapter(testStackAdapter)
//        stackview_main.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))

        Handler().postDelayed(
            {
                // gambis to fix first position bug when list have only one child
                if (colors.size == 1)
                    colors.add(0, android.R.color.transparent)

                testStackAdapter.updateData(colors)
                configure()
            }, 200
        )
    }

    private fun configure() {
        val cardHeight = resources.getDimension(R.dimen.card_height)
        val showHeight = stackview_main.showHeight
        val paddingTop = (showHeight - (cardHeight * colors.size)) + (dp2px(40) * (colors.size.minus(1)))
        val scrollY = -paddingTop.toInt()

        Log.i("Main", "ShowHeight: ${stackview_main.showHeight}")
        Log.i("Main", "PaddingTop: $paddingTop")
        Log.i("Main", "ScrollY: $scrollY")

        stackview_main.setPadding(
            dp2px(16),
            if (paddingTop < 0) 0 else paddingTop.toInt(),
            dp2px(16),
            0)

        if (paddingTop < 0)
            stackview_main.scrollDelegate.scrollViewTo(0, scrollY)
    }

    private fun dp2px(value: Int): Int {
        val density: Float = resources.displayMetrics.density
        return (value * density + 0.5f).toInt()
    }

    override fun endAnimation(view: View, position: Int) {
        this.selectedPosition = position

        Intent(this, SecondActivity::class.java).apply {
            val transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(
                this@MainActivity, view, "cardTransition")
            this.putExtra("bgColor", colors[position])
            startActivityForResult(this, 123, transitionActivityOptions.toBundle())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123) {
            stackview_main.performItemClick(stackview_main.viewHolders[selectedPosition])
        }
    }
}
