package it.fantacalcio.sample.core.extension

import it.fantacalcio.sample.core.constants.Constants.EMPTY_STRING

fun String?.orEmpty() = this?.let { this } ?: kotlin.run { EMPTY_STRING }
fun String.first() = this.substring(0, 1)