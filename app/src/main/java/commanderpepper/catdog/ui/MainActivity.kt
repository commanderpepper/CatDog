package commanderpepper.catdog.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import com.bumptech.glide.Glide
import commanderpepper.catdog.CatDogConstants
import commanderpepper.catdog.R
import commanderpepper.catdog.repo.CatDogRepository
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var catButton: ImageButton
    private lateinit var dogButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val catDogRepo = CatDogRepository

        catButton = findViewById(R.id.catButton)
        dogButton = findViewById(R.id.dogButton)

        var catUrl = ""
        var dogUrl = ""

        runBlocking {
            withContext(Dispatchers.Default) {
                catUrl = catDogRepo.getCatUrl()
                while (!catUrl.contains("jpg")) {
                    Log.d(CatDogConstants.catTag, "Not a jpg")
                    Log.d(CatDogConstants.catTag, catUrl)
                    catUrl = catDogRepo.getCatUrl()
                }

                dogUrl = catDogRepo.getDogUrl()
                while (!dogUrl.contains("jpg")) {
                    Log.d(CatDogConstants.dogTag, "Not a jpg")
                    Log.d(CatDogConstants.dogTag, dogUrl)
                    dogUrl = catDogRepo.getDogUrl()
                }
                Log.d(CatDogConstants.catTag, catUrl)
                Log.d(CatDogConstants.dogTag, dogUrl)
            }
        }
        Glide.with(this@MainActivity)
            .load(catUrl)
            .into(catButton)

        Glide.with(this@MainActivity)
            .load(dogUrl)
            .into(dogButton)

    }
}
