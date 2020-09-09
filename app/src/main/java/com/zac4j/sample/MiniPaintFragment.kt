package com.zac4j.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zac4j.widget.MiniPaint

/**
 * Desc:
 *
 * @author: zac
 * @date: 2020/9/9
 */
class MiniPaintFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val painter = MiniPaint(requireContext())
    painter.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    painter.contentDescription = getString(R.string.canvasContentDescription)
    return painter
  }
}