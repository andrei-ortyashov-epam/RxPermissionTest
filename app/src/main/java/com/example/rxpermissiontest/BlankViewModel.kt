package com.example.rxpermissiontest

import android.Manifest
import android.util.Log
import androidx.lifecycle.*
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class BlankViewModel : ViewModel(), LifecycleObserver {

    private val errorMutable by lazy { MutableLiveData<String>() }
    val errorEvent: LiveData<String> by lazy { errorMutable }

    private val permissionMutable by lazy { MutableLiveData<Boolean>() }
    val permissionEvent: LiveData<Boolean> by lazy { permissionMutable }

    private lateinit var rxPermissions: RxPermissions

    protected val disposables = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) = disposables.add(disposable)

    fun init(rxPermissions: RxPermissions) {
        Log.d("+++", "init BlankViewModel")
        this.rxPermissions = rxPermissions
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onViewResumed() {
        Log.d("+++", "onViewResumed BlankViewModel")
        val disposable = rxPermissions
            .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe(this::onPermissionRequestResult)
        addDisposable(disposable)
    }

    private fun onPermissionRequestResult(granted: Boolean) {
        errorMutable.value = if (granted) "Permission Granted" else "No permission"
        permissionMutable.value = granted
    }

    override fun onCleared() {
        Log.d("+++", "onCleared BlankViewModel")
        disposables.clear()
    }
}
