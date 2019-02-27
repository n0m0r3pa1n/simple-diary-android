package com.nmp90.mysimplediary.utils

import android.view.View
import androidx.databinding.BindingAdapter


@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}
