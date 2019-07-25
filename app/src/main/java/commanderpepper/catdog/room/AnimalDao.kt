package commanderpepper.catdog.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import commanderpepper.catdog.models.AnimalUrl

/**
 * Animal Data Access Object
 */

@Dao
interface AnimalDao {
    @Query("SELECT url FROM animal WHERE animalType = 'DOG'")
    fun getDogsUrls(): List<String>

    @Query("SELECT url FROM animal WHERE animalType = 'CAT'")
    fun getCatUrls(): List<String>

    @Query("SELECT url FROM animal")
    fun getUrls(): List<String>

    @Insert
    fun addUrl(animalUrl: AnimalUrl): Long

    @Delete
    fun deleteUrl(animalUrl: AnimalUrl)
}

