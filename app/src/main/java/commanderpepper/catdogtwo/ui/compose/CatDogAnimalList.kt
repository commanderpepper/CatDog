package commanderpepper.catdogtwo.ui.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import commanderpepper.catdogtwo.models.Cat
import commanderpepper.catdogtwo.models.Dog

@Composable
fun CatDogAnimalList(modifier: Modifier = Modifier, cats: List<CatListItem>, onClick: (Cat) -> Unit) {
    LazyColumn(modifier = modifier) {
        items(cats){ catItem ->
            CatDogAnimal(cat = catItem.cat, isFavorite = catItem.isFavorite, onClick = onClick)
        }
    }
}

@Composable
fun CatDogAnimalList(modifier: Modifier = Modifier, dogs: List<DogListItem>, onClick: (Dog) -> Unit) {
    LazyColumn(modifier = modifier) {
        items(dogs){ dogItem ->
            CatDogAnimal(dog = dogItem.dog, isFavorite = dogItem.isFavorite, onClick = onClick)
        }
    }
}

@Composable
fun CatDogAnimalListPreview(){
    CatDogAnimalList(cats = emptyList()){

    }
}

data class CatListItem(val cat: Cat, val isFavorite: Boolean)

data class DogListItem(val dog: Dog, val isFavorite: Boolean)