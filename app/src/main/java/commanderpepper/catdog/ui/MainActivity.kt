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
import kotlinx.android.synthetic.main.main_fragment.view.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set Content View
        DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
    }
}
