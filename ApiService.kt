package com.example.tugaspraktikum.network

import com.example.tugaspraktikum.model.LoginRequest
import com.example.tugaspraktikum.model.LoginResponse
import com.example.tugaspraktikum.model.PasienResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("api/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("api/pasien")
    fun getPasien(@Header("Authorization") token: String): Call<PasienResponse>
}