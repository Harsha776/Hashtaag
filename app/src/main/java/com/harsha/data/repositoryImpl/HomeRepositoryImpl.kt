package com.harsha.data.repositoryImpl

import com.harsha.data.RepoService
import com.hemanth.cricbuzz.data.model.NewsResponse
import com.hemanth.cricbuzz.data.repository.HomeRepository
import io.reactivex.Single
import retrofit2.Response

class HomeRepositoryImpl(
    private val repoService: RepoService
) : HomeRepository {

    override fun getNewsDetails(): Single<Response<NewsResponse>> = repoService.getNewsDetails()

}