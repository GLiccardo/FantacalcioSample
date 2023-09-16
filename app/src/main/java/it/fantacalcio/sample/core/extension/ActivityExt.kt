package it.fantacalcio.sample.core.extension

import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import it.fantacalcio.sample.R

/*
 * AppCompatActivity
 */

inline fun <reified T : AppCompatActivity> AppCompatActivity.startActivity() {
    val intent = Intent()
    intent.setClass(this, T::class.java)
    startActivity(intent)
}

inline fun <reified T : AppCompatActivity> AppCompatActivity.startActivity(options: Bundle?) {
    val intent = Intent()
    intent.setClass(this, T::class.java)
    startActivity(intent, options)
}


/*
 * Fragment
 */

fun AppCompatActivity.replaceFragment(
    fragment: Fragment,
    fragmentTag: String = fragment.javaClass.canonicalName.orEmpty(),
    addToBackStack: Boolean = true,
    @IdRes containerResId: Int = R.id.container
) {
    supportFragmentManager
        .beginTransaction()
        .setReorderingAllowed(true)
        .replace(containerResId, fragment, fragmentTag)
        .apply {
            if (addToBackStack) {
                addToBackStack(fragmentTag)
            }
        }
        .commit()
}

fun AppCompatActivity.addFragment(
    fragment: Fragment,
    fragmentTag: String = fragment.javaClass.canonicalName.orEmpty(),
    addToBackStack: Boolean = true,
    @IdRes containerResId: Int = R.id.container
) {
    supportFragmentManager
        .beginTransaction()
        .setReorderingAllowed(true)
        .add(containerResId, fragment, fragmentTag)
        .apply {
            if (addToBackStack) {
                addToBackStack(fragmentTag)
            }
        }
        .commit()
}

fun AppCompatActivity.popFragment(
    fragmentTag: String? = null,
    popImmediate: Boolean = false
) {
    if (!fragmentTag.isNullOrEmpty()) {
        if (popImmediate) {
            supportFragmentManager.popBackStackImmediate(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } else {
            supportFragmentManager.popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    } else {
        if (popImmediate) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}