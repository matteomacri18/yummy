package com.matteomacri.gnam.services

import com.matteomacri.gnam.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipesApiService {
    companion object {
        var retrofit: Retrofit? = null

        fun getRetrofitClient(): RecipesInterface {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(RecipesInterface::class.java)
        }

    }
}