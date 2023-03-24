package com.example.mbapp.utils

import android.content.Context
import android.view.View
import android.widget.Toast

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Any.toast(context: Context) =
    Toast.makeText(context, this.toString(), Toast.LENGTH_SHORT).show()