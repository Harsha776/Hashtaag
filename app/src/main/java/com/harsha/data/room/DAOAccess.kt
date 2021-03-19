package com.harsha.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.harsha.data.model.ImagePojo


@Dao
interface DAOAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imagePojo: ImagePojo)

    @Query("SELECT * FROM ImagePojo")
    fun getImage() : LiveData<List<ImagePojo>>?


}