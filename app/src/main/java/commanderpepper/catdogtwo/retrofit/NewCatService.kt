package commanderpepper.catdogtwo.retrofit

import commanderpepper.catdogtwo.CatDogConstants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface NewCatService {
    @GET("/cat?json=true")
    fun getCat(): NewCat

    @GET("/cat")
    fun getCatImage(): String?

    companion object {
        fun create(): NewCatService {
            val newCatRetrofit = Retrofit.Builder()
                .baseUrl(CatDogConstants.newCatBaseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

            return newCatRetrofit.create(NewCatService::class.java)
        }
    }
}

data class NewCat(val _id: String, val mimetype: String, val size: Int, val tags: List<String>)
