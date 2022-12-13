package com.dinadurykina.skylexicon.ui

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.recyclerview.widget.DiffUtil
import timber.log.Timber

//val http = "https://firebasestorage.googleapis.com/v0/b/nmixer-97a91.appspot.com/o/musics%2FDark%20World.mp3?alt=media&token=f8564ca6-cf59-468d-bd98-13ff646a1752"
//val http = "https://d2fmfepycn0xw0.cloudfront.net/?gender=male&accent=british&text=chair"
fun playSound(soundUrl: String?) {
    val mediaPlayer = MediaPlayer()
    mediaPlayer.setDataSource(soundUrl)
    // необязательно:
    mediaPlayer.setAudioAttributes(
        AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
    )
    Timber.i("MediaPlayer ON: $soundUrl ")
    mediaPlayer.prepare()
    mediaPlayer.start()
}

// Спросить в Инете можно ли так и, если нельзя, то почему
// diffCallback адаптер не перерисовывает не изменившиеся элементы
@SuppressLint("DiffUtilEquals")
class DiffCallback<D> : DiffUtil.ItemCallback<D>() {
    override fun areItemsTheSame(oldItem: D & Any, newItem: D & Any): Boolean =
             oldItem.toString() == newItem.toString()
    override fun areContentsTheSame(oldItem: D & Any, newItem: D & Any): Boolean =
             oldItem.hashCode() == newItem.hashCode()
    }

