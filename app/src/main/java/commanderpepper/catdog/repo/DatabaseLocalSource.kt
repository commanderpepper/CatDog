package commanderpepper.catdog.repo

import android.content.Context
import android.database.sqlite.SQLiteException
import commanderpepper.catdog.models.AnimalUrl
import commanderpepper.catdog.room.AnimalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class DatabaseLocalSource(val animalDatabase: AnimalDatabase) {

    fun getList(): List<String> {
        var url = listOf<String>()
        runBlocking {
            withContext(Dispatchers.IO) {
                url = animalDatabase.animalDao().getUrls()
            }
        }
        return url
    }

    fun getCatUrls(): List<String> {
        var url = listOf<String>()
        runBlocking {
            withContext(Dispatchers.IO) {
                url = animalDatabase.animalDao().getCatUrls()
            }
        }
        return url
    }

    fun getDogUrls(): List<String> {
        var url = listOf<String>()
        runBlocking {
            withContext(Dispatchers.IO) {
                url = animalDatabase.animalDao().getDogsUrls()
            }
        }
        return url
    }

    fun addCat(url: String) {
        runBlocking {
            withContext(Dispatchers.IO) {
                animalDatabase.animalDao().addUrl(AnimalUrl(url, "CAT"))
            }
        }
    }

    fun addDog(url: String) {
        runBlocking {
            withContext(Dispatchers.IO) {
                animalDatabase.animalDao().addUrl(AnimalUrl(url, "DOG"))
            }
        }
    }

    fun deleteCat(url: String) {
        runBlocking {
            withContext(Dispatchers.IO) {
                animalDatabase.animalDao().deleteAnimal(AnimalUrl(url, "CAT"))
            }
        }
    }

    fun deleteDog(url: String) {
        runBlocking {
            withContext(Dispatchers.IO) {
                animalDatabase.animalDao().deleteAnimal(AnimalUrl(url, "DOG"))
            }
        }
    }

    //This can be done better I think
    fun deleteAnimal(url: String) {
        runBlocking {
            withContext(Dispatchers.IO) {
                try {
                    animalDatabase.animalDao().deleteAnimal(AnimalUrl(url, "CAT"))
                } catch (e: SQLiteException) {
                    animalDatabase.animalDao().deleteAnimal(AnimalUrl(url, "DOG"))
                }
            }
        }
    }

    companion object {
        private var instance: DatabaseLocalSource? = null

        fun getInstance(context: Context): DatabaseLocalSource? {
            if (instance == null) {
                synchronized(DatabaseLocalSource::javaClass) {
                    instance = DatabaseLocalSource(AnimalDatabase.getInstance(context))
                }
            }
            return instance
        }

        fun clearInstance() {
            instance = null
        }

    }
}