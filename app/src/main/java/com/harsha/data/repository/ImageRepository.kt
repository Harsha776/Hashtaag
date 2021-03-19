package com.harsha.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.harsha.data.model.ImagePojo
import com.harsha.data.room.ImageDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ImageRepository {

    companion object {

        var imageDataBase: ImageDataBase? = null

        var imageData:LiveData<List<ImagePojo>>?=null

        fun initializeDB(context: Context) : ImageDataBase {
            return ImageDataBase.getDataseClient(context)
        }

        fun insertImage(context: Context, imagePojo:ImagePojo) {

            imageDataBase = initializeDB(context)

            CoroutineScope(IO).launch {
                imageDataBase!!.daoAccess().insertImage(imagePojo)
            }

        }

        fun getImageData(context: Context) : LiveData<List<ImagePojo>>? {
            imageDataBase = initializeDB(context)
            imageData = imageDataBase!!.daoAccess().getImage()
            return imageData
        }

    }
}