package com.zac4j.sample

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.zac4j.widget.ClippedView
import com.zac4j.widget.MiniPaint
import com.zac4j.widget.SpotLightImageView

/**
 * Page for display custom views.
 *
 * @author: zac
 * @date: 2020/9/9
 */
class CustomViewFragment : Fragment() {

  companion object {
    private const val ARGS_VIEW_TYPE = "view_type"

    const val VIEW_TYPE_MINI_PAINT = 0x101
    const val VIEW_TYPE_CLIP_RECT = 0x102
    const val VIEW_TYPE_SPOTLIGHT = 0x103
  }

  private var mViewType: Int? = VIEW_TYPE_MINI_PAINT

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.takeIf { it.containsKey(ARGS_VIEW_TYPE) }
        ?.let {
          mViewType = it.getInt(ARGS_VIEW_TYPE, VIEW_TYPE_MINI_PAINT)
        }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return when (mViewType) {
      VIEW_TYPE_CLIP_RECT -> {
        ClippedView(requireContext())
      }
      VIEW_TYPE_SPOTLIGHT -> {
        showSpotLightIntroductionDialog()
        SpotLightImageView(requireContext())
      }
      else -> {
        val painter = MiniPaint(requireContext())
        painter.contentDescription = getString(R.string.canvasContentDescription)
        painter
      }
    }
  }

  private fun showSpotLightIntroductionDialog() {
    AlertDialog.Builder(requireContext())
        .setIcon(com.zac4j.widget.R.drawable.android)
        .setTitle(com.zac4j.widget.R.string.instructions_title)
        .setMessage(com.zac4j.widget.R.string.instructions)
        .setIcon(ContextCompat.getDrawable(requireContext(), android.R.drawable.ic_media_play))
        .setPositiveButton("OK", null)
        .create()
        .show()
  }

}