package com.dinadurykina.skylexicon.ui.share

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.dinadurykina.skylexicon.R
// Окошко чтобы показать увеличенное изображение и назад
// AlertDialog Kotlin URI
// //d2zkmv5t5kao9.cloudfront.net/images/b905a618b56c721ce683164259ac02c4.jpeg?w=640&h=480
class DialogImageFragment : DialogFragment() {
    private val args: DialogImageFragmentArgs by navArgs()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val imageUri = ImageView(requireActivity())
        imageUri.adjustViewBounds = true

        args.uri?.let {
            val imgUri = it.toUri().buildUpon().scheme("https").build()
            Glide.with(requireActivity())
                .load(imgUri)
                // Добавьте простые изображения загрузки и ошибок
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
                .into(imageUri)
        }
        imageUri.setOnClickListener{ dialog?.dismiss() }
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(imageUri)
        return builder.create()
    }
}
