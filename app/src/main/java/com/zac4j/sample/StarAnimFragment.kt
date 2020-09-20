package com.zac4j.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zac4j.sample.databinding.FragmentStarAnimBinding
import com.zac4j.sample.viewmodel.StarAnimViewModel

/**
 * Star property animation
 *
 * @author: zac
 * @date: 2020/9/20
 */
class StarAnimFragment : Fragment() {

  private lateinit var mViewBinding: FragmentStarAnimBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    mViewBinding = FragmentStarAnimBinding.inflate(inflater, container, false)
    mViewBinding.viewModel = StarAnimViewModel()
    return mViewBinding.root
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
  }

}