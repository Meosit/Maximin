package by.mksn.miapr

import java.util.*

private val RANDOM = Random()

fun randomInt(bound: Int = Int.MAX_VALUE) = RANDOM.nextInt(bound)

fun <T> Array<T>.randomElement(): T? = (if (this.isEmpty()) null else this[randomInt(this.size)])