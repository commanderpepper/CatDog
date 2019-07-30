package commanderpepper.catdog.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import commanderpepper.catdog.repo.CatDogRepository
import commanderpepper.catdog.repo.DatabaseLocalSource

class CatDogListFragmentViewModel(application: Application) : AndroidViewModel(application) {
    val catDogUrls = MutableLiveData<List<String>>()
    var localDataSource: DatabaseLocalSource = DatabaseLocalSource.getInstance(application.applicationContext)!!
    lateinit var option: String

    /**
     * Sets the catDogUrls list according to the option from the user
     */
    fun getUrls() {
        when (option) {
            "CAT" -> if (catDogUrls.value == null) catDogUrls.value = CatDogRepository.getListOfCatUrls(10)
            "DOG" -> if (catDogUrls.value == null) catDogUrls.value = CatDogRepository.getListOfDogUrls(10)
            "BOTH" -> if (catDogUrls.value == null) {
                catDogUrls.value = CatDogRepository.getListOfCatUrls(1)
                for (i in 1 until 10) {
                    if (i % 2 == 0) {
                        catDogUrls.value = catDogUrls.value!! + CatDogRepository.getListOfCatUrls(1)
                    } else {
                        catDogUrls.value = catDogUrls.value!! + CatDogRepository.getListOfDogUrls(1)
                    }
                }
            }
            "CATFAV" -> catDogUrls.value = localDataSource.getCatUrls()
            "DOGFAV" -> catDogUrls.value = localDataSource.getDogUrls()
            "BOTHFAV" -> catDogUrls.value = localDataSource.getList()
        }
        Log.d("CATDOGURLS", catDogUrls.value.toString())
    }

    /**
     * Adds urls, used with endless scrolling
     */
    fun addUrls(amount: Int) {
        when (option) {
            "CAT" -> addCatUrlsToList(amount)
            "DOG" -> addDogUrlsToList(amount)
            "BOTH" -> addBothUrlstoList(amount)
        }
    }

    private fun addCatUrlsToList(amount: Int) {
        catDogUrls.value = catDogUrls.value!! + CatDogRepository.getListOfCatUrls(amount)
    }

    private fun addDogUrlsToList(amount: Int) {
        catDogUrls.value = catDogUrls.value!! + CatDogRepository.getListOfDogUrls(amount)
    }

    private fun addBothUrlstoList(amount: Int) {
        for (i in 0 until amount) {
            if (i % 2 == 0) {
                catDogUrls.value = catDogUrls.value!! + CatDogRepository.getListOfCatUrls(1)
            } else {
                catDogUrls.value = catDogUrls.value!! + CatDogRepository.getListOfDogUrls(1)
            }
        }
    }

    /**
     * Adds urls to the database
     */
    fun saveFavoriteUrl(url: String, position: Int) {
        Log.d("SAVE", "Saving URL")
        Log.d("SAVE", option)
        when (option) {
            "CAT" -> saveCatUrlToDatabase(url)
            "DOG" -> saveDogUrlToDatabase(url)
            "BOTH" -> saveUrlToDatabaseUsingPosition(url, position)
        }
    }

    /**
     * Deletes urls from database
     */
    fun deleteFavoriteUrl(url: String, position: Int) {
        when (option) {
            "CATFAV" -> deleteCatUrlFromDatabase(url)
            "DOGFAV" -> deleteDogUrlFromDatabase(url)
            "BOTHFAV" -> deleteUrlFromDatabaseUsingUrl(url, position)
        }
    }

    private fun saveCatUrlToDatabase(url: String) {
        Log.d("SAVECAT", "Saving CAT URL")
        localDataSource.addCat(url)
    }

    private fun saveDogUrlToDatabase(url: String) {
        localDataSource.addDog(url)
    }

    private fun saveUrlToDatabaseUsingPosition(url: String, position: Int) {
        if (position % 2 == 0) {
            saveCatUrlToDatabase(url)
        } else {
            saveDogUrlToDatabase(url)
        }
    }

    private fun deleteCatUrlFromDatabase(url: String) {
        localDataSource.deleteCat(url)
        getUrls()
    }

    private fun deleteDogUrlFromDatabase(url: String) {
        localDataSource.deleteDog(url)
        getUrls()
    }

    private fun deleteUrlFromDatabaseUsingUrl(url: String, position: Int) {
        if (position % 2 == 0) {
            deleteCatUrlFromDatabase(url)
        } else {
            deleteDogUrlFromDatabase(url)
        }
    }

}