package com.ronaldsantos.catholicliturgy.domain.useCase.welcome

import androidx.annotation.VisibleForTesting
import com.ronaldsantos.catholicliturgy.domain.repository.WelcomeRepository
import com.ronaldsantos.catholicliturgy.library.framework.usecase.ReturnUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadOnBoarding @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: WelcomeRepository
) : ReturnUseCase<Unit, Boolean>() {
    override suspend fun execute(params: Unit): Flow<Boolean> {
        return repository.readOnBoardingState()
    }
}
