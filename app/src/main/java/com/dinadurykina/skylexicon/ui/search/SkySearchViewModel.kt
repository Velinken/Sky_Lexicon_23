/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.dinadurykina.skylexicon.ui.search

import androidx.lifecycle.*
import com.dinadurykina.skylexicon.network.WordRecycler
import com.dinadurykina.skylexicon.repository.SkyRepository
import com.dinadurykina.skylexicon.ui.Event
import com.dinadurykina.skylexicon.ui.about.SkyHistory
import com.dinadurykina.skylexicon.ui.playSound
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [SkySearchFragment].
 */

class SkySearchViewModel(
    val skyRepository : SkyRepository,
    slovo:String,
    val skyHistory: SkyHistory
    ) : ViewModel() {
    // Вводимое слово связано двухсторонним биндингом с полем
    // наблюдается из фрагмента и при изменении зовется поиск
    val slovo: MutableLiveData<String> = MutableLiveData<String>(slovo) //("Chair")



   // Для Json нерасшифрованного (отладка)
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    // Основной список для RecyclerView
    private val _wordsListRecycler = MutableLiveData<List<WordRecycler>>()
        val wordsListRecycler: LiveData<List<WordRecycler>>
            get() = _wordsListRecycler

    // Для перехода ко второму экрану
    private val _navigateToSkyMeanings = MutableLiveData<Event<String?>>()
    val navigateToSkyMeanings: LiveData<Event<String?>>
        get() = _navigateToSkyMeanings

    private val _showImage = MutableLiveData<Event<String?>>()
    val showImage : LiveData<Event<String?>>
        get() = _showImage

    // Для перехода ко второму экрану
    fun onSkySearchClicked(id:String) {
        _navigateToSkyMeanings.value = Event(id)
    }

    fun searchSlovo(slovo:String) {
        viewModelScope.launch {
            try {
                _wordsListRecycler.value = skyRepository.getSkySearchRecycler(slovo).value
               // _wordsListRecycler = skyRepository.getSkySearchRecycler(slovo)
                _response.value = "good"
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }
    // https://stackoverflow.com/questions/51648366/how-to-call-edittext-oneditoraction-from-xml-using-data-binding
    fun onSearchGo(slovo:String) {
        viewModelScope.launch {
            try {
                val historyWord = skyRepository.getSkySearch0(slovo)
                skyHistory.addHistoryWord(historyWord!!)
                _response.value = "good"
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }

    }

    fun onSkyImageClicked(imageUri:String) {
        _showImage.value = Event(imageUri)
    }

    fun onClickSound(imageUrl:String) = playSound(imageUrl)
}

@Suppress("UNCHECKED_CAST")
class SkySearchViewModelFactory (
    private val skyRepository: SkyRepository,
    private val slovo: String,
    private val skyHistory: SkyHistory
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (SkySearchViewModel(skyRepository,slovo,skyHistory) as T)
}

