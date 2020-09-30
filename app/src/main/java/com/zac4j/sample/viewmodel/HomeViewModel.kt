package com.zac4j.sample.viewmodel

import android.os.Bundle
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.zac4j.sample.ARGS_VIEW_TYPE
import com.zac4j.sample.R
import com.zac4j.sample.VIEW_TYPE_CLIP_RECT
import com.zac4j.sample.VIEW_TYPE_SPOTLIGHT

/**
 * ViewModel for home page.
 *
 * @author: zac
 * @date: 2020/9/30
 */
class HomeViewModel : ViewModel() {

  private val _navDirection = MutableLiveData<NavDirections>()
  val navDirection: LiveData<NavDirections>
    get() = _navDirection

  fun navigate(buttonId: Int) {
    val navDirection = when (buttonId) {
      R.id.main_btn_show_rect -> {
        object : NavDirections {
          override fun getActionId() = R.id.action_home_to_custom_view

          override fun getArguments() = bundleOf(ARGS_VIEW_TYPE to VIEW_TYPE_CLIP_RECT)
        }
      }
      else -> {
        object : NavDirections {
          override fun getActionId() = R.id.action_home_to_custom_view

          override fun getArguments() = bundleOf(ARGS_VIEW_TYPE to VIEW_TYPE_SPOTLIGHT)
        }
      }
    }

    _navDirection.postValue(navDirection)
  }

}