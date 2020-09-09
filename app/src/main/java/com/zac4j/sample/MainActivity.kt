package com.zac4j.sample

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
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
    when (view.id) {
      id.main_btn_show_badge -> {
//        val badgeView = BadgeView(this@MainActivity, mContentTextView)
//        badgeView.increment(10)
//        badgeView.show()
      }
      id.main_btn_show_paint -> {
        val fragment = supportFragmentManager.findFragmentById(id.main_container)
        if (fragment == null) {
          val page = MiniPaintFragment()
          supportFragmentManager.beginTransaction()
              .add(id.main_container, page)
              .commit()
        }
      }
    }
  }
}