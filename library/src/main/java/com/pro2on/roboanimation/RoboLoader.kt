package com.pro2on.roboanimation

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

/**
 * @author Konstantin Chupryna on 4/5/19.
 */

class RoboLoader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {



    //rec
    private val mainColor: Int = Color.GRAY
    private val recOne = RectF(0f, 0f, 0f, 0f)
    private val recTwo = RectF(0f, 0f, 0f, 0f)
    private val recThree = RectF(0f, 0f, 0f, 0f)


    private var progress = 0F



    private val mainPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = mainColor
        style = Paint.Style.STROKE
    }


    private var fraction = 0F
    private var rectangleSize = 0F
    private var halfFraction = 0F




    private var isAnimating = false
    private val animatorOne = ValueAnimator.ofFloat(0.0f, 1.0f).apply {
        addUpdateListener {
            progress = it.animatedValue as Float
            postInvalidateOnAnimation()
            interpolator = LinearInterpolator()
        }
        duration = 4000L

        repeatCount = ValueAnimator.INFINITE
    }



    init {
        setOnClickListener { toggleAnimation() }
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)


        fraction = (width / 7).toFloat()
        rectangleSize = fraction * 3
        halfFraction = fraction / 2

        // without changing
        recOne.bottom = rectangleSize - halfFraction
        recOne.top = halfFraction
        recOne.right = 5 * halfFraction
        recOne.left = halfFraction

        recTwo.top = halfFraction
        recTwo.right = 13 * halfFraction
        recTwo.left = 9 * halfFraction
        recTwo.bottom = 5 * halfFraction


        recThree.bottom = 13 * halfFraction
        recThree.right = 13 * halfFraction
        recThree.top = 9 * halfFraction
        recThree.left = halfFraction


        mainPaint.strokeWidth = fraction
    }




    override fun onDraw(canvas: Canvas?) {


        if (progress > 0.0f && progress <= 0.125) {

            val localProgress = 8 * progress

            recOne.right = 5 * halfFraction
            recOne.left = halfFraction

            recTwo.right = 13 * halfFraction
            recTwo.left = 9 * halfFraction
            recTwo.top = halfFraction
            recTwo.bottom = 5 * halfFraction


            recThree.bottom = 13F * halfFraction
            recThree.top = 9 * halfFraction

            recThree.right = 13 * halfFraction - localProgress * 8 * halfFraction

        }

        else if (progress > 0.125 && progress <= 0.25) {

            // save previous animation result
            recThree.right = 5 * halfFraction

            val localProgress = 8 * progress - 1
            recTwo.bottom = 5 * halfFraction + localProgress * 8 * halfFraction

        }

        else if (progress > 0.25 && progress <= 0.375f) {

            // save previous animation result
            recTwo.bottom = 13 * halfFraction

            val localProgress = 8 * progress - 2
            recTwo.top = halfFraction + localProgress * 8 * halfFraction

        }

        else if (progress > 0.375f && progress <= 0.50f) {

            // save previous animation result
            recTwo.top = 9 * halfFraction

            val localProgress = 8 * progress - 3
            recOne.right = 5 * halfFraction + localProgress * 8 * halfFraction

        } else if (progress > 0.50 && progress <= 0.625) {

            // save
            recOne.right = 13 * halfFraction

            val localProgress =  8 * progress - 4
            recOne.left = halfFraction  + localProgress * 8 * halfFraction


        } else if (progress > 0.625 && progress <= 0.75) {

            // save previous animation result
            recOne.left = 9 * halfFraction

            val localProgress =  8 * progress - 5
            recThree.top = 9 * halfFraction - localProgress * 8 * halfFraction

        } else if (progress > 0.75 && progress <= 0.875) {

            // save previous animation result
            recThree.top = halfFraction

            val localProgress =  8 * progress - 6
            recThree.bottom = 13 * halfFraction - localProgress * 8 * halfFraction

        } else if (progress > 0.875 && progress <= 1) {

            // save previous animation result
            recThree.bottom = 5 * halfFraction

            val localProgress =  8 * progress - 7
            recTwo.left = 8 * halfFraction  - localProgress * 7 *  halfFraction

        }


        canvas?.drawRect(recOne, mainPaint) // box 2
        canvas?.drawRect(recTwo, mainPaint) // box 3
        canvas?.drawRect(recThree, mainPaint) // box 1

    }


    private fun toggleAnimation() {


        if (isAnimating) {
            animatorOne.end()

            progress = 0.0f
            invalidate()
            isAnimating = false

        } else {

            progress = 0.0f
            animatorOne.start()
            isAnimating = true
        }


    }




}