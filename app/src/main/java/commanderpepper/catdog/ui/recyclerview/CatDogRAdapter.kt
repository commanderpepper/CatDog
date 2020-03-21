package commanderpepper.catdog.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import commanderpepper.catdog.R
import commanderpepper.catdog.models.UrlAnimal

class CatDogRAdapter(
    private val urls: List<UrlAnimal>,
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

    override fun getItemCount(): Int {
        return urls.size
    }

    override fun onBindViewHolder(holder: CatDogRViewHolder, position: Int) {
        holder.setFavStatus(checkIfFavorite, urls[position])
        holder.setFavOnClickListener(
            urls[position],
            saveFavUrl,
            removeDavUrl
        )
    }

}