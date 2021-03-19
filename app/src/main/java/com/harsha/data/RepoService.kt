package com.harsha.data

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.POST

interface RepoService {

    @POST("image/test")
    fun getPostDetails(): Single<Response<ResponseBody>>

}