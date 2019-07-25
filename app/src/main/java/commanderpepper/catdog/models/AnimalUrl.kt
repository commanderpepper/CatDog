package commanderpepper.catdog.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animal")
data class AnimalUrl(
    @PrimaryKey
    val url: String,
    @ColumnInfo(name = "animalType")
    val animal: String
)