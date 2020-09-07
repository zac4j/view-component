package com.zac4j.sample

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import com.zac4j.sample.R.id
import com.zac4j.sample.R.layout
import com.zac4j.widget.BadgeView

class MainActivity : Activity(), OnClickListener {
  private var mContentTextView: TextView? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)
    mContentTextView = findViewById(id.main_tv_text)
    findViewById<View>(id.main_btn_show_badge).setOnClickListener(this)
  }

  override fun onClick(view: View) {
    when (view.id) {
      id.main_btn_show_badge -> {
        val badgeView = BadgeView(this@MainActivity, mContentTextView)
        badgeView.increment(10)
        badgeView.show()
      }
    }
  }
}