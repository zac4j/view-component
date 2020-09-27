package com.zac4j.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zac4j.sample.databinding.FragmentHomeBinding

/**
 * Home page
 *
 * @author: zac
 * @date: 2020/9/27
 */
class HomeFragment : Fragment() {

  private lateinit var mViewBinding: FragmentHomeBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    mViewBinding = FragmentHomeBinding.inflate(inflater, container, false)
    return mViewBinding.root
  }
}