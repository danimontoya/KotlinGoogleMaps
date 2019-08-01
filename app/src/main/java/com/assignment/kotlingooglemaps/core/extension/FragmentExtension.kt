package com.assignment.kotlingooglemaps.core.extension

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.ViewModelProviders
import com.assignment.kotlingooglemaps.core.platform.BaseFragment
import com.assignment.kotlingooglemaps.features.presentation.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by danieh on 01/08/2019.
 */
inline fun <reified T : ViewModel> Fragment.viewModel(factory: Factory, body: T.() -> Unit): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}

val BaseFragment.viewContainer: View get() = (activity as MainActivity).root

val BaseFragment.appContext: Context get() = activity?.applicationContext!!
