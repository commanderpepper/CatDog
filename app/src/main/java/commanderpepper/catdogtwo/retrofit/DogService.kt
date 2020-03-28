package commanderpepper.catdogtwo.retrofit

import commanderpepper.catdogtwo.CatDogConstants
import commanderpepper.catdogtwo.models.Dog
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface DogService {
    @GET("/woof.json")
    suspend fun getDog(): Dog

    companion object {
        fun create(): DogService {
            val dogRetrofit = Retrofit.Builder()
                .baseUrl(CatDogConstants.dogBaseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            return dogRetrofit.create(DogService::class.java)
        }
    }
}