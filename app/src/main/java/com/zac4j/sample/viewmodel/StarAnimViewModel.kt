package com.zac4j.sample.viewmodel

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModel

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