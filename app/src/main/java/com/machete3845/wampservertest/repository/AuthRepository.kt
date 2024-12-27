package com.machete3845.wampservertest.repository

import android.util.Log
import com.google.gson.Gson
import com.machete3845.wampservertest.api.AuthApi
import com.machete3845.wampservertest.viewModels.User
import org.json.JSONObject
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
    private val gson: Gson
) {
    suspend fun login(username: String, password: String): User {
        val response = authApi.login(username, password)
        if (response.isSuccessful) {
            val responseBody = response.body()?.string()
            Log.d("AuthRepository", "Server response: $responseBody")

            return try {
                val jsonObject = JSONObject(responseBody)
                if (jsonObject.getString("status") == "success") {
                    val userJson = jsonObject.getJSONObject("user")
                    User(
                        id = userJson.getString("id"),
                        name = userJson.getString("name"),
                        role = userJson.getInt("role")
                    )
                } else {
                    throw Exception(jsonObject.getString("message"))
                }
            } catch (e: Exception) {
                Log.e("AuthRepository", "Error parsing JSON: ${e.message}")
                throw Exception("Invalid credentials")
            }
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("AuthRepository", "Server error: $errorBody")
            throw Exception(errorBody ?: "Unknown error occurred")
        }
    }
}

