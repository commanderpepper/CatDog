package commanderpepper.catdog.room

import androidx.room.*
import commanderpepper.catdog.models.AnimalUrl

/**
 * Animal Data Access Object
 */

@Dao
interface AnimalDao {
    @Query("SELECT url FROM animal WHERE animalType = 'DOG'")
    suspend fun getDogsUrls(): List<String>

    @Query("SELECT url FROM animal WHERE animalType = 'CAT'")
    suspend fun getCatUrls(): List<String>

    @Query("SELECT url FROM animal")
    suspend fun getUrls(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUrl(animalUrl: AnimalUrl): Long

    @Delete
    suspend fun deleteAnimal(animalUrl: AnimalUrl)

    @Query("DELETE FROM animal WHERE url = :url")
    suspend fun deleteRowUsingUrl(url: String)

    @Query("DELETE FROM animal")
    fun clearTableForTesting()

    @Query("SELECT COUNT(*) from animal WHERE url = :url")
    suspend fun checkForAnimalUrl(url: String): Int

}

