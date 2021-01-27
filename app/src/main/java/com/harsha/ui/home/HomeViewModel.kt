package com.harsha.ui.home

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harsha.common.Constants.SPLASH_DELAY_MILLIS
import com.harsha.common.Constants.TRUE
import com.harsha.common.NoConnectivityException
import com.hemanth.cricbuzz.data.model.NewsResponse
import com.hemanth.cricbuzz.data.repository.HomeRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject


class HomeViewModel @Inject constructor( private val homeRepository: HomeRepository):ViewModel() {

    private val _time = MutableLiveData<Boolean>()
     val progressBar=ObservableBoolean(false)

     fun delayScreen(){
         Handler(Looper.getMainLooper()).postDelayed({
//             _time.postValue(TRUE)
             getNewsDetails()
         }, SPLASH_DELAY_MILLIS)
    }

    /**
     * <h2>getNewsDetails</h2>
     * this is the method to get news response from Api
     */
    fun getNewsDetails() {
        progressBar.set(true)
        val disposableObserver =
                object : DisposableSingleObserver<Response<NewsResponse>>() {
                    override fun onSuccess(value: Response<NewsResponse>) {
                        progressBar.set(false)
                        Log.d("TAG", "onError: "+value.code())
                    }

                    override fun onError(e: Throwable) {
                        progressBar.set(false)
                        if (e is NoConnectivityException) {
                            Log.d("TAG", "onError: "+e.message)
//                            _eventNewsArticle.postValue(Event(Pair(Constants.NO_INTERNET, e.message)))
                        }
                        else {
                            Log.d("TAG", "onError: "+e.message)
//                            _eventNewsArticle.postValue(Event(Pair(Constants.ERROR, e.message)))
                        }
                    }
                }
        homeRepository.getNewsDetails().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver)

    }

    fun onDelayObserver():MutableLiveData<Boolean>{
        return _time
    }
}