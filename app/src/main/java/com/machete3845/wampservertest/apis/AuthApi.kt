package com.machete3845.wampservertest.api

import com.machete3845.wampservertest.viewModels.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {
        @FormUrlEncoded
        @POST("login.php")
        suspend fun login(
                @Field("username") username: String,
                @Field("password") password: String
        ): Response<ResponseBody>
}