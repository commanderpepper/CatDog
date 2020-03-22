package commanderpepper.catdog.ui.recyclerview

import android.R
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import commanderpepper.catdog.databinding.CatdogItemBinding
import commanderpepper.catdog.models.UrlAnimal

class CatDogRViewHolder(private val binding: CatdogItemBinding) :
    RecyclerView.ViewHolder(binding.root) {


    private fun setToFav() {
        binding.favImage.setImageDrawable(itemView.resources.getDrawable(R.drawable.btn_star_big_on))
        binding.favImage.tag = "ON"
    }

    private fun setToNotFav() {
        binding.favImage.setImageDrawable(itemView.resources.getDrawable(R.drawable.btn_star_big_off))
        binding.favImage.tag = "OFF"
    }

    fun bindUrlAnimal(url: UrlAnimal) {
        when (url) {
            is UrlAnimal.UrlCat -> bindCat(url.cat.file)
            is UrlAnimal.UrlDog -> bindDog(url.dog.url)
        }
    }

    fun bindCat(url: String) {
        Glide.with(binding.root)
            .load(url)
            .into(binding.catdogImage)
    }

    fun bindDog(url: String) {
        if (url.contains("gif".toRegex())) {
            Glide.with(binding.root)
                .asGif()
                .load(url)
                .into(binding.catdogImage)
        } else {
            Glide.with(binding.root)
                .load(url)
                .into(binding.catdogImage)
        }
    }

    fun setFavOnClickListener(
        url: UrlAnimal,
        saveUrl: (url: UrlAnimal) -> Unit,
        deleteUrl: (url: UrlAnimal) -> Unit
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

    fun setFavStatus(checkIfFavorite: (UrlAnimal) -> Boolean, url: UrlAnimal) {
        if (checkIfFavorite(url)) {
            setToFav()
        } else {
            setToNotFav()
        }
    }
}