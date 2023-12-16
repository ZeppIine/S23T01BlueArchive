package kr.ac.kumoh.ce.s20180147.s23t01bluearchive

import retrofit2.http.GET

interface StudentApi {
    @GET("student")
    suspend fun getStudent(): List<Student>
    @GET("academy")
    suspend fun getAcademy(): List<Academy>
}