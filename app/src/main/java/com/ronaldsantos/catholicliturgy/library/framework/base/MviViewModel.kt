package com.ronaldsantos.catholicliturgy.library.framework.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class MviViewModel<STATE : BaseViewState<*>, EVENT> : MvvmViewModel() {

    private val _uiState = MutableStateFlow<BaseViewState<*>>(BaseViewState.Empty)
    val uiState = _uiState.asStateFlow()

    abstract fun onTriggerEvent(eventType: EVENT)

    protected fun setState(state: STATE) = _uiState.update {
        state
    }

    override fun startLoading() {
        super.startLoading()
        _uiState.value = BaseViewState.Loading
    }

    override fun handleError(exception: Throwable) {
        super.handleError(exception)
        val errorMessage = when (exception) {
            is java.net.UnknownHostException -> "Não foi possível conectar ao servidor. Verifique sua conexão com a internet."
            is java.net.SocketTimeoutException -> "A conexão com o servidor expirou. Tente novamente mais tarde."
            is retrofit2.HttpException -> {
                when (exception.code()) {
                    in 500..599 -> "${exception.code()} - O servidor está temporariamente indisponível. Tente novamente mais tarde."
                    else -> "${exception.code()} - Ocorreu um erro ao processar sua solicitação. Por favor, tente novamente."
                }
            }
            else -> "Ocorreu um erro inesperado. Por favor, tente novamente."
        }
        _uiState.value = BaseViewState.Error(Throwable(errorMessage, cause = exception))
    }
}