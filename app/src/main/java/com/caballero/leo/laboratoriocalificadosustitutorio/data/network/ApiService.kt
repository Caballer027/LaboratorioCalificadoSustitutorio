package com.caballero.leo.laboratoriocalificadosustitutorio.data.network

import com.caballero.leo.laboratoriocalificadosustitutorio.data.models.Post
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    fun getPosts(): Call<List<Post>>
}
