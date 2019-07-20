package commanderpepper.catdog.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import commanderpepper.catdog.CatDogConstants
import commanderpepper.catdog.R
import commanderpepper.catdog.databinding.MainFragmentBinding
import commanderpepper.catdog.repo.CatDogRepository
import commanderpepper.catdog.viewmodel.CatDogMainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CatDogMainFragment : Fragment() {

    lateinit var catDogMainViewModel: CatDogMainViewModel
    private lateinit var catButton: ImageButton
    private lateinit var dogButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catDogMainViewModel = ViewModelProviders.of(this).get(CatDogMainViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: MainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)

        catButton = binding.catButton
        dogButton = binding.dogButton

        val catDogRepository = CatDogRepository

        // If the string is empty then get some urls
        if (catDogMainViewModel.catUrl == "") {
            runBlocking {
                withContext(Dispatchers.Default) {
                    catDogMainViewModel.catUrl = catDogRepository.getCatUrl()

                    while (!catDogMainViewModel.catUrl.contains("jpg|png".toRegex())) {
                        Log.d(CatDogConstants.catTag, catDogMainViewModel.catUrl)
                        catDogMainViewModel.catUrl = catDogRepository.getCatUrl()
                    }

                    catDogMainViewModel.dogUrl = catDogRepository.getDogUrl()
                    while (!catDogMainViewModel.dogUrl.contains("jpg|png|gif".toRegex())) {
                        Log.d(CatDogConstants.dogTag, catDogMainViewModel.dogUrl)
                        catDogMainViewModel.dogUrl = catDogRepository.getDogUrl()
                    }
                }
            }
        }

        Glide.with(this)
            .load(catDogMainViewModel.catUrl)
            .into(catButton)

        if (catDogMainViewModel.dogUrl.contains("jpg|png".toRegex())) {
            Glide.with(this)
                .load(catDogMainViewModel.dogUrl)
                .into(dogButton)
        } else {
            Glide.with(this)
                .asGif()
                .load(catDogMainViewModel.dogUrl)
                .into(dogButton)
        }

        return binding.root
    }
}