package com.dinadurykina.skylexicon.ui
// https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
// этот класс имеет смысл разместить в подпакет util или utils
// build.gradle(Module): dependencies.implementation 'androidx.fragment:fragment-ktx:1.3.5'
// Дальнейшее развитие со штатной lifecycle:lifecycle-runtime-ktx:2.4.0-alpha01
// https://proandroiddev.com/android-singleliveevent-redux-with-kotlin-flow-b755c70bb055
// https://medium.com/androiddevelopers/a-safer-way-to-collect-flows-from-android-uis-23080b1f8bda
// https://gist.github.com/JoseAlcerreca/e0bba240d9b3cffa258777f12e5c0ae9
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 * Используется в качестве оболочки для данных, которые отображаются через живые данные, представляющие событие.
 * * Что такое событие?
 * В приложении  вы используете настраиваемый Event класс
 *  для LiveData представления одноразовых событий (таких как навигация или всплывающая закусочная).
 *  Event LiveData Наблюдается в Fragment:
 *  open class Event<out T>(private val content: T? = null) {
 * @Suppress("MemberVisibilityCanBePrivate")
 * var hasBeenHandled = false
 *      private set
 * //  Returns the content and prevents its use again.
 * fun getContentIfNotHandled(): T? = if (hasBeenHandled) null  else content.also { hasBeenHandled = true }
 * //  Returns the content, even if it's already been handled.
 * fun peekContent(): T? = content
 * }
 *
 */

class EventTestSample {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun eventSampleInt() {
        val testContent = 123
        val event = Event(testContent)
        assertEquals(event.hasBeenHandled,false)
        assertEquals(event.peekContent(),testContent)

        val peekFirst = event.getContentIfNotHandled()
        assertEquals(peekFirst, testContent)
        assertEquals(peekFirst, 123)
        assertEquals(event.hasBeenHandled,true)
        assertEquals(event.peekContent(),testContent)

        val peekSecond = event.getContentIfNotHandled()
        assertEquals(peekSecond, null)
        assertNotEquals(peekSecond, 123)
        assertEquals(event.hasBeenHandled,true)
        assertEquals(event.peekContent(),testContent)
    }

    @Test
    fun eventSampleString() {
        val testContent = "apple"
        val event = Event(testContent)
        assertEquals(event.hasBeenHandled,false)
        assertEquals(event.peekContent(),testContent)

        val peekFirst = event.getContentIfNotHandled()
        assertEquals(peekFirst, testContent)
        assertEquals(event.hasBeenHandled,true)
        assertEquals(event.peekContent(),testContent)

        val peekSecond = event.getContentIfNotHandled()
        assertEquals(peekSecond, null)
        assertEquals(event.hasBeenHandled,true)
        assertEquals(event.peekContent(),testContent)
    }

    @Test
    fun eventSampleNull() {
        val testContent:Any? = null
        val event = Event(testContent)
        assertEquals(event.hasBeenHandled,false)
        assertNull(event.peekContent())

        val peekFirst = event.getContentIfNotHandled()
        assertNull(peekFirst)
        assertEquals(event.hasBeenHandled,true)
        assertNull(event.peekContent())

        val peekSecond = event.getContentIfNotHandled()
        assertNull(peekSecond)
        assertEquals(event.hasBeenHandled,true)
        assertNull(event.peekContent())

    }

    @Test
    fun eventSampleListInt() {
        val testContent = arrayListOf(1, 2, 3, 4, 5)
        val event = Event(testContent)
        assertEquals(event.hasBeenHandled,false)
        assertEquals(event.peekContent(),testContent)

        val peekFirst = event.getContentIfNotHandled()
        assertEquals(peekFirst, testContent)
        assertEquals(peekFirst?.get(3), 4)
        assertEquals(event.hasBeenHandled,true)
        assertEquals(event.peekContent(),testContent)

        val peekSecond = event.getContentIfNotHandled()
        assertEquals(peekSecond, null)
        assertNotEquals(peekSecond, 123)
        assertEquals(event.hasBeenHandled,true)
        assertEquals(event.peekContent(),testContent)
    }
    // Хорошо бы передать класс, например data/enum ...
    // Сложные типы составные: лист классов, класс листов
    // Лямбду, функцию, надо подумать что из Котлина

    // Передать событию в качестве контента другое событие

    // На этом закончить и перейти EventObserverUnit
}