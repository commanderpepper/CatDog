package commanderpepper.catdog.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import commanderpepper.catdog.Choice
import commanderpepper.catdog.R
import commanderpepper.catdog.databinding.MainFragmentBinding
import commanderpepper.catdog.viewmodel.CatDogMainViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect


class CatDogMainFragment : Fragment() {

    lateinit var catDogMainViewModel: CatDogMainViewModel
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
            catDogMainViewModel.catFlow
                .catch {
                    loadErrorImage(catButton)
                    catButton.isActivated = false
                    catButton.isEnabled = false
                }
                .collect { cat ->
                    loadImageIntoGlide(cat.file, catButton)
                }
        }

        lifecycleScope.launch {
            catDogMainViewModel.dogFlow
                .catch {
                    loadErrorImage(dogButton)
                    dogButton.isActivated = false
                    dogButton.isEnabled = false
                }
                .collect { dog ->
                    loadImageIntoGlide(dog.url, dogButton)
                }
        }

//        lifecycleScope.launch {
//            catDogMainViewModel.getCatFlow()
//                .catch {
//                    Log.d("Flow", "Something went wrong")
//                    loadErrorImage(catButton)
//                    catButton.isActivated = false
//                    catButton.isEnabled = false
//                }
//                .collect { cat ->
//                    loadImageIntoGlide(cat.file, catButton)
//                }
//        }

//        catDogMainViewModel.catUrl = catDogMainViewModel.getUseableCatUrl()
//        catDogMainViewModel.dogUrl = catDogMainViewModel.getUseableDogUrl()

//        if (catDogMainViewModel.catUrl == "error") {
//            loadErrorImage(catButton)
//            catButton.isActivated = false
//            catButton.isEnabled = false
//        } else {
//            loadImageIntoGlide(catDogMainViewModel.catUrl, catButton)
//        }
//
//        if (catDogMainViewModel.dogUrl == "error") {
//            loadErrorImage(dogButton)
//            dogButton.isActivated = false
//            dogButton.isEnabled = false
//        } else {
//            if (catDogMainViewModel.dogUrl.contains("jpg|png".toRegex())) {
//                loadImageIntoGlide(catDogMainViewModel.dogUrl, dogButton)
//            } else {
//                loadGifIntoGlide(catDogMainViewModel.dogUrl, dogButton)
//            }
//        }

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

    override fun onStart() {
        super.onStart()
//        scope.cancel()
    }
}