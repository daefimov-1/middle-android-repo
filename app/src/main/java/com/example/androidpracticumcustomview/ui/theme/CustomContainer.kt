package com.example.androidpracticumcustomview.ui.theme

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout

/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */

class CustomContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private companion object {
        const val ALPHA = "alpha"
        const val VERTICAL_TRANSLATION = "translationY"

        const val ALPHA_ANIMATION_DURATION = 2000L
        const val VERTICAL_TRANSLATION_ANIMATION_DURATION = 5000L
        const val START_DELAY_FOR_SECOND_VIEW = 2000L
    }

    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val width = right - left
        val height = bottom - top

        if (childCount > 0) {
            val firstChild = getChildAt(0)
            firstChild.layout(0, 0, width, height / 2)
            if (childCount > 1) {
                val secondChild = getChildAt(1)
                secondChild.layout(0, height / 2, width, height)
            }
        }
    }

    fun addViewWithAnimation(child: View, screenHeight: Int) {
        if (childCount < 2) {
            child.alpha = 0f
            super.addView(child)
            animateView(child, childCount - 1, screenHeight)
        } else {
            throw IllegalStateException("You can't add more than two views")
        }
    }

    private fun animateView(view: View, viewIndex: Int, screenHeight: Int) {
        val myheight = screenHeight
        val startHeight = when (viewIndex) {
            0 -> myheight / 4.toFloat()
            1 -> -1 * myheight / 4.toFloat()
            else -> throw IllegalStateException("Wrong index of view")
        }
        view.translationX = 0F
        view.translationY = startHeight

        ObjectAnimator.ofFloat(view, ALPHA, 0f, 1f).apply {
            interpolator = AccelerateInterpolator()
            duration = ALPHA_ANIMATION_DURATION
            if (viewIndex == 1) {
                startDelay = START_DELAY_FOR_SECOND_VIEW
            }
            start()
        }

        ObjectAnimator.ofFloat(view, VERTICAL_TRANSLATION, startHeight, 0f).apply {
            interpolator = DecelerateInterpolator()
            duration = VERTICAL_TRANSLATION_ANIMATION_DURATION
            if (viewIndex == 1) {
                startDelay = START_DELAY_FOR_SECOND_VIEW
            }
            start()
        }
    }
}