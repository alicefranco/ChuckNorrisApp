package br.pprojects.chucknorrisapp.util

import android.content.Context
import android.view.View
import android.app.AlertDialog
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Locale

fun View?.visible() {
    this?.visibility = View.VISIBLE
}

fun View?.gone() {
    this?.visibility = View.GONE
}

fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun AppCompatActivity?.addFragment(fragment: Fragment, @IdRes layoutItem: Int, tag: String, addTobackStack: Boolean) {
    if (addTobackStack)
        this?.supportFragmentManager?.beginTransaction()?.add(layoutItem, fragment, tag)?.addToBackStack(tag)?.commit()
    else
        this?.supportFragmentManager?.beginTransaction()?.add(layoutItem, fragment, tag)?.commit()
}

fun AppCompatActivity?.replaceFragment(fragment: Fragment, @IdRes layoutItem: Int, tag: String, addTobackStack: Boolean) {
    if (addTobackStack)
        this?.supportFragmentManager?.beginTransaction()?.replace(layoutItem, fragment, tag)?.addToBackStack(tag)?.commit()
    else
        this?.supportFragmentManager?.beginTransaction()?.replace(layoutItem, fragment, tag)?.commit()
}

fun createDialog(context: Context, title: String, message: String) {
    val builder = AlertDialog.Builder(context)

    builder
        .setTitle(title)
        .setMessage(message)
        .create().show()
}

fun String.formatString(originalFormat: String, finalFormat: String): String {
    val originalSdf = SimpleDateFormat(originalFormat, Locale.getDefault())
    val finalSdf = SimpleDateFormat(finalFormat, Locale.getDefault())

    originalSdf.parse(this)?.let {
        return finalSdf.format(it).toString()
    } ?: run {
        return ""
    }
}