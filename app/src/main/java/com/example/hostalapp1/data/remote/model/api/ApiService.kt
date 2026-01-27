package com.example.hostalapp1.data.remote.model.api

import com.example.hostalapp1.data.remote.model.model.HostalExternoDto
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun obtenerHostalesExternos(): List<HostalExternoDto>
}