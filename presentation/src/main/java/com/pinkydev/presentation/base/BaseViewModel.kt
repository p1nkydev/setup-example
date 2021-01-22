package com.pinkydev.presentation.base


import androidx.lifecycle.*
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import com.pinkydev.domain.base.BaseUseCase
import com.pinkydev.domain.base.CompletionBlock
import com.pinkydev.domain.exceptions.NetworkError
import com.pinkydev.domain.exceptions.ServerError
import com.pinkydev.presentation.tools.NavigationCommand
import com.pinkydev.presentation.tools.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Effect> : ViewModel() {

    private val _state = MutableLiveData<State>()

    private val _effect = SingleLiveEvent<Effect>()

    private val _commonEffect = SingleLiveEvent<BaseEffect>()

    val commonEffect: LiveData<BaseEffect>
        get() = _commonEffect

    val navigationCommands = SingleLiveEvent<NavigationCommand>()

    val state: LiveData<State>
        get() = _state

    val effect: LiveData<Effect>
        get() = _effect

    protected fun postState(state: State) {
        _state.value = state
    }

    protected fun postEffect(effect: Effect) {
        _effect.postValue(effect)
    }

    fun navigate(directions: NavDirections, extras: Navigator.Extras? = null) {
        navigationCommands.postValue(NavigationCommand.To(directions, extras))
    }

    fun navigate(command: NavigationCommand) {
        navigationCommands.postValue(command)
    }

    /**
     * Override if arguments will be used
     */
    open fun loadArguments() {}


    suspend fun <P, R> BaseUseCase<P, R>.getWith(param: P): R {
        var result: R? = null
        val block: CompletionBlock<R> = {
            onSuccess = { result = it }
            onError = { throw it }
        }
        execute(param, block)
        return result!!
    }

    fun launchAll(
        withLoader: Boolean = true,
        errorHandle: (Throwable) -> Unit = ::handleError,
        block: suspend () -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (withLoader) _commonEffect.value = ShowGeneralLoading()
                block()
            } catch (t: Throwable) {
                errorHandle(t)
            } finally {
                if (withLoader) _commonEffect.value = HideGeneralLoading()
            }
        }
    }

    protected fun handleError(t: Throwable) {
        when (t) {
            is ServerError.NotAuthorized -> _commonEffect.postValue(ForceLogout(t))
            is ServerError.ServerIsDown -> _commonEffect.postValue(BackEndError())
            is NetworkError -> _commonEffect.postValue(NoInternet())
            else -> _commonEffect.postValue(UnknownError(cause = t))
        }
    }

    protected fun <T> Flow<T>.handleLoading(): Flow<T> = flow {
        this@handleLoading
            .onStart { _commonEffect.postValue(ShowGeneralLoading()) }
            .collectIndexed { index, value ->
                if (index == 0) _commonEffect.postValue(HideGeneralLoading())
                emit(value)
            }
    }

    protected fun <T> Flow<T>.handleError(): Flow<T> = catch { handleError(it) }

    protected fun <T> Flow<T>.launch(scope: CoroutineScope = viewModelScope): Job =
        this.handleError()
            .handleLoading()
            .launchIn(scope)

    protected fun <P, R, U : BaseUseCase<P, R>> U.launch(param: P, block: CompletionBlock<R> = {}) {
        viewModelScope.launch {
            val actualRequest = BaseUseCase.Request<R>().apply(block)

            val proxy: CompletionBlock<R> = {
                onStart = {
                    showLoading(param)
                    actualRequest.onStart?.invoke()
                }
                onSuccess = {
                    hideLoading(param)
                    actualRequest.onSuccess(it)
                }
                onCancel = {
                    hideLoading(param)
                    actualRequest.onCancel?.invoke(it)
                }
                onTerminate = actualRequest.onTerminate
                onError = {
                    hideLoading(param)
                    actualRequest.onError?.invoke(it) ?: handleError(it)
                }
            }
            execute(param, proxy)
        }
    }

    protected fun <P, R, U : BaseUseCase<P, R>> U.launchNoLoading(
        param: P,
        block: CompletionBlock<R> = {}
    ) {
        viewModelScope.launch {
            val actualRequest = BaseUseCase.Request<R>().apply(block)

            val proxy: CompletionBlock<R> = {
                onStart = actualRequest.onStart
                onSuccess = actualRequest.onSuccess
                onCancel = actualRequest.onCancel
                onTerminate = actualRequest.onTerminate
                onError = actualRequest.onError ?: ::handleError
            }
            execute(param, proxy)
        }
    }

    protected fun <P, R, U : BaseUseCase<P, R>> U.launchUnsafe(
        param: P,
        block: CompletionBlock<R> = {}
    ) {
        viewModelScope.launch { execute(param, block) }
    }

    protected open fun <P> showLoading(param: P) {
        _commonEffect.value = ShowGeneralLoading()
    }

    protected open fun <P> hideLoading(param: P) {
        _commonEffect.value = HideGeneralLoading()
    }
}