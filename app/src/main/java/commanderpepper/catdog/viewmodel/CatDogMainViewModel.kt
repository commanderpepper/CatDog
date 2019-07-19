package commanderpepper.catdog.viewmodel

import android.widget.ImageButton
import androidx.lifecycle.ViewModel
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.Glide
import androidx.databinding.BindingAdapter


class CatDogMainViewModel : ViewModel() {
    var dogUrl: String = ""
    var catUrl: String = ""
}