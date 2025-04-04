package com.example.androidpracticumcustomview

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import com.example.androidpracticumcustomview.ui.theme.CustomContainer
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator

class XmlActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xml_layout)
        startXmlPracticum()
    }

    private fun startXmlPracticum() {
        val customContainer = findViewById<CustomContainer>(R.id.customContainer)
        setContentView(customContainer)
        customContainer.setOnClickListener {
            finish()
        }

        val firstView = View(this)
        firstView.setBackgroundColor(getColor(R.color.teal_200))
        customContainer.addView(
            firstView,
            FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )
        animateView(firstView, AnimatedView.FIRST)

        val secondView = View(this)
        secondView.setBackgroundColor(getColor(R.color.purple_500))
        // Добавление второго элемента через некоторое время (например, по задержке)
        Handler(Looper.getMainLooper()).postDelayed({
            customContainer.addView(secondView)
        }, 2000)
        animateView(secondView, AnimatedView.SECOND)
    }

    private fun animateView(view: View, animatedView: AnimatedView) {
        val height = getScreenHeight(this)
        val startHeight = when (animatedView) {
            AnimatedView.FIRST -> height / 4.toFloat()
            AnimatedView.SECOND -> -1 * height / 4.toFloat()
        }
        view.translationX = 0F
        view.translationY = startHeight
        view.visibility = View.VISIBLE

        ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).apply {
            interpolator = AccelerateInterpolator()
            duration = 2000
            if (animatedView == AnimatedView.SECOND) {
                startDelay = 2000
            }
            start()
        }

        ObjectAnimator.ofFloat(view, "translationY", startHeight, 0f).apply {
            interpolator = DecelerateInterpolator()
            duration = 5000
            if (animatedView == AnimatedView.SECOND) {
                startDelay = 2000
            }
            start()
        }
    }

    private fun getScreenHeight(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}

private enum class AnimatedView {
    FIRST,
    SECOND
}