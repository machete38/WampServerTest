package com.machete3845.wampservertest.apis

import com.machete3845.wampservertest.data.ServiceResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ServiceApi {
    @GET("get_services.php")
    suspend fun getServices(): Response<List<ServiceResponse>>

    @FormUrlEncoded
    @POST("save_service.php")
    suspend fun addService(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("description") description: String,
        @Field("line") line: String,
        @Field("price") price: String
    ): Response<Unit>

    @FormUrlEncoded
    @POST("delete_service.php")
    suspend fun deleteService(@Field("id") id: String): Response<Unit>
}

