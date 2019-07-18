package commanderpepper.catdog.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import commanderpepper.catdog.CatDogConstants
import commanderpepper.catdog.R
import commanderpepper.catdog.databinding.ActivityMainBinding
import commanderpepper.catdog.repo.CatDogRepository
import commanderpepper.catdog.viewmodel.CatDogMainViewModel
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var catButton: ImageButton
    private lateinit var dogButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        setContentView(R.layout.activity_main)

        val catDogRepo = CatDogRepository

        catButton = binding.catButton
        dogButton = binding.dogButton

        val catDogMainViewModel = ViewModelProviders.of(this).get(CatDogMainViewModel::class.java)

        runBlocking {
            withContext(Dispatchers.Default) {
                catDogMainViewModel.catUrl = catDogRepo.getCatUrl()
                while (!catDogMainViewModel.catUrl.contains("jpg")) {
                    Log.d(CatDogConstants.catTag, "Not a jpg")
                    Log.d(CatDogConstants.catTag, catDogMainViewModel.catUrl)
                    catDogMainViewModel.catUrl = catDogRepo.getCatUrl()
                }

                catDogMainViewModel.dogUrl = catDogRepo.getDogUrl()
                while (!catDogMainViewModel.dogUrl.contains("jpg")) {
                    Log.d(CatDogConstants.dogTag, "Not a jpg")
                    Log.d(CatDogConstants.dogTag, catDogMainViewModel.dogUrl)
                    catDogMainViewModel.dogUrl = catDogRepo.getDogUrl()
                }
                Log.d(CatDogConstants.catTag, catDogMainViewModel.catUrl)
                Log.d(CatDogConstants.dogTag, catDogMainViewModel.dogUrl)
            }
        }

        Glide.with(this@MainActivity)
            .load(catDogMainViewModel.catUrl)
            .into(catButton)

        Glide.with(this@MainActivity)
            .load(catDogMainViewModel.dogUrl)
            .into(dogButton)

    }
}
