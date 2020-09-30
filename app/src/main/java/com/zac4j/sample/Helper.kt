package com.zac4j.sample

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Utilities
 *
 * @author: zac
 * @date: 2020/9/30
 */

/**
 * Custom view type
 */
const val ARGS_VIEW_TYPE = "view_type"
const val VIEW_TYPE_MINI_PAINT = 0x101
const val VIEW_TYPE_CLIP_RECT = 0x102
const val VIEW_TYPE_SPOTLIGHT = 0x103

/**
 * Observe once
 */
fun <T> LiveData<T>.observeOnce(
  lifecycleOwner: LifecycleOwner,
  observer: Observer<T>
) {
  observe(lifecycleOwner, object : Observer<T> {
    override fun onChanged(t: T?) {
      observer.onChanged(t)
      removeObserver(this)
    }
  })
}