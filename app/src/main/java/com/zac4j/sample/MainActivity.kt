package com.zac4j.sample

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.zac4j.sample.R.id
import com.zac4j.sample.R.layout
import com.zac4j.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {

  private var binding: ActivityMainBinding? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)

    binding = ActivityMainBinding.inflate(layoutInflater)
  }

  override fun onClick(view: View) {
    var fragment: Fragment? = supportFragmentManager.findFragmentById(id.main_container)
    when (view.id) {
      id.main_btn_show_star_anim -> {
        fragment = StarAnimFragment()
      }
      id.main_btn_show_paint -> {
        if (fragment is CustomViewFragment && fragment.getViewType() == CustomViewFragment.VIEW_TYPE_MINI_PAINT) {
          return
        }

        fragment = CustomViewFragment.newInstance(CustomViewFragment.VIEW_TYPE_MINI_PAINT)
      }
      id.main_btn_show_rect -> {
        if (fragment is CustomViewFragment && fragment.getViewType() == CustomViewFragment.VIEW_TYPE_CLIP_RECT) {
          return
        }

        fragment = CustomViewFragment.newInstance(CustomViewFragment.VIEW_TYPE_CLIP_RECT)
      }
      id.main_btn_show_spotlight -> {
        if (fragment is CustomViewFragment && fragment.getViewType() == CustomViewFragment.VIEW_TYPE_SPOTLIGHT) {
          return
        }

        fragment = CustomViewFragment.newInstance(CustomViewFragment.VIEW_TYPE_SPOTLIGHT)
      }
    }
    fragment?.let {
      supportFragmentManager.beginTransaction()
          .replace(id.main_container, it)
          .commit()
    }
  }
}