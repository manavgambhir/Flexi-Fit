package com.example.flexifit.data

import okhttp3.*

class MealQueryInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url()

        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("type", "public")
            .addQueryParameter("app_id", "ad0d843d")
            .addQueryParameter("app_key", "3eb7b7510a32ccdc4616ca352c55c04f")
            .addQueryParameter("cuisineType", "Indian")
            .build()

        val newRequest = original.newBuilder()
            .url(newUrl)
            .header("Edamam-Account-User","manavgambhir")
            .header("accept", "application/json")
            .addHeader("Accept-Language","en")
            .build()

        return chain.proceed(newRequest)
    }
}