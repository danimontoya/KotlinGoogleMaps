package com.assignment.kotlingooglemaps.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.assignment.kotlingooglemaps.KotlinGoogleMapsApp
import com.assignment.kotlingooglemaps.core.di.ApplicationComponent
import com.assignment.kotlingooglemaps.core.extension.viewContainer
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Created by danieh on 01/08/2019.
 */
abstract class BaseFragment : Fragment() {

    abstract fun layoutId(): Int

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as KotlinGoogleMapsApp).appComponent
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(layoutId(), container, false)

    internal fun hideAppBarLayout() {
        (activity?.appbar as AppBarLayout).setExpanded(false)
    }

    internal fun notifyLong(message: String) = Snackbar.make(viewContainer, message, Snackbar.LENGTH_LONG).show()

    internal fun notifyShort(message: String) = Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()
}
