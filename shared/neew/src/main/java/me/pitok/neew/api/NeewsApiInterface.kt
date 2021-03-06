package me.pitok.neew.api

import me.pitok.neew.api.response.NeewsResponse
import me.pitok.networking.response.MessageResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface NeewsApiInterface {

    @GET("get_news")
    suspend fun getNeews(): Response<NeewsResponse>

    @POST("add_new_by_content")
    @FormUrlEncoded
    suspend fun addNeewByContent(@Field("content") content: String): Response<MessageResponse>

    @POST("add_new_by_twitter_link")
    @FormUrlEncoded
    suspend fun addNeewByTWLink(@Field("link") link: String): Response<MessageResponse>

    @POST("report")
    @FormUrlEncoded
    suspend fun report(@Field("new_id") neew_id: Int): Response<MessageResponse>

}