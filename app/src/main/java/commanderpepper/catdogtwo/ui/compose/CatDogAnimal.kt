package commanderpepper.catdogtwo.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import commanderpepper.catdogtwo.models.Cat
import commanderpepper.catdogtwo.models.Dog

@Composable
fun CatDogAnimal(cat: Cat, isFavorite: Boolean, onClick: (Cat) -> Unit) {
    Box(modifier = Modifier.clickable { onClick(cat) }) {
        AsyncImage(model = cat.file, contentDescription = "A cat")
        Image(
            modifier = Modifier.align(alignment = Alignment.TopStart),
            painter = if (isFavorite) painterResource(id = android.R.drawable.btn_star_big_on) else painterResource(
                id = android.R.drawable.btn_star_big_off
            ), contentDescription = "Is cat favorited"
        )
    }
}

@Composable
fun CatDogAnimal(dog: Dog, isFavorite: Boolean, onClick: (Dog) -> Unit) {
    Box {
        AsyncImage(model = dog.url, contentDescription = "A dog")
        Image(
            modifier = Modifier.align(alignment = Alignment.TopStart),
            painter = if (isFavorite) painterResource(id = android.R.drawable.btn_star_big_on) else painterResource(
                id = android.R.drawable.btn_star_big_off
            ), contentDescription = "Is dog favorited"
        )
    }
}

@Preview
@Composable
fun CatDogAnimalPreviews(){
    Column {
        CatDogAnimal(cat = Cat("https://i.imgur.com/94fhCnt.png"), isFavorite = false){ }
        CatDogAnimal(cat = Cat("https://i.imgur.com/94fhCnt.png"), isFavorite = true){ }
        CatDogAnimal(cat = Cat(""), isFavorite = true){ }

    }
}