package commanderpepper.catdog.repo

import android.graphics.Path
import android.util.Log
import commanderpepper.catdog.models.*
import commanderpepper.catdog.retrofit.CatService
import commanderpepper.catdog.retrofit.DogService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object CatDogRepository {
    private val catService: CatService = CatService.create()
    private val dogService: DogService = DogService.create()
    private val

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

    fun getCatFlow(): Flow<Cat> {
        return flow {
            emit(Cat(getCat()))
        }.flowOn(Dispatchers.IO)
    }

    fun getDogFlow(): Flow<Dog> {
        return flow {
            emit(Dog(getDog()))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUrls(option: Option, amount: Int): Flow<List<UrlAnimal>> {
        return when (option) {
            Option.CAT -> //TODO
            Option.DOG -> //TODO
            Option.BOTH -> //TODO
            Option.CATFAV -> DatabaseLocalSource.getInstance()!!.getCatListFromDatabase()
            Option.DOGFAV -> //TODO
        }
    }
}

