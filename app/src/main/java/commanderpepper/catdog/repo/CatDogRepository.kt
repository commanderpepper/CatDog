package commanderpepper.catdog.repo

import commanderpepper.catdog.retrofit.CatService
import commanderpepper.catdog.retrofit.DogService

object CatDogRepository {
    private var catService: CatService = CatService.create()
    private var dogService: DogService = DogService.create()

    suspend fun getCatUrl() = catService.getCat().file

    suspend fun getDogUrl() = dogService.getDog().url

}