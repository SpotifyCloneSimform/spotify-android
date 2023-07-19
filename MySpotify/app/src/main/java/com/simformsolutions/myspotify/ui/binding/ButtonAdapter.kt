package com.simformsolutions.myspotify.ui.binding

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.button.MaterialButton

@BindingAdapter("android:checked")
fun MaterialButton.doChecked(isChecked: Boolean) {
    this.isChecked = isChecked
}

@BindingAdapter("android:checkedAttrChanged")
fun MaterialButton.setCheckedListener(attrChange: InverseBindingListener) {
    addOnCheckedChangeListener { _, _ ->
        attrChange.onChange()
    }
}

@InverseBindingAdapter(attribute = "android:checked")
fun MaterialButton.doChecked(): Boolean = isChecked