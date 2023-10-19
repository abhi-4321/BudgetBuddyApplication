package com.example.budgetbuddy.di

import android.util.Log
import com.example.budgetbuddy.api.AuthInterceptor

import com.example.budgetbuddy.api.BudgetApi
import com.example.budgetbuddy.api.UserApi
import com.example.budgetbuddy.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }


//    @Singleton
//    @Provides
//    fun provideOkHttpClient(interceptor: AuthInterceptor): OkHttpClient {
//        return OkHttpClient.Builder().addInterceptor(interceptor).build()
//    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: AuthInterceptor): OkHttpClient {
    Log.d("budgetResponse", interceptor.toString())
    Log.d("budgetResponse", OkHttpClient.Builder().addInterceptor(interceptor).toString())
    return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }


    @Singleton
    @Provides
    fun providesUserAPI(retrofitBuilder: Retrofit.Builder): UserApi {

        return retrofitBuilder.build().create(UserApi::class.java)
    }

//    @Singleton
//    @Provides
//    fun provideBudgetApi(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): BudgetApi {
//        Log.d("okhttpresponse","hello" )
//        return retrofitBuilder.client(okHttpClient).build().create(BudgetApi::class.java)
//    }


    @Singleton
    @Provides
    fun provideBudgetApi(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): BudgetApi {
        Log.d("budgetResponse", "in noteApi")
        return retrofitBuilder.client(okHttpClient).build().create(BudgetApi::class.java)
    }


}