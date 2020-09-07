package com.zac4j.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.zac4j.widget.BadgeView;

public class MainActivity extends Activity implements View.OnClickListener {

  private TextView mContentTextView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mContentTextView = findViewById(R.id.main_tv_text);
    findViewById(R.id.main_btn_show_badge).setOnClickListener(this);

  }

  @Override public void onClick(View view) {
    switch (view.getId()) {
      case R.id.main_btn_show_badge:
        BadgeView badgeView = new BadgeView(MainActivity.this, mContentTextView);
        badgeView.increment(10);
        badgeView.show();
        break;
    }
  }
}
