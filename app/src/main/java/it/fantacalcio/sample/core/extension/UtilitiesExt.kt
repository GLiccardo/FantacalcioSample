package it.fantacalcio.sample.core.extension

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import timber.log.Timber


val Context.inputMethodManager get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

fun View.showKeyboard() {
    val imm = context.inputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
    try {
        context.inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (e: RuntimeException) {
        Timber.e(e.stackTraceToString())
    }
}

fun Fragment.hideKeyboard() {
    view?.hideKeyboard()
}

fun Activity.hideKeyboard() {
    this.currentFocus?.hideKeyboard() ?: View(this).hideKeyboard()
}