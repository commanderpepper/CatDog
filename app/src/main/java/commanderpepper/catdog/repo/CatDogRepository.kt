package commanderpepper.catdog.repo

import commanderpepper.catdog.retrofit.CatService
import commanderpepper.catdog.retrofit.DogService
import kotlinx.coroutines.*

class CatDogRepository {
    var catService: CatService = CatService.create()
    var dogService: DogService = DogService.create()

    suspend fun getCatUrl(): String {
        var url = ""

        withContext(Dispatchers.IO) {
            url = catService.getCat().file
        }

        return url
    }

//    fun getDogUrl(): String {
//        var url = ""
//        GlobalScope.launch {
//            url = dogService.getDog().url
//        }
//        val t = GlobalScope.async { dogService.getDog().url }
//        val z = t.await()
//        return z
//    }

    companion object {
        private val catDogRepository: CatDogRepository? = null
        fun create(): CatDogRepository {
            return catDogRepository ?: CatDogRepository()
        }
    }

}