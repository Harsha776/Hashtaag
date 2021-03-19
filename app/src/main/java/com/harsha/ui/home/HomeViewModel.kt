package com.harsha.ui.home

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harsha.common.Constants.PATH
import com.harsha.common.NoConnectivityException
import com.harsha.common.Utility
import com.harsha.data.model.ImagePojo
import com.harsha.data.repository.HomeRepository
import com.harsha.data.repository.ImageRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    mContext: Context
) : ViewModel() {

    val progressBar = ObservableBoolean(false)
    var liveImageData: LiveData<List<ImagePojo>>? = null
    private var mContext: Context = mContext
    val netWorkState = MutableLiveData<Boolean>()

    /**
     * <h2>getImageDetails</h2>
     * this is the method to the image url from post Api
     */
    fun getImageDetails() {
        progressBar.set(true)
        val disposableObserver =
            object : DisposableSingleObserver<Response<ResponseBody>>() {
                override fun onSuccess(value: Response<ResponseBody>) {
                    progressBar.set(false)
                    netWorkState.postValue(false)
                    val path = value.body()!!.string()
                    val jsonObject = JSONObject(path)
                    saveData(jsonObject.getString(PATH))
                }

                override fun onError(e: Throwable) {
                    progressBar.set(false)
                    if (e is NoConnectivityException) {
                        netWorkState.postValue(true)
                        Log.d("TAG", "onError: " + e.message)
                    } else {
                        Log.d("TAG", "onError: " + e.message)
                    }
                }
            }
        homeRepository.getPostDetails().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposableObserver)

    }

    fun saveData(url: String) {
        AsyncTask.execute {
            var bitmap = Utility.getBitmapFromURL(url)
            var image = ImagePojo()
            image.image = bitmap
            ImageRepository.insertImage(mContext, image)
        }
        getImageFromRoom()
    }

    fun getImageFromRoom(): LiveData<List<ImagePojo>>? {
        liveImageData = ImageRepository.getImageData(mContext)
        return liveImageData
    }

    fun checkNetworkState(): MutableLiveData<Boolean> {
        return netWorkState
    }
}