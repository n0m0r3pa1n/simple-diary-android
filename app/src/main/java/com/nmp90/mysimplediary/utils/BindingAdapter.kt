package com.nmp90.mysimplediary.utils

import android.databinding.BindingAdapter
import android.view.View


@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}