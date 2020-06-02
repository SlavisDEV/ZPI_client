package io.slavisdev.zpi.net

import io.slavisdev.zpi.data.TokenModel
import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

    @POST("token/")
    @FormUrlEncoded
    fun getToken(
        @Field("username") username: String,
        @Field("password") password: String
    ): Deferred<TokenModel>
}