package com.machete3845.wampservertest.apis

import com.machete3845.wampservertest.data.Message
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface MessageApi {
    @GET("get_messages.php")
    suspend fun getMessages(): Response<List<Message>>

    @FormUrlEncoded
    @POST("send_message.php")
    suspend fun sendMessage(
        @Field("username") username: String,
        @Field("msg") msg: String
    ): Response<Unit>
}