package commanderpepper.catdogtwo.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import commanderpepper.catdogtwo.Choice
import commanderpepper.catdogtwo.R
import commanderpepper.catdogtwo.databinding.MainFragmentBinding
import commanderpepper.catdogtwo.viewmodel.CatDogMainViewModel
import kotlinx.coroutines.*


class CatDogMainFragment : Fragment() {

    private lateinit var catDogMainViewModel: CatDogMainViewModel
    private lateinit var catButton: ImageButton
    private lateinit var dogButton: ImageButton
    private lateinit var bothButton: Button

    /**
     * Instantiates a view model to persist the starting dog and cat images
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catDogMainViewModel = ViewModelProviders.of(this).get(CatDogMainViewModel::class.java)
    }

    /**
     * Creates reference to buttons and sets urls for the dog and cat button images
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: MainFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)

        catButton = binding.catButton
        dogButton = binding.dogButton
        bothButton = binding.bothButton

        lifecycleScope.launch {
            val catUrl = catDogMainViewModel.getUseableCatUrl()
            if (catUrl != "error") {
                loadImageIntoGlide(catUrl, catButton)
            } else {
                loadErrorImage(catButton)
                disableButton(catButton)
                disableButton(bothButton)
            }
        }

        lifecycleScope.launch {
            val dogUrl = catDogMainViewModel.getUseableDogUrl()
            if (dogUrl != "error") {
                if (dogUrl.contains("gif")) {
                    loadGifIntoGlide(dogUrl, dogButton)
                } else {
                    loadImageIntoGlide(dogUrl, dogButton)
                }
            } else {
                loadErrorImage(dogButton)
                disableButton(dogButton)
                disableButton(bothButton)
            }
        }

        val intent = Intent(context, ListActivity::class.java)

        catButton.setOnClickListener {
            startListActivity(intent, Choice.CAT.toString())
        }

        dogButton.setOnClickListener {
            startListActivity(intent, Choice.DOG.toString())
        }

        bothButton.setOnClickListener {
            startListActivity(intent, Choice.BOTH.toString())
        }

        return binding.mainLayout
    }

    private fun disableButton(view: View) {
        view.isActivated = false
        view.isEnabled = false
    }

    private fun loadErrorImage(imageView: ImageView) {
        Glide.with(this)
            .load(R.drawable.loading_error)
            .into(imageView)
    }

    private fun startListActivity(intent: Intent, option: String) {
        val bundle = Bundle().apply {
            this.putString("Option", option)
        }
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun loadImageIntoGlide(url: String, imageView: ImageView) {
        Glide.with(this)
            .load(url)
            .into(imageView)
    }

    private fun loadGifIntoGlide(url: String, imageView: ImageView) {
        Glide.with(this)
            .asGif()
            .load(url)
            .into(imageView)
    }

}