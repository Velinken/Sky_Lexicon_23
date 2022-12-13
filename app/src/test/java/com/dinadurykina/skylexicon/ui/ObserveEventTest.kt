package com.dinadurykina.skylexicon.ui
// https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
// этот класс имеет смысл разместить в подпакет util или utils
// build.gradle(Module): dependencies.implementation 'androidx.fragment:fragment-ktx:1.3.5'
// Дальнейшее развитие со штатной lifecycle:lifecycle-runtime-ktx:2.4.0-alpha01
// https://proandroiddev.com/android-singleliveevent-redux-with-kotlin-flow-b755c70bb055
// https://medium.com/androiddevelopers/a-safer-way-to-collect-flows-from-android-uis-23080b1f8bda
// https://gist.github.com/JoseAlcerreca/e0bba240d9b3cffa258777f12e5c0ae9

// Event     https://gist.github.com/52e326bc01a8cfaf5e410f239cd801db
// ViewModel https://gist.github.com/Dzendo/ef2167d1572f5fb6af95d6700c879cce
// Fragment  https://gist.github.com/Dzendo/0eb95f9a8004ddd7a1ab98755a1e6d38
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * An [observeEvent] for [Event]s, simplifying the pattern of checking if the [Event]'s content has already been handled.
 * [Наблюдатель] для [события]s, упрощающий шаблон проверки того, было ли содержимое [события] уже обработано.
 *
 * [onEventUnhandledContent] is *only* called if the [Event]'s contents has not been handled.
 * [on Event Unhandled Content] вызывается *только* в том случае, если содержимое [события] не было обработано.
 * про жту функцию здесь:
 * inline fun <T> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, crossinline onEventUnhandledContent: (T) -> Unit) {
 *        observe(owner) { it?.getContentIfNotHandled()?.let(onEventUnhandledContent) }
 * }
 */

class ObserveEventTest {

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