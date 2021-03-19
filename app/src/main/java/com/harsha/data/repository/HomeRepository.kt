package com.harsha.data.repository

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response

interface HomeRepository {

    fun getPostDetails(): Single<Response<ResponseBody>>

}