package commanderpepper.catdogtwo.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import commanderpepper.catdogtwo.models.AnimalUrl

@Database(entities = [AnimalUrl::class], version = 1)
abstract class AnimalDatabase : RoomDatabase() {
    abstract fun animalDao(): AnimalDao

    companion object {
        @Volatile
        private var instance: AnimalDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): AnimalDatabase {
            synchronized(lock) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AnimalDatabase::class.java,
                        "animal-db"
                    )
                        .build()
                }
            }
            return instance!!
        }

    }
}