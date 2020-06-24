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
//        R.color.color_1,
//        R.color.color_2,
//        R.color.color_3,
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
                testStackAdapter.updateData(colors)
                configure()
            }, 200
        )
    }

    private fun configure() {
        val overlapGap = stackview_main.overlapGaps
        val listSize = colors.size
        val cardHeight = resources.getDimension(R.dimen.card_height)
        val measureHeight = stackview_main.measuredHeight
        val paddingTop = ((measureHeight - (cardHeight * listSize)) + ((overlapGap * listSize.minus(1)) * 2)).toInt()
        val paddingStartEnd = resources.getDimension(R.dimen.default_margin).toInt()
        val paddingBottom = (resources.getDimension(R.dimen.big_margin)).toInt()

        // measureHeight + paddingBottom = showHeight

        stackview_main.setPadding(
            paddingStartEnd,
            if (paddingTop < 0) 0 else paddingTop + overlapGap,
            paddingStartEnd,
            paddingBottom
        )
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
