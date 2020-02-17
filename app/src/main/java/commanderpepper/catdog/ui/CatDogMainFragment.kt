package commanderpepper.catdog.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import commanderpepper.catdog.Choice
import commanderpepper.catdog.R
import commanderpepper.catdog.databinding.MainFragmentBinding
import commanderpepper.catdog.viewmodel.CatDogMainViewModel

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

        catDogMainViewModel.catUrl = catDogMainViewModel.getUseableCatUrl()
        catDogMainViewModel.dogUrl = catDogMainViewModel.getUseableDogUrl()

        if (catDogMainViewModel.catUrl == "error") {
            Glide.with(this)
                .load(R.drawable.loading_error)
                .into(catButton)

            catButton.isActivated = false
            catButton.isEnabled = false
        } else {
            Glide.with(this)
                .load(catDogMainViewModel.catUrl)
                .into(catButton)
        }

        if (catDogMainViewModel.dogUrl == "error") {
            Glide.with(this)
                .load(R.drawable.loading_error)
                .into(dogButton)

            dogButton.isActivated = false
            dogButton.isEnabled = false
        } else {
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
        }

        val intent = Intent(context, ListActivity::class.java)

        catButton.setOnClickListener {
            val bundle = Bundle().apply {
                this.putString("Option", Choice.CAT.toString())
            }
            intent.putExtras(bundle)
            startActivity(intent)
        }

        dogButton.setOnClickListener {
            val bundle = Bundle().apply {
                this.putString("Option", Choice.DOG.toString())
            }
            intent.putExtras(bundle)
            startActivity(intent)
        }

        bothButton.setOnClickListener {
            val bundle = Bundle().apply {
                this.putString("Option", Choice.BOTH.toString())
            }
            intent.putExtras(bundle)
            startActivity(intent)
        }

        return binding.root
    }
}