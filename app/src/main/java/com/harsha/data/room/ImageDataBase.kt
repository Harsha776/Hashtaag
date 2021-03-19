package com.harsha.data.room

import android.content.Context
import androidx.room.*
import com.harsha.data.model.ImagePojo

@Database(entities = [ImagePojo::class], version = 1, exportSchema = false)
abstract class ImageDataBase : RoomDatabase() {

    abstract fun daoAccess() : DAOAccess

    companion object {

        @Volatile
        private var INSTANCE: ImageDataBase? = null

        fun getDataseClient(context: Context) : ImageDataBase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, ImageDataBase::class.java, "IMAGE_DATABASE")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }

    }

}