package commanderpepper.catdogtwo.ui

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
import commanderpepper.catdogtwo.R
import commanderpepper.catdogtwo.databinding.CatdoglistFragmentBinding
import commanderpepper.catdogtwo.models.Option
import commanderpepper.catdogtwo.models.UrlAnimal
import commanderpepper.catdogtwo.models.isFav
import commanderpepper.catdogtwo.models.toOption
import commanderpepper.catdogtwo.repo.CatDogRepository
import commanderpepper.catdogtwo.ui.recyclerview.CatDogRAdapter
import commanderpepper.catdogtwo.viewmodel.CatDogListFragmentViewModel
import kotlinx.android.synthetic.main.catdoglist_fragment.view.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

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

        if (listViewModel.optionSC.isFav()) {
            lifecycleScope.launch {
                val hasFavs = listViewModel.checkForFavorites(optionSC)
                if (hasFavs) {
                    binding.favsText.visibility = View.GONE
                    binding.listScrollView.visibility = View.VISIBLE
                }
            }
        } else {
            binding.favsText.visibility = View.GONE
            binding.listScrollView.visibility = View.VISIBLE
        }

        /**
         * Functions to pass to the recycler view adapter
         */
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

        recyclerView = binding.root.catDogList.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        lifecycleScope.launch {
            listViewModel.getUrlAnimals()
        }

        /**
         * Every time an item is sent to the flow, add the item to the view adapter list and notify the adapter.
         */
        flow.onEach {
            viewAdapter.addUrl(it)
            viewAdapter.notifyItemRangeInserted(viewAdapter.itemCount, 1)
        }.launchIn(lifecycleScope)



        /**
         * Adds a listener that activates when the user is at the end of the RecyclerView
         * Allows for endless scrolling when choosing random animals
         */
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && !optionSC.isFav()) {
                    lifecycleScope.launch {
                        listViewModel.getUrlAnimals()
                    }
                }
            }
        })

        return binding.root
    }

    /**
     * Informs the view model that the fragment is detaching. Useful to set info in the view model to help with configuration changes.
     */
    override fun onDetach() {
        super.onDetach()
        listViewModel.fragmentDetach()
    }
}
