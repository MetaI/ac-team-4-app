package com.team4.boulderBuild.ui.common

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.team4.boulderBuild.BoulderBuildApp
import kotlin.properties.Delegates


val Context.app: BoulderBuildApp
    get() = applicationContext as BoulderBuildApp


fun <T : ViewDataBinding> ViewGroup.bindingInflate(
    @LayoutRes layoutRes: Int,
    attachToRoot: Boolean = true
): T =
    DataBindingUtil.inflate(LayoutInflater.from(context), layoutRes, this, attachToRoot)

inline fun <VH : RecyclerView.ViewHolder, T> RecyclerView.Adapter<VH>.basicDiffUtil(
    initialValue: List<T>,
    crossinline areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    crossinline areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new }
) =
    Delegates.observable(initialValue) { _, old, new ->
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                areItemsTheSame(old[oldItemPosition], new[newItemPosition])

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                areContentsTheSame(old[oldItemPosition], new[newItemPosition])

            override fun getOldListSize(): Int = old.size

            override fun getNewListSize(): Int = new.size
        }).dispatchUpdatesTo(this@basicDiffUtil)
    }