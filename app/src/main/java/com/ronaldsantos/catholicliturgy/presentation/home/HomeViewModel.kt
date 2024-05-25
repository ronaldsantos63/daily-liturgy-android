package com.ronaldsantos.catholicliturgy.presentation.home

import com.ronaldsantos.catholicliturgy.domain.useCase.dailyLiturgy.GetCurrentDailyLiturgy
import com.ronaldsantos.catholicliturgy.library.framework.base.BaseViewState
import com.ronaldsantos.catholicliturgy.library.framework.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentDailyLiturgy: GetCurrentDailyLiturgy
) : MviViewModel<BaseViewState<HomeViewState>, HomeEvent>() {
    override fun onTriggerEvent(eventType: HomeEvent) {
        when (eventType){
            is HomeEvent.AddOrRemoveFavorite -> TODO()
            is HomeEvent.DeleteFavorite -> TODO()
            HomeEvent.LoadDailyLiturgy -> onLoadDailyLiturgy()
        }
    }

    private fun onLoadDailyLiturgy() = safeLaunch {
        setState(BaseViewState.Loading)
        val dailyLiturgy = getCurrentDailyLiturgy(Unit)
        setState(BaseViewState.Data(HomeViewState(dailyLiturgy = dailyLiturgy)))
    }

//    private fun onAddOrRemoveFavorite(dto: CharacterDto) = safeLaunch {
//        val params = UpdateCharacterFavorite.Params(dto)
//        call(updateCharacterFavorite(params))
//    }
//
//    private fun onLoadFavorites() = safeLaunch {
//        call(getFavorites(Unit)) {
//            if (it.isEmpty()) {
//                setState(BaseViewState.Empty)
//            } else {
//                setState(BaseViewState.Data(CharactersViewState(favorList = it)))
//            }
//        }
//    }
//
//    private fun onDeleteFavorite(id: Int) = safeLaunch {
//        call(deleteFavorite(DeleteCharacterFavorite.Params(id))) {
//            onTriggerEvent(CharactersEvent.LoadFavorites)
//        }
//    }
}
