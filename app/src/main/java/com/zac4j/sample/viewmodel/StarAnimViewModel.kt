package com.zac4j.sample.viewmodel

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModel
import com.zac4j.sample.R

/**
 * ViewModel for star animation ui.
 *
 * @author: zac
 * @date: 2020/9/20
 */
class StarAnimViewModel : ViewModel() {

  fun rotate(
    button: Button,
    imageView: ImageView
  ) {
    ObjectAnimator.ofFloat(imageView, View.ROTATION, -360F, 0F)
        .apply {
          duration = 1000L
          disableViewDuringAnimation(button)
        }
        .start()
  }

  fun translate(
    button: Button,
    imageView: ImageView
  ) {
    ObjectAnimator.ofFloat(imageView, View.TRANSLATION_X, 200F)
        .apply {
          repeatCount = 1
          repeatMode = ObjectAnimator.REVERSE
          disableViewDuringAnimation(button)
        }
        .start()
  }

  fun scale(
    button: Button,
    imageView: ImageView
  ) {
    val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4F)
    val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4F)

    ObjectAnimator.ofPropertyValuesHolder(imageView, scaleX, scaleY)
        .apply {
          repeatCount = 1
          repeatMode = ObjectAnimator.REVERSE
          disableViewDuringAnimation(button)
        }
        .start()
  }

  fun fade(
    button: Button,
    imageView: ImageView
  ) {
    ObjectAnimator.ofFloat(imageView, View.ALPHA, 0F)
        .apply {
          repeatCount = 1
          repeatMode = ObjectAnimator.REVERSE
          disableViewDuringAnimation(button)
        }
        .start()
  }

  fun colorize(
    button: Button,
    imageView: ImageView
  ) {
    ObjectAnimator.ofArgb(imageView.parent, "backgroundColor", Color.BLACK, Color.RED)
        .apply {
          duration = 500
          repeatCount = 1
          repeatMode = ObjectAnimator.REVERSE
          disableViewDuringAnimation(button)
        }
        .start()
  }

  fun show(imageView: ImageView) {
    // get star holder dimens
    val container = imageView.parent as ViewGroup
    val containerW = container.width
    val containerH = container.height
    var startW = imageView.width.toFloat()
    var startH = imageView.height.toFloat()

    // create new star
    val newStar = AppCompatImageView(imageView.context)
    newStar.setImageResource(R.drawable.ic_star)
    newStar.layoutParams = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT
    )
    container.addView(newStar)

    newStar.scaleX = Math.random()
        .toFloat() * 1.5F + 0.1F
    newStar.scaleY = newStar.scaleX
    startW *= newStar.scaleX
    startH *= newStar.scaleY

    newStar.translationX = Math.random()
        .toFloat() * containerW - startW / 2

    val mover = ObjectAnimator.ofFloat(newStar, View.TRANSLATION_Y, -startH, containerH + startH)
    mover.interpolator = AccelerateInterpolator(1F)
    val rotator = ObjectAnimator.ofFloat(newStar, View.ROTATION, (Math.random() * 1080).toFloat())
    rotator.interpolator = LinearInterpolator()

    AnimatorSet().apply {
      playTogether(mover, rotator)
      duration = (Math.random() * 1500 + 500).toLong()
      addListener(object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator?) {
          container.removeView(newStar)
        }
      })
    }
        .start()
  }

  private fun ObjectAnimator.disableViewDuringAnimation(
    view: View
  ) {
    addListener(object : AnimatorListenerAdapter() {
      override fun onAnimationStart(animation: Animator?) {
        view.isEnabled = false
      }

      override fun onAnimationEnd(animation: Animator?) {
        view.isEnabled = true
      }
    })
  }

}