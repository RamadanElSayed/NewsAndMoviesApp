package com.example.newsapp.data.remote

import com.example.newsapp.data.models.UploadResponse
import com.example.newsapp.data.models.User
import com.example.newsapp.data.models.UserProfile
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface ApiService {

    // 1. Basic HTTP Methods (GET, POST, PUT, DELETE)

    // @GET: Retrieves data from the server
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<User>

    // @POST: Sends new data to the server
    @POST("users")
    suspend fun createUser(@Body user: User): Response<User>

    // @PUT: Updates existing data on the server
    @PUT("users/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body user: User): Response<User>

    // @DELETE: Removes data from the server
    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Response<Unit>

    // 2. @Path: Dynamically inserts values into the endpoint URL
    @GET("users/{id}")
    suspend fun fetchUserById(@Path("id") userId: Int): Response<User>

    // 3. @Query: Adds query parameters for filtering
    @GET("users")
    suspend fun getUsersByStatus(@Query("status") status: String): Response<List<User>>

    // 3. @QueryMap: Adds multiple query parameters dynamically from a map
    @GET("users")
    suspend fun getUsersByFilters(@QueryMap filters: Map<String, String>): Response<List<User>>

    // Example usage of @QueryMap
    // Filters can be passed as: mapOf("age" to "30", "city" to "New York", "job" to "Developer")

    // 4. @Body: Serializes data and sends it in the request body
    @POST("users")
    suspend fun registerUser(@Body user: User): Response<User>

    // 5. Multipart and @Part for file uploads
    @Multipart
    @POST("upload")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Response<UploadResponse>

    // Creating Multipart parts:
    // File part: MultipartBody.Part.createFormData("file", file.name, file.asRequestBody("image/*".toMediaTypeOrNull()))
    // Text part: RequestBody.create("text/plain".toMediaTypeOrNull(), "Description text here")

    // 6. @Url for dynamic URLs
    @GET
    suspend fun getUsersByUrl(@Url url: String): Response<List<User>>

    // 7. @FormUrlEncoded and @Field for form-encoded requests
    @FormUrlEncoded
    @POST("api/users")
    suspend fun doCreateUserWithField(
        @Field("name") name: String,
        @Field("job") job: String
    ): Response<User>

    // 8. Custom Headers
    @GET("profile")
    suspend fun getUserProfile(@Header("Authorization") token: String): Response<UserProfile>

    // Multiple Headers with @HeaderMap
    @GET("profile")
    suspend fun getUserProfileWithHeaders(@HeaderMap headers: Map<String, String>): Response<UserProfile>


}