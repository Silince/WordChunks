package com.appchamp.wordchunks.ui

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Any custom fragment or activity can be turned into a LifecycleOwner by implementing
 * the built-in LifecycleRegistryOwner interface (instead of extending LifecycleFragment or
 * LifecycleActivity).
 */
abstract class BaseActivity<T : AndroidViewModel> : AppCompatActivity(), LifecycleRegistryOwner {

    protected abstract val viewModelClass: Class<T>

    protected lateinit var viewModel: T

    private val lifecycleRegistry: LifecycleRegistry by lazy { LifecycleRegistry(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(viewModelClass)
    }

    override fun getLifecycle(): LifecycleRegistry = lifecycleRegistry
}