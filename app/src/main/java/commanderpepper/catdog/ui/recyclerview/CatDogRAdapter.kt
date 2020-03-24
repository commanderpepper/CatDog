package commanderpepper.catdog.ui.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import commanderpepper.catdog.R
import commanderpepper.catdog.models.UrlAnimal

class CatDogRAdapter(
    private val urls: MutableList<UrlAnimal>,
    private val saveFavUrl: (UrlAnimal) -> (Unit),
    private val removeDavUrl: (UrlAnimal) -> (Unit),
    private val checkIfFavorite: (UrlAnimal) -> Boolean
) :
    RecyclerView.Adapter<CatDogRViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatDogRViewHolder {
        return CatDogRViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.catdog_item,
                parent,
                false
            )
        )
    }

    fun addUrl(url: UrlAnimal) {
        urls.add(url)
    }

    override fun getItemCount(): Int {
        return urls.size
    }

    override fun onBindViewHolder(holder: CatDogRViewHolder, position: Int) {
        Log.d("AnimalURL", urls[position].toString())
        holder.bindUrlAnimal(urls[position])
        holder.setFavStatus(checkIfFavorite, urls[position])
        holder.setFavOnClickListener(
            urls[position],
            saveFavUrl,
            removeDavUrl
        )
    }

}