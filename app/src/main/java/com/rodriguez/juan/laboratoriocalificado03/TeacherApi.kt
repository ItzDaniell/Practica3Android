package com.rodriguez.juan.laboratoriocalificado03
import retrofit2.Response;
import retrofit2.http.GET;

interface TeacherApi {
    @GET("/list/teacher")
    suspend fun getTeachers(): Response<TeacherListResponse>
}