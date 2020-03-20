package commanderpepper.catdog.ui.recyclerview

import android.R
import androidx.recyclerview.widget.RecyclerView
import commanderpepper.catdog.databinding.CatdogItemBinding
import commanderpepper.catdog.models.Url

class CatDogRViewHolder(private val binding: CatdogItemBinding) :
    RecyclerView.ViewHolder(binding.root) {


    fun setToFav() {
        binding.favImage.setImageDrawable(itemView.resources.getDrawable(R.drawable.btn_star_big_on))
        binding.favImage.tag = "ON"
    }

    fun setToNotFav() {
        binding.favImage.setImageDrawable(itemView.resources.getDrawable(R.drawable.btn_star_big_off))
        binding.favImage.tag = "OFF"
    }

    fun setFavOnClickListener(
        url: Url,
        saveUrl: (url: Url) -> Unit,
        deleteUrl: (url: Url) -> Unit
    ) {
        binding.favImage.setOnClickListener {
            if (binding.favImage.tag == "OFF") {
                saveUrl(url)
                setToFav()
            } else {
                deleteUrl(url)
                setToNotFav()
            }
        }
    }

    fun setFavStatus(checkIfFavorite: (Url) -> Boolean, url: Url) {
        if(checkIfFavorite(url)){
            setToFav()
        }
        else{
            setToNotFav()
        }
    }
}