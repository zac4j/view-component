package com.zac4j.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zac4j.widget.ClippedView
import com.zac4j.widget.MiniPaint

/**
 * Desc:
 *
 * @author: zac
 * @date: 2020/9/9
 */
class CustomViewFragment : Fragment() {

  companion object {
    private const val ARGS_VIEW_TYPE = "view_type"

    const val VIEW_TYPE_MINI_PAINT = 0x101
    const val VIEW_TYPE_CLIP_RECT = 0x102

    fun newInstance(viewType: Int): CustomViewFragment {
      val args = Bundle()
      args.putInt(ARGS_VIEW_TYPE, viewType)
      val fragment = CustomViewFragment()
      fragment.arguments = args
      return fragment
    }
  }

  private var mViewType: Int? = VIEW_TYPE_MINI_PAINT

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.takeIf { it.containsKey(ARGS_VIEW_TYPE) }
        ?.let {
          mViewType = it.getInt(ARGS_VIEW_TYPE)
        }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return when (mViewType) {
      VIEW_TYPE_CLIP_RECT -> {
        return ClippedView(requireContext())
      }
      VIEW_TYPE_MINI_PAINT -> {
        val painter = MiniPaint(requireContext())
        painter.contentDescription = getString(R.string.canvasContentDescription)
        return painter
      }
      else -> super.onCreateView(inflater, container, savedInstanceState)
    }
  }
}