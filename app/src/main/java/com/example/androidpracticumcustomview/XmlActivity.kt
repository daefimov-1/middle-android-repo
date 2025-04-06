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

        val screenHeight = getScreenHeight(this)
        val firstView = View(this)
        firstView.setBackgroundColor(getColor(R.color.teal_200))
        customContainer.addViewWithAnimation(
            firstView, screenHeight
        )

        val secondView = View(this)
        secondView.setBackgroundColor(getColor(R.color.purple_500))
        // Добавление второго элемента через некоторое время (например, по задержке)
        Handler(Looper.getMainLooper()).postDelayed({
            customContainer.addViewWithAnimation(secondView, screenHeight)

        }, 2000)
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