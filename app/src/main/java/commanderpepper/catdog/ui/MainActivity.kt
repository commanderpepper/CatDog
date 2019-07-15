package commanderpepper.catdog.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import commanderpepper.catdog.CatDogConstants
import commanderpepper.catdog.R
import commanderpepper.catdog.models.Cat
import commanderpepper.catdog.models.Dog
import commanderpepper.catdog.retrofit.CatService
import commanderpepper.catdog.retrofit.DogService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val catService = CatService.create()
        val dogService = DogService.create()
        var cat: Cat?
        var dog: Dog?

        GlobalScope.launch {
            cat = catService.getCat()
            dog = dogService.getDog()
            Log.d(CatDogConstants.catTag, cat.toString())
            Log.d(CatDogConstants.dogTag, dog.toString())
        }
    }
}
