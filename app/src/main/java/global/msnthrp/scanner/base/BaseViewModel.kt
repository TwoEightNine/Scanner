package global.msnthrp.scanner.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import global.msnthrp.scanner.utils.androidSchedulers
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val loadingLiveData = MutableLiveData<Boolean>()

    private val errorLiveData = MutableLiveData<OneTimeEvent<String>>()

    val loading: LiveData<Boolean>
        get() = loadingLiveData

    val error: LiveData<OneTimeEvent<String>>
        get() = errorLiveData

    protected var isLoading: Boolean
        get() = loadingLiveData.value == true
        set(value) {
            loadingLiveData.value = value
        }

    protected fun setError(errorMessage: String) {
        isLoading = false
        errorLiveData.value = OneTimeEvent(errorMessage)
    }

    protected fun Disposable.addToDisposables() {
        compositeDisposable.add(this)
    }

    protected fun <T> Single<T>.subscr(consumer: (T) -> Unit) {
        androidSchedulers()
            .doOnSubscribe {
                isLoading = true
            }
            .doOnSuccess {
                isLoading = false
            }
            .doOnError { throwable ->
                setError(interceptError(throwable.message ?: "null throwable"))
            }
            .subscribe(consumer)
            .addToDisposables()
    }

    protected fun Completable.subscr(consumer: () -> Unit) {
        toSingleDefault(true).subscr { consumer() }
    }

    protected fun interceptError(errorMessage: String): String = errorMessage

    fun dispose() {
        compositeDisposable.dispose()
    }

}