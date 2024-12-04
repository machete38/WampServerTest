package com.machete3845.wampservertest.apis

import com.machete3845.wampservertest.data.Incident
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.sql.Date


interface IncidentApi {
    @GET("get_incidents.php")
    suspend fun getIncidents(): Response<List<Incident>>

    @FormUrlEncoded
    @POST("save_incident.php")
    suspend fun createIncident(
        @Field("id") id: String,
        @Field("title") title: String,
        @Field("description") description: String,
        @Field("priority") priority: String,
        @Field("status") status: String,
        @Field("createdAt") createdAt: Date,
        ): Response<Unit>


}