package com.harsha.ui.home

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.harsha.ui.splashactivity.R
import com.harsha.ui.splashactivity.databinding.HomeActivityBinding
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
        observeRoomDatabase()
        observeNetworkState()
    }

    /**
     * this method is used to initialize the view
     */
    private fun initializeView() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.home_activity)
        mBinding.tvClick.setOnClickListener {
            homeViewModel.getImageDetails()
        }
    }

    /**
     * This method is used initialize the viewModel class for this activity.
     */
    private fun initializeViewModel() {
        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        mBinding.viewModel=homeViewModel
    }

    /**
     * observing the room database data
     */
    private fun observeRoomDatabase(){
        homeViewModel.getImageFromRoom()!!.observe(this, Observer {
            if(it!=null && it.size>0){
                var byte= it[0].image
                val bmp = BitmapFactory.decodeByteArray(byte, 0,byte.size)
                mBinding.tvSplashTitle.setImageBitmap(bmp)
            }

        })
    }

    /**
     * observing the network state
     */
    private fun observeNetworkState(){
        homeViewModel.checkNetworkState().observe(this, Observer {
            if(it!=null && it)
                mBinding.clNetWork.visibility= View.VISIBLE
            else
                mBinding.clNetWork.visibility= View.GONE
        })
    }
}