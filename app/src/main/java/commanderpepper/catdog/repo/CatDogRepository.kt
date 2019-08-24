package commanderpepper.catdog.repo

import commanderpepper.catdog.retrofit.CatService
import commanderpepper.catdog.retrofit.DogService
import commanderpepper.catdog.room.AnimalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

object CatDogRepository {
    private var catService: CatService = CatService.create()
    private var dogService: DogService = DogService.create()

    private suspend fun getCatUrl() = catService.getCat().file

    private suspend fun getDogUrl() = dogService.getDog().url

    /**
     * Gets cat urls and filters them. Rejects any cat urls that aren't jpgs or pngs.
     */
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

    /**
     * Get dog urls and filters them. Rejects any cat urls that aren't jpgs, pngs or gifs
     */
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



    /**
     * Get a list of dog urls
     */
    fun getListOfDogUrls(amount: Int): List<String> {
        val dogUrls = mutableListOf<String>()
        for (i in 0 until amount) {
            dogUrls.add(getUseableDogUrlFromAPI())
        }
        return dogUrls.toList()
    }

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

}