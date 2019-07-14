package commanderpepper.catdog.retrofit

import commanderpepper.catdog.CatDogConstants
import commanderpepper.catdog.R
import commanderpepper.catdog.models.Cat
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


interface CatService {
    @GET("/meow")
    suspend fun getCat(): Cat

    companion object {
        fun create(): CatService {
            val catRetroFit = Retrofit.Builder()
                .baseUrl(CatDogConstants.catBaseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            return catRetroFit.create(CatService::class.java)
        }

    }
}