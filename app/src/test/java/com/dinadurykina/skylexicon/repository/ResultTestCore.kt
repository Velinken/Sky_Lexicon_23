package com.dinadurykina.skylexicon.repository

//package kotlin
// API:   https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/
// ENG:   https://gist.github.com/DinaDurykina/7ace19e138370c6a9b8ddf54f9d542c4
// Eng/RUS:   https://gist.github.com/DinaDurykina/4167a080d6d09c3ac5da1869ece421e2
// https://habr.com/ru/post/545926/

// 05.07.2021 https://t.me/kotlin_start/49848

// https://github.com/Kotlin/KEEP/blob/master/proposals/stdlib/result.md
// https://github.com/Kotlin/KEEP/blob/master/proposals/stdlib/result.md

/**
 * Функции имеют понятные и понятные имена, которые следуют устоявшейся традиции стандартной библиотеки Kotlin
 * и устанавливают следующие дополнительные соглашения:
 *
 * Функции, которые могут генерировать ранее подавленное (захваченное) исключение,
 * имеют явный OrThrow суффикс, например getOrThrow.
 * Функции, которые захватывают выброшенное исключение и инкапсулируют его в Result экземпляр,
 * имеют явный Catching суффикс, например runCatching и mapCatching.
 * Традиционная map функция преобразования, которая работает с успешными случаями,
 * дополняется recover функцией, которая аналогичным образом преобразует исключительные случаи.
 * Внутри либо отказ map или recover превратить операцию Прерывает как традиционные функции,
 * но mapCatching и recoverCatchingEncapsulate неудача в преобразовании в результирующий Result.
 * Функции для запроса кейса имеют естественные имена isSuccess и isFailure.
 * Функции, которые действуют в случаях успеха или неудачи,
 * получают имена onSuccess и onFailure возвращают свой получатель
 * без изменений для дальнейшего связывания в соответствии с традицией,
 * установленной onEach расширением из Стандартной библиотеки.
 * Строковое представление Result значения ( toString) - это либо, Success(v)либо Failure(x)
 * где v и x являются строковыми представлениями соответствующего значения и исключения.
 * equals и hashCode реализуются естественным образом для типа результата,
 * сравнивая соответствующие значения или исключения.
 */

import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * A discriminated union that encapsulates a successful outcome with a value of type [T]
 * or a failure with an arbitrary [Throwable] exception.
 * Дискриминируемое объединение, которое инкапсулирует успешный результат со значением типа [T]
 * или сбой с произвольным исключением [Throwable].
 * https://dev.to/mahendranv/kotlin-value-class-new-kid-in-town-3p9h
 * public value class Result<out T> @PublishedApi internal constructor(
    @PublishedApi
    internal val value: Any?
    ) : java.io.Serializable
 */

class ResultTestCore {


    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    // Properties

    /** isSuccess
     * Returns true if this instance represents a successful outcome. In this case isFailure returns false
     * Возвращает значение true, если этот экземпляр представляет собой успешный результат. В этом случае ошибка возвращает false
     * Returns `true` if this instance represents a successful outcome.
     * In this case [isFailure] returns `false`.
     * Возвращает `true", если этот экземпляр представляет собой успешный результат.
     * В этом случае [isFailure] возвращает `false`.
     *  public val isSuccess: Boolean get() = value !is Failure
     */
    @Test
    fun isSuccess() {
        val a= Result
    }

    /** isFailure
     * Returns true if this instance represents a failed outcome. In this case isSuccess returns false
     * Возвращает значение true, если этот экземпляр представляет собой неудачный результат. В этом случае Успех возвращает false
     * Returns `true` if this instance represents a failed outcome.
     * In this case [isSuccess] returns `false`.
     * Возвращает `true", если этот экземпляр представляет собой неудачный результат.
     * В этом случае [isSuccess] возвращает `false`.
     *  public val isFailure: Boolean get() = value is Failure
     */
    @Test
    fun isFailure() {
    }

    // Functions

    /** getOrNull()
     * Returns the encapsulated value if this instance represents [success][Result.isSuccess] or `null`
     * if it is [failure][Result.isFailure].
     * Возвращает инкапсулированное значение, если этот экземпляр представляет [success][Result.isSuccess] или `null`
     * если это [сбой][Result.isFailure].
     *
     * This function is a shorthand for `getOrElse { null }` (see [getOrElse]) or
     * `fold(onSuccess = { it }, onFailure = { null })` (see [fold]).
     * Эта функция является сокращением для "getOrElse { null}" (см. [getOrElse]) или
     * `fold(onSuccess = { it }, OnFailure = { null }) ' (см. [fold]).
     *  public inline fun getOrNull(): T? =
        when {
        isFailure -> null
        else -> value as T
        }
     */
    @Test
    fun getOrNull() {
    }

    /** exceptionOrNull()
     * Returns the encapsulated Throwable exception if this instance represents failure or null if it is success.
     * Возвращает инкапсулированное выбрасываемое исключение, если этот экземпляр представляет собой сбой, или значение null, если он успешен.
     *
     * Returns the encapsulated [Throwable] exception if this instance represents [failure][isFailure] or `null`
     * if it is [success][isSuccess].
     *  Возвращает инкапсулированное исключение [Throwable], если этот экземпляр представляет собой [failure][isFailure] или "null".
     * если это [успех][isSuccess].
     *
     * This function is a shorthand for `fold(onSuccess = { null }, onFailure = { it })` (see [fold]).
     * Эта функция является сокращение для "fold(onSuccess = { null }, OnFailure = { it})" (см. [fold]).
     * public fun exceptionOrNull(): Throwable? =
        when (value) {
        is Failure -> value.exception
        else -> null
        }
     */

    @Test
    fun exceptionOrNull() {
    }

    /** toString()
     * Returns a string `Success(v)` if this instance represents [success][Result.isSuccess]
     * where `v` is a string representation of the value or a string `Failure(x)` if
     * it is [failure][isFailure] where `x` is a string representation of the exception.
     * Возвращает строку " Success(v)", если этот экземпляр представляет [success][Result.isSuccess]
     * где " v "- строковое представление значения или строка " Failure(x)", если
     * это [failure][isFailure], где " x " - строковое представление исключения.
     * public override fun toString(): String =
        when (value) {
        is Failure -> value.toString() // "Failure($exception)"
        else -> "Success($value)"
        }
     */
    @Test
    fun testToString() {
    }

    // ?????
    @Test
    fun getValue() {
        val a = Result
    }

    // Companion Object Functions

    /**
     * Companion object for [Result] class that contains its constructor functions
     * [success] and [failure].
     *  Сопутствующий объект для класса [Result], который содержит его функции конструктора
     * [успех] и [неудача].
     */

    /** success(value: T)
     * Returns an instance that encapsulates the given [value] as successful value.
     * Возвращает экземпляр, который инкапсулирует данное [значение] в качестве успешного значения.

        public inline fun <T> success(value: T): Result<T> = Result(value)
    */


    /** failure(exception: Throwable)
     * Returns an instance that encapsulates the given [Throwable] [exception] as failure.
     * Возвращает экземпляр, который инкапсулирует данное [Throwable] [исключение] как сбой.

    public inline fun <T> failure(exception: Throwable): Result<T> =  Result(createFailure(exception))
    */

}