package commanderpepper.catdog.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CatDogListFragmentViewModel(application: Application) : AndroidViewModel(application) {
    val catDogUrls = MutableLiveData<List<String>>()
    private val urlAnimalChannel: Channel<UrlAnimal> = Channel(Channel.UNLIMITED)
    val urlFlow: Flow<UrlAnimal> = urlAnimalChannel.consumeAsFlow()

    private val amount = 4

//    private val localDataSource: DatabaseLocalSource = DatabaseLocalSource.getInstance(application.applicationContext)!!

    private val repository = CatDogRepository

    lateinit var option: String
    lateinit var optionSC: Option

    val context = viewModelScope.coroutineContext + Dispatchers.IO

    suspend fun getUrlAnimals() {
        viewModelScope.launch(Dispatchers.IO) {
            when (optionSC) {
                is Option.CAT -> {
                    repository.getCatList(amount).map {
                        UrlAnimal.UrlCat(Cat(it))
                    }.addUrlsToChannel()
                }
                is Option.DOG -> {
                    repository.getDogList(amount).map {
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
                    repository.getCatsAndDogs(amount).addUrlsToChannel()
                }
            }
        }
    }

    private suspend fun List<UrlAnimal>.addUrlsToChannel() {
        this.forEach {
            urlAnimalChannel.send(it)
        }
    }

    /**
     * Sets the catDogUrls list according to the option from the user
     */
//    fun getUrls() {
//        runBlocking {
//            when (option) {
//                "CAT" -> if (catDogUrls.value == null)
//                    catDogUrls.value =
//                        withContext(context) {
//                            CatDogRepository.getCatList(10)
//                        }
//                "DOG" -> if (catDogUrls.value == null) catDogUrls.value =
//                    withContext(context) {
//                        CatDogRepository.getDogList(10)
//                    }
//                "BOTH" -> if (catDogUrls.value == null) {
//                    catDogUrls.value =
//                        withContext(context) {
//                            CatDogRepository.getCatList(1)
//                        }
//                    for (i in 1 until 10) {
//                        if (i % 2 == 0) {
//                            catDogUrls.value =
//                                catDogUrls.value!! + withContext(context) {
//                                    CatDogRepository.getCatList(1)
//                                }
//                        } else {
//                            catDogUrls.value =
//                                catDogUrls.value!! + withContext(context) {
//                                    CatDogRepository.getDogList(1)
//                                }
//                        }
//                    }
//                }
//                "CATFAV" -> catDogUrls.value =
//                    withContext(context) {
//                        localDataSource.getCatListFromDatabase()
//                    }
//                "DOGFAV" -> catDogUrls.value =
//                    withContext(context) {
//                        localDataSource.getDogListFromDatabase()
//                    }
//                "BOTHFAV" -> catDogUrls.value =
//                    withContext(context) {
//                        localDataSource.getListFromDatabase()
//                    }
//            }
//        }
//        Log.d("CATDOGURLS", catDogUrls.value.toString())
//    }

//    /**
//     * Adds urls, used with endless scrolling
//     */
//    fun addUrls(amount: Int) {
//        when (option) {
//            "CAT" -> addCatUrlsToList(amount)
//            "DOG" -> addDogUrlsToList(amount)
//            "BOTH" -> addBothUrlstoList(amount)
//        }
//    }
//
//    private fun addCatUrlsToList(amount: Int) {
//        runBlocking {
//            catDogUrls.value =
//                catDogUrls.value!! + withContext(context) {
//                    CatDogRepository.getCatList(amount)
//                }
//        }
//    }
//
//    private fun addDogUrlsToList(amount: Int) {
//        runBlocking {
//            catDogUrls.value =
//                catDogUrls.value!! + withContext(context) {
//                    CatDogRepository.getDogList(amount)
//                }
//        }
//    }
//
//    private fun addBothUrlstoList(amount: Int) {
//        runBlocking {
//            for (i in 0 until amount) {
//                if (i % 2 == 0) {
//                    catDogUrls.value =
//                        catDogUrls.value!! + withContext(context) {
//                            CatDogRepository.getCatList(1)
//                        }
//                } else {
//                    catDogUrls.value =
//                        catDogUrls.value!! + withContext(context) {
//                            CatDogRepository.getDogList(1)
//                        }
//                }
//            }
//        }
//
//    }
//
//    /**
//     * Adds urls to the database
//     */
//    fun saveFavoriteUrl(url: String, position: Int) {
//        Log.d("SAVE", "Saving URL")
//        Log.d("SAVE", option)
//        when (option) {
//            "CAT" -> saveCatUrlToDatabase(url)
//            "DOG" -> saveDogUrlToDatabase(url)
//            "BOTH" -> saveUrlToDatabaseUsingPosition(url, position)
//        }
//    }
//
//    /**
//     * Deletes urls from database
//     */
//    fun deleteFavoriteUrl(url: String, position: Int) {
//        when (option) {
//            "CATFAV" -> deleteCatUrlFromDatabase(url)
//            "DOGFAV" -> deleteDogUrlFromDatabase(url)
//            "BOTHFAV" -> deleteUrlFromDatabaseUsingUrl(url, position)
//        }
//    }
//
//
//    private fun saveCatUrlToDatabase(url: String) {
//        Log.d("SAVECAT", "Saving CAT URL")
//        runBlocking {
//            withContext(context) {
//                localDataSource.addCattoDatabase(url)
//            }
//        }
//    }
//
//    private fun saveDogUrlToDatabase(url: String) {
//        runBlocking {
//            withContext(context) {
//                localDataSource.addDogtoDatabase(url)
//            }
//        }
//
//    }
//
//    private fun saveUrlToDatabaseUsingPosition(url: String, position: Int) {
//        if (position % 2 == 0) {
//            saveCatUrlToDatabase(url)
//        } else {
//            saveDogUrlToDatabase(url)
//        }
//    }
//
//    private fun deleteCatUrlFromDatabase(url: String) {
//        runBlocking {
//            withContext(context) {
//                localDataSource.deleteCatFromDatabase(url)
//            }
//        }
//        getUrls()
//    }
//
//    private fun deleteDogUrlFromDatabase(url: String) {
//        runBlocking {
//            withContext(context) {
//                localDataSource.deleteDogFromDatabase(url)
//            }
//        }
//        getUrls()
//    }
//
//    private fun deleteUrlFromDatabaseUsingUrl(url: String, position: Int) {
//        if (position % 2 == 0) {
//            deleteCatUrlFromDatabase(url)
//        } else {
//            deleteDogUrlFromDatabase(url)
//        }
//    }

}