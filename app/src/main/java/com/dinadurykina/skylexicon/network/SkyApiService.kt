/*
 * Copyright 2018, The Android Open Source Project
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
// https://classroom.udacity.com/courses/ud9012/lessons/2be0ed85-721d-4a8d-a484-909b5c98336c/concepts/b36af08e-1bce-48e8-9a31-2e268907d2f0
package com.dinadurykina.skylexicon.network
// Это файл для хранения сетевого уровня API, который будет использовать  ViewModel представления

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// В app/build.gradle добавление зависимостей библиотеки Retrofit.

// SkyApiService Класс содержит слой сети для приложения; то есть это API,
// который вы ViewModel будете использовать для связи с веб-сервисом.
// Это класс, в котором вы будете реализовывать API службы Retrofit.
// Мы предоставили переменную для корневого веб-адреса конечной точки сервера SkyEng:

private const val BASE_URL = "https://dictionary.skyeng.ru/"

// используйте Moshi Builder для создания объекта Moshi с KotlinJsonAdapterFactory:
// преобразует Json с полями в объекты Kotlin data class c полями объектов
private val moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()

//  Используйте Retrofit.Builder для создания объекта Retrofit.
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

// Создайте SkyApiService интерфейс и определите getProperties() метод для запроса строки ответа JSON.
// Аннотируйте метод с помощью @GET, указав конечную точку для ответа по skyeng JSON,
// и создайте Call объект Retrofit , который запустит HTTP-запрос.
// В Основном через него приложение и обращается через API к интернету
// а он возращает ответ через фабрику конверторов
// Это аналогично интерфейсу DAO для ROOM типа концептуально того же
interface SkyApiService {

    @GET("api/public/v1/words/search")
    suspend fun getSearch(@Query("search") type: String): List<Word>
// Если вверху добавили фабрику из пакета корутин RetroFit то теперь можно здесь убрать обратный вызов и
// естественно переделывать вызов интерфейса SkyApiService из ViewModel на "корутинный"

    @GET("api/public/v1/meanings")
   suspend fun getMeanings(@Query("ids") type: String): List<Meaning>
}

// Передав API сервиса, который только что определили,
// создайте публичный объект, который называется SkyApi
// для предоставления Retrofit сервиса остальной части приложения:
object SkyApi {
    val retrofitService : SkyApiService by lazy { retrofit.create(SkyApiService::class.java) }
    // Метод Retrofit create()создает сервис Retrofit с SkyApiService интерфейсом
}
// Так здесь создали объект SkyApi для обращения от программы и запроса функций
// Внутри этого объекта встроены: (RetroFit  + (URL-> Конверторы)) + Интерфейс

// Здесь только создан мехпнизм для обращения но использование его xthtp SkyRepository bp ViewModel
// В SkyRepository.kt OverviewViewModel.kt, используйте, SkyApi.retrofitService
// чтобы поставить в очередь запрос Retrofit getSearch(),
// переопределяя требуемые обратные вызовы Retrofit,
// чтобы назначить ответ JSON или сообщение об ошибке значению _response LiveData.
// Убедитесь в том , чтобы импортировать ДООСНАСТКУ версии Callback, Call и Response.


