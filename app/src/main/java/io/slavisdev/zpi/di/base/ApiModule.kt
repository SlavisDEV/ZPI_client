/*
 * Created by Sławomir Przybylski
 * 16/05/20 22:51
 */

package io.slavisdev.zpi.di.base

import android.content.Context
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.NormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCache
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.sql.ApolloSqlHelper
import com.apollographql.apollo.cache.normalized.sql.SqlNormalizedCacheFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import io.slavisdev.zpi.net.Api
import io.slavisdev.zpi.net.ConnectivityInterceptor
import io.slavisdev.zpi.net.RepeaterInterceptor
import io.slavisdev.zpi.settings.AppSettings
import io.slavisdev.zpi.utils.API_URL
import io.slavisdev.zpi.utils.GRAPH_QL_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .serializeNulls()
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context, appSettings: AppSettings): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(ConnectivityInterceptor(context))
            .addInterceptor(RepeaterInterceptor(appSettings))
            .addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder()
                    .method(original.method, original.body)
                builder.header("Authorization", appSettings.getAccessToken() ?: "")
                chain.proceed(builder.build())
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideApiRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(API_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun providesApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(GRAPH_QL_URL)
            .okHttpClient(okHttpClient)
            .build()
    }
}