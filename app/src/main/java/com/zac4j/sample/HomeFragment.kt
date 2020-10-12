package com.zac4j.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.zac4j.sample.databinding.FragmentHomeBinding
import com.zac4j.sample.viewmodel.HomeViewModel

/**
 * Home page
 *
 * @author: zac
 * @date: 2020/9/27
 */
class HomeFragment : Fragment() {

  private lateinit var mViewBinding: FragmentHomeBinding
  private lateinit var mViewModel: HomeViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    mViewBinding = FragmentHomeBinding.inflate(inflater, container, false)

    mViewModel = HomeViewModel()

    mViewBinding.viewModel = mViewModel

    return mViewBinding.root
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    mViewModel.navDirection.observe(viewLifecycleOwner, Observer {
      findNavController().navigate(it)
    })
  }
}