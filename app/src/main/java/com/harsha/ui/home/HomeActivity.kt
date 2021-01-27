package com.harsha.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.harsha.ui.splashactivity.R
import com.harsha.ui.splashactivity.databinding.HomeActivityBinding
import com.harsha.ui.splashactivity.databinding.SplashActivityBinding
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var mBinding:HomeActivityBinding
    lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeView()
        initializeViewModel()
        runAnimation()
        dalayObserver()
    }

    /**
     * this method is used to initialize the view
     */
    private fun initializeView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.home_activity)
    }

    /**
     * This method is used initialize the viewModel class for this activity.
     */
    private fun initializeViewModel() {
        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        mBinding.viewModel=homeViewModel
        homeViewModel.delayScreen()
    }

    /**
     * text animation
     */
    private fun runAnimation() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        mBinding.tvSplashTitle.startAnimation(animation)
    }

    /**
     * observing the delay to move next activity
     */
    private fun dalayObserver(){
        homeViewModel.onDelayObserver().observe(this, Observer {
            if(it){

                Toast.makeText(this,"Home Activity",Toast.LENGTH_SHORT).show()
            }
        })
    }
}