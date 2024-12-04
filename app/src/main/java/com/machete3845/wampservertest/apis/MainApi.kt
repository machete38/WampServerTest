package com.machete3845.wampservertest.apis

import com.machete3845.wampservertest.data.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MainApi {
    @GET("get_all_items.php")
    suspend fun getAllUsers(): List<User>

    @POST("save_user.php")
    suspend fun saveUser(@Body user: User)
}