package commanderpepper.catdog.repo

import commanderpepper.catdog.retrofit.CatService
import commanderpepper.catdog.retrofit.DogService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

object CatDogRepository {
    private var catService: CatService = CatService.create()
    private var dogService: DogService = DogService.create()

    suspend fun getCatUrl() = catService.getCat().file

    suspend fun getDogUrl() = dogService.getDog().url

    fun getUseableCatUrlFromAPI(): String {
        var catUrl = ""
        runBlocking {
            withContext(Dispatchers.IO) {
                while (!catUrl.contains("jpg|png".toRegex())) {
                    catUrl = getCatUrl()
                }
            }
        }
        return catUrl
    }

    fun getUseableDogUrlFromAPI(): String {
        var dogUrl = ""
        runBlocking {
            withContext(Dispatchers.IO) {
                while (!dogUrl.contains("gif|jpg|png".toRegex())) {
                    dogUrl = getDogUrl()
                }
            }
        }
        return dogUrl
    }

    fun getListOfCatUrls(amount: Int): List<String> {
        val catUrls = mutableListOf<String>()
        for (i in 0 until amount) {
            catUrls.add(getUseableCatUrlFromAPI())
        }
        return catUrls.toList()
    }
}