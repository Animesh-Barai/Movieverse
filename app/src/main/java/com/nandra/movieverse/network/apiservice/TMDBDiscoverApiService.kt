package com.nandra.movieverse.network.apiservice

import com.nandra.movieverse.network.ConnectivityInterceptor
import com.nandra.movieverse.network.response.DiscoverResponse
import com.nandra.movieverse.util.Constant.API_KEY_MOVIE_DB
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBDiscoverApiService {

    @GET("movie")
    suspend fun getMovie(
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int
    ) : Response<DiscoverResponse>

    @GET("tv")
    suspend fun getTVSeries(
        @Query("language") language: String,
        @Query("sort_by") sortBy: String,
        @Query("page") page: Int
    ) : Response<DiscoverResponse>

    @GET("movie")
    suspend fun getSpecifiedDateReleaseMovie(
        @Query("primary_release_date.gte") dateGte: String,
        @Query("primary_release_date.lte") dateLte: String
    ) : Response<DiscoverResponse>

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor) : TMDBDiscoverApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY_MOVIE_DB)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.themoviedb.org/3/discover/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TMDBDiscoverApiService::class.java)
        }
    }
}