package commanderpepper.catdog.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import commanderpepper.catdog.models.Cat
import commanderpepper.catdog.models.Dog
import commanderpepper.catdog.models.Option
import commanderpepper.catdog.models.UrlAnimal
import commanderpepper.catdog.repo.CatDogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

private const val amount = 2

class CatDogListFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val urlAnimalChannel: Channel<UrlAnimal> = Channel(Channel.UNLIMITED)

    /**
     * A list that holds urls. Useful for loading urls upon configuration changes (like a phone rotation).
     */
    private val urlAnimalList = mutableListOf<UrlAnimal>()

    /**
     * The first time using this view model the list is not used. When the fragment detaches, this is set to true and the list is used when the fragment is reattached.
     */
    private var useList = false

    private val repository = CatDogRepository

    lateinit var optionSC: Option

    suspend fun getUrlAnimals(urlAmount: Int = amount) {
        Log.d("ListViewModel", optionSC.toString())
        viewModelScope.launch(Dispatchers.IO) {
            when (optionSC) {
                is Option.CAT -> {
                    repository.getCatList(urlAmount).map {
                        UrlAnimal.UrlCat(Cat(it))
                    }.addUrlsToChannel()
                }
                is Option.DOG -> {
                    repository.getDogList(urlAmount).map {
                        UrlAnimal.UrlDog(Dog(it))
                    }.addUrlsToChannel()
                }
                is Option.CATFAV -> {
                    repository.getFavoriteCats().map {
                        UrlAnimal.UrlCat(Cat(it))
                    }.addUrlsToChannel()
                }
                is Option.DOGFAV -> {
                    repository.getFavoriteDogs().map {
                        UrlAnimal.UrlDog(Dog(it))
                    }.addUrlsToChannel()
                }
                is Option.BOTH -> {
                    repository.getCatsAndDogs(urlAmount).addUrlsToChannel()
                }
            }
        }
    }

    private suspend fun List<UrlAnimal>.addUrlsToChannel() {
        this.forEach {
            urlAnimalChannel.send(it)
        }
        urlAnimalList += this
    }

    suspend fun checkForFavorites(option: Option) : Boolean{
        return repository.checkForFavorites(option)
    }

    fun getFlowOfUrlAnimals(): Flow<UrlAnimal> {
        return urlAnimalChannel.consumeAsFlow()
    }

    fun getListForAdapter(): MutableList<UrlAnimal> {
        return if (useList) {
            urlAnimalList
        } else
            mutableListOf()
    }

    fun fragmentDetach() {
        useList = true
    }
}