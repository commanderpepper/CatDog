package commanderpepper.catdog.repo

import commanderpepper.catdog.models.AnimalUrl
import commanderpepper.catdog.models.Cat
import commanderpepper.catdog.models.Url
import commanderpepper.catdog.models.UrlAnimal
import commanderpepper.catdog.retrofit.CatService
import commanderpepper.catdog.retrofit.DogService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow

object CatDogRepository {
    private var catService: CatService = CatService.create()
    private var dogService: DogService = DogService.create()

    private suspend fun getCatUrl() = catService.getCat().file

    private suspend fun getDogUrl() = dogService.getDog().url


    suspend fun getCat(): String {
        var catUrl = ""
        while (!catUrl.contains("jpg|png".toRegex())) {
            catUrl = getCatUrl()
        }
        return catUrl
    }

    suspend fun getDog(): String {
        var dogUrl = ""
        while (!dogUrl.contains("gif|jpg|png".toRegex())) {
            dogUrl = getDogUrl()
        }
        return dogUrl
    }

    suspend fun getCatList(amount: Int): List<String> {
        val catUrls = mutableListOf<String>()
        for (i in 0 until amount) {
            catUrls.add(getCat())
        }
        return catUrls
    }

    suspend fun getDogList(amount: Int): List<String> {
        val dogUrls = mutableListOf<String>()
        for (i in 0 until amount) {
            dogUrls.add(getDog())
        }
        return dogUrls
    }

    fun getUrls(options: String, amount: Int): Flow<UrlAnimal> {
        return flow {
            getCatList(amount).map { it -> UrlAnimal.UrlCat(Cat(it)) }.asFlow()
        }
    }
}
