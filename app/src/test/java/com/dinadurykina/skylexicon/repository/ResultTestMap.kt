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

/** ResultTestMap
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

class ResultTestMap {


    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    /** map
     * Returns the encapsulated result of the given [transform] function applied to the encapsulated value
     * if this instance represents [success][Result.isSuccess] or the
     * original encapsulated [Throwable] exception if it is [failure][Result.isFailure].
     * Возвращает инкапсулированный результат данной функции [transform], примененной к инкапсулированному значению
     * если этот экземпляр представляет [success][Result.isSuccess] или
     * исходное инкапсулированное исключение [Throwable], если это [failure][Result.isFailure].
     *
     * Note, that this function rethrows any [Throwable] exception thrown by [transform] function.
     * See [mapCatching] for an alternative that encapsulates exceptions.
     * Обратите внимание, что эта функция переосмысливает любое исключение [Throwable], вызванное функцией [transform].
     * Альтернативу, инкапсулирующую исключения, см. в разделе [mapCatching].

    public inline fun <R, T> Result<T>.map(transform: (value: T) -> R): Result<R> {
        contract {
            callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
        }
        return when {
            isSuccess -> Result.success(transform(value as T))
            else -> Result(value)
        }
    }
     */

    /** mapCatching
     * Returns the encapsulated result of the given [transform] function applied to the encapsulated value
     * if this instance represents [success][Result.isSuccess] or the
     * original encapsulated [Throwable] exception if it is [failure][Result.isFailure].
     * Возвращает инкапсулированный результат данной функции [transform], примененной к инкапсулированному значению
     * если этот экземпляр представляет [success][Result.isSuccess] или
     * исходное инкапсулированное исключение [Throwable], если это [failure][Result.isFailure].
     *
     * This function catches any [Throwable] exception thrown by [transform] function and encapsulates it as a failure.
     * Эта функция улавливает любое исключение [Throwable] , вызванное функцией [transform], и инкапсулирует его как сбой.
     * See [map] for an alternative that rethrows exceptions from `transform` function.
     * См. [карта] для альтернативы, которая переосмысливает исключения из функции " преобразование`.

    public inline fun <R, T> Result<T>.mapCatching(transform: (value: T) -> R): Result<R> {
        return when {
            isSuccess -> runCatching { transform(value as T) }
            else -> Result(value)
        }
    }
     */

    /** recover
     * Returns the encapsulated result of the given [transform] function applied to the encapsulated [Throwable] exception
     * if this instance represents [failure][Result.isFailure] or the
     * original encapsulated value if it is [success][Result.isSuccess].
     * Возвращает инкапсулированный результат данной функции [transform], примененной к инкапсулированному исключению [Throwable] .
     * если этот экземпляр представляет [failure][Result.isFailure] или
     * исходное инкапсулированное значение, если это [success][Result.isSuccess].
     *
     * Note, that this function rethrows any [Throwable] exception thrown by [transform] function.
     * See [recoverCatching] for an alternative that encapsulates exceptions.
     * Обратите внимание, что эта функция переосмысливает любое исключение [Throwable], вызванное функцией [transform].
     * Альтернативу, инкапсулирующую исключения, см. в разделе [recoverCatching].


    public inline fun <R, T : R> Result<T>.recover(transform: (exception: Throwable) -> R): Result<R> {
        contract {
            callsInPlace(transform, InvocationKind.AT_MOST_ONCE)
        }
        return when (val exception = exceptionOrNull()) {
            null -> this
            else -> Result.success(transform(exception))
        }
    }
     */

    /** recoverCatching
     * Returns the encapsulated result of the given [transform] function applied to the encapsulated [Throwable] exception
     * if this instance represents [failure][Result.isFailure] or the
     * original encapsulated value if it is [success][Result.isSuccess].
     * * Возвращает инкапсулированный результат данной функции [transform], примененной к инкапсулированному исключению [Throwable] .
     * если этот экземпляр представляет [failure][Result.isFailure] или
     * исходное инкапсулированное значение, если это [success][Result.isSuccess].
     *
     * This function catches any [Throwable] exception thrown by [transform] function and encapsulates it as a failure.
     * See [recover] for an alternative that rethrows exceptions.
     * Эта функция улавливает любое исключение [Throwable] , вызванное функцией [transform], и инкапсулирует его как сбой.
     * См. [восстановление] для альтернативы, которая переосмысливает исключения.

    public inline fun <R, T : R> Result<T>.recoverCatching(transform: (exception: Throwable) -> R): Result<R> {
        return when (val exception = exceptionOrNull()) {
            null -> this
            else -> runCatching { transform(exception) }
        }
    }
     */

// "peek" onto value/exception and pipe
// "заглянуть" в значение/исключение и канал

    /** onFailure
     * Performs the given [action] on the encapsulated [Throwable] exception if this instance represents [failure][Result.isFailure].
     * Returns the original `Result` unchanged.
     * Выполняет заданное [действие] над инкапсулированным значением, если этот экземпляр представляет [success][Result.isSuccess].
     * Возвращает исходный " Результат` без изменений.

    public inline fun <T> Result<T>.onFailure(action: (exception: Throwable) -> Unit): Result<T> {
        contract {
            callsInPlace(action, InvocationKind.AT_MOST_ONCE)
        }
        exceptionOrNull()?.let { action(it) }
        return this
    }
     */

    /** onSuccess
     * Performs the given [action] on the encapsulated value if this instance represents [success][Result.isSuccess].
     * Returns the original `Result` unchanged.
     * Выполняет заданное [действие] над инкапсулированным значением, если этот экземпляр представляет [success][Result.isSuccess].
     * Возвращает исходный " Результат` без изменений.

    public inline fun <T> Result<T>.onSuccess(action: (value: T) -> Unit): Result<T> {
        contract {
            callsInPlace(action, InvocationKind.AT_MOST_ONCE)
        }
        if (isSuccess) action(value as T)
        return this
    }
     */


    /** runCatching no in API
     * Calls the specified function [block] and returns its encapsulated result if invocation was successful,
     * catching any [Throwable] exception that was thrown from the [block] function execution and encapsulating it as a failure.
     * Вызывает указанную функцию [блок] и возвращает ее инкапсулированный результат, если вызов был успешным.,
     * перехват любого исключения [Throwable] , которое было вызвано выполнением функции [block], и инкапсуляция его как сбоя.

    public inline fun <R> runCatching(block: () -> R): Result<R> {
    return try {
    Result.success(block())
    } catch (e: Throwable) {
    Result.failure(e)
    }
    }
     */

    /** T.runCatching  no in API
     * Calls the specified function [block] with `this` value as its receiver and returns its encapsulated result if invocation was successful,
     * catching any [Throwable] exception that was thrown from the [block] function execution and encapsulating it as a failure.
     * Вызывает указанную функцию [блок] со значением "this" в качестве получателя и возвращает ее инкапсулированный результат, если вызов был успешным.,
     * перехват любого исключения [Throwable] , которое было вызвано выполнением функции [block], и инкапсуляция его как сбоя.

    public inline fun <T, R> T.runCatching(block: T.() -> R): Result<R> {
    return try {
    Result.success(block())
    } catch (e: Throwable) {
    Result.failure(e)
    }
    }
     */

}