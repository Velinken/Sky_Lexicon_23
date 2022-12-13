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
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

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

class ResultTestExtension {


    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    // Extension Functions

    /** fold
     * Returns the result of [onSuccess] for the encapsulated value if this instance represents [success][Result.isSuccess]
     * or the result of [onFailure] function for the encapsulated [Throwable] exception if it is [failure][Result.isFailure].
     * Возвращает результат [onSuccess] для инкапсулированного значения, если этот экземпляр представляет [success][Result.isSuccess]
     * или результат функции [OnFailure] для инкапсулированного исключения [Throwable], если это [сбой][Result.isFailure].
     *
     * Note, that this function rethrows any [Throwable] exception thrown by [onSuccess] or by [onFailure] function.
     * Обратите внимание, что эта функция переосмысливает любое исключение [Throwable], вызванное функцией [onSuccess] или функцией [OnFailure].

    public inline fun <R, T> Result<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (exception: Throwable) -> R
    ): R {
    contract {
    callsInPlace(onSuccess, InvocationKind.AT_MOST_ONCE)
    callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
    }
    return when (val exception = exceptionOrNull()) {
    null -> onSuccess(value as T)
    else -> onFailure(exception)
    }
    }
     */


    /** getOrDefault
     * Returns the encapsulated value if this instance represents [success][Result.isSuccess] or the
     * [defaultValue] if it is [failure][Result.isFailure].
     * Возвращает инкапсулированное значение, если этот экземпляр представляет [success][Result.isSuccess] или
     * [Значение по умолчанию], если это [сбой][Результат.isFailure].
     *
     * This function is a shorthand for `getOrElse { defaultValue }` (see [getOrElse]).
     * Эта функция является сокращением для "getOrElse { defaultValue}" (см. [getOrElse]).

    public inline fun <R, T : R> Result<T>.getOrDefault(defaultValue: R): R {
    if (isFailure) return defaultValue
    return value as T
    }
     */



    /** getOrElse
     * Returns the encapsulated value if this instance represents [success][Result.isSuccess] or the
     * result of [onFailure] function for the encapsulated [Throwable] exception if it is [failure][Result.isFailure].
     * Возвращает инкапсулированное значение, если этот экземпляр представляет [success][Result.isSuccess] или
     * результат функции [OnFailure] для инкапсулированного исключения [Throwable], если это [failure][Result.isFailure].
     *
     * Note, that this function rethrows any [Throwable] exception thrown by [onFailure] function.
     * Обратите внимание, что эта функция переосмысливает любое исключение [Throwable], вызванное функцией [OnFailure].
     *
     * This function is a shorthand for `fold(onSuccess = { it }, onFailure = onFailure)` (see [fold]).
     * Эта функция является сокращением для "fold(onSuccess = { it }, OnFailure = OnFailure)" (см. [fold]).

    public inline fun <R, T : R> Result<T>.getOrElse(onFailure: (exception: Throwable) -> R): R {
    contract {
    callsInPlace(onFailure, InvocationKind.AT_MOST_ONCE)
    }
    return when (val exception = exceptionOrNull()) {
    null -> value as T
    else -> onFailure(exception)
    }
    }
     */


    /** getOrThrow()
     * Returns the encapsulated value if this instance represents [success][Result.isSuccess] or throws the encapsulated [Throwable] exception
     * if it is [failure][Result.isFailure].
     * Возвращает инкапсулированное значение, если этот экземпляр представляет [успех][Result.is Успех] или вызывает инкапсулированное исключение [Throwable]
     * если это [сбой][Result.is Неудача].
     *
     * This function is a shorthand for `getOrElse { throw it }` (see [getOrElse]).
     * Эта функция является сокращением для "getOrElse { throw it}" (см. [getOrElse]).

    public inline fun <T> Result<T>.getOrThrow(): T {
    throwOnFailure()
    return value as T
    }
     */


}