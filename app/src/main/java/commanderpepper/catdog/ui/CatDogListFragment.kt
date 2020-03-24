package commanderpepper.catdog.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import commanderpepper.catdog.R
import commanderpepper.catdog.databinding.CatdoglistFragmentBinding
import commanderpepper.catdog.models.Option
import commanderpepper.catdog.models.UrlAnimal
import commanderpepper.catdog.models.toOption
import commanderpepper.catdog.repo.CatDogRepository
import commanderpepper.catdog.ui.recyclerview.CatDogRAdapter
import commanderpepper.catdog.viewmodel.CatDogListFragmentViewModel
import kotlinx.android.synthetic.main.catdoglist_fragment.view.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatDogListFragment : Fragment() {

    private lateinit var option: String
    private lateinit var optionSC: Option
    private lateinit var listViewModel: CatDogListFragmentViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: CatDogRAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    /**
     * Takes in the option from the user.
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        option = activity?.intent?.extras?.getString("Option") ?: "Where's the data?"
        optionSC = option.toOption()
        Log.d("ListFragment", option)
    }

    /**
     * Creates a View Model which will help with persisting data and abstracting calls to Retrofit and Room
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listViewModel = ViewModelProviders.of(this).get(CatDogListFragmentViewModel::class.java)
        listViewModel.optionSC = optionSC
        lifecycleScope.launch {
            listViewModel.getUrlAnimals()
        }
//        listViewModel.getUrls()
    }

    /**
     * Creates a view model and an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: CatdoglistFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.catdoglist_fragment, container, false)

        viewManager = LinearLayoutManager(context)
//        viewAdapter = CatDogAdapter(option, listViewModel)

        val saveUrlAnimal: (UrlAnimal) -> Unit = CatDogRepository::saveUrl
        val deleteUrlAnimal: (UrlAnimal) -> Unit = CatDogRepository::deleteUrl
        val checkForFavorite: (UrlAnimal) -> Boolean = CatDogRepository::checkIfFavorite
        
        val flow = listViewModel.getFlowOfUrlAnimals()

        viewAdapter = CatDogRAdapter(
            listViewModel.getListForAdapter(),
            saveFavUrl = saveUrlAnimal,
            removeDavUrl = deleteUrlAnimal,
            checkIfFavorite = checkForFavorite
        )

        flow.onEach {
            viewAdapter.addUrl(it)
        }.launchIn(lifecycleScope)

        recyclerView = binding.root.catDogList.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        // Whenever a change is made the MutableLiveData list, the list inside the view adapter is informed
//        listViewModel.catDogUrls.observe(viewLifecycleOwner, Observer {
//            viewAdapter.submitList(it)
//        })
        /**
         * Checks to see if the list from favs is empty. If so, then display the imageView instead.
         */
//        if ((listViewModel.catDogUrls.value?.isEmpty() ?: false)) {
//            listViewModel.getUrls()
//            binding.listScrollView.visibility = View.GONE
//            binding.favsText.visibility = View.VISIBLE
//        } else {
//            recyclerView = binding.root.catDogList.apply {
//                layoutManager = viewManager
//                adapter = viewAdapter
//            }

        /**
         * Adds a listener that activates when the user is at the end of the RecyclerView
         * Allows for endless scrolling when choosing random animals
         */
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    lifecycleScope.launch {
                        listViewModel.getUrlAnimals(2)
                    }
                }
            }
        })
        return binding.root
    }

    override fun onDetach() {
        super.onDetach()
        listViewModel.fragmentDetach()
    }
}
