package edu.msudenver.cs3013.project03

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import edu.msudenver.cs3013.project03.ImageLoader

class GlideImageLoader(private val context: Context): ImageLoader {
    override fun loadImage(imageUrl: String, imageView: ImageView) {
        Glide.with(context).load(imageUrl).centerCrop().into(imageView)
    }
}