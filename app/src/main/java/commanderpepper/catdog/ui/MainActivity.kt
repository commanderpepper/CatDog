package commanderpepper.catdog.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import com.squareup.picasso.Picasso
import commanderpepper.catdog.CatDogConstants
import commanderpepper.catdog.R
import commanderpepper.catdog.models.Cat
import commanderpepper.catdog.models.Dog
import commanderpepper.catdog.repo.CatDogRepository
import commanderpepper.catdog.retrofit.CatService
import commanderpepper.catdog.retrofit.DogService
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var catButton: ImageView
    private lateinit var dogButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val catDogRepo = CatDogRepository

        catButton = findViewById(R.id.testView)
//        dogButton = findViewById(R.id.dogButton)


        runBlocking {
            withContext(Dispatchers.Default) {
                var url = catDogRepo.getCatUrl()
                while (!url.contains("jpg")) {
                    url = catDogRepo.getCatUrl()
                    Log.d(CatDogConstants.catTag, "Not a jpg")
                    Log.d(CatDogConstants.catTag, url)
                }
                Log.d(CatDogConstants.catTag, url)
                withContext(Dispatchers.Main) {
                    Picasso.get().apply {
                        this.isLoggingEnabled = true
                    }
                        .load(url)
                        .into(catButton)
                }
            }
        }
    }
}
