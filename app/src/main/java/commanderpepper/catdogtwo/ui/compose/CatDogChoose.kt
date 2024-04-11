package commanderpepper.catdogtwo.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import commanderpepper.catdogtwo.models.Cat
import commanderpepper.catdogtwo.models.Dog

@Composable
fun CatDogChoose(
    modifier: Modifier = Modifier,
    cat: Cat,
    dog: Dog,
    onCatClick: () -> Unit,
    onDogClick: () -> Unit,
    onBothClick: () -> Unit
) {
    Box(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                modifier = Modifier.weight(.5f).clickable { onCatClick() },
                model = cat.file,
                contentDescription = "A cat"
            )
            AsyncImage(
                modifier = Modifier.weight(.5f).clickable { onDogClick() },
                model = dog.url,
                contentDescription = "A dog"
            )
        }
        TextButton(
            modifier = Modifier.align(alignment = Alignment.Center),
            onClick = { onBothClick() }) {
            Text(text = "BOTH?")
        }
    }
}

@Composable
fun CatDogChoosePreview() {
    CatDogChoose(cat = Cat(""), dog = Dog(""), onCatClick = {}, onDogClick = {}, onBothClick = {})
}