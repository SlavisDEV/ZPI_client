/*
 * Created by Sławomir Przybylski
 * 21/05/20 19:59
 */

package io.slavisdev.zpi.data


import com.google.gson.annotations.SerializedName

data class TokenModel(
    @SerializedName("token")
    val token: String,
    @SerializedName("user")
    val userId: Int
)