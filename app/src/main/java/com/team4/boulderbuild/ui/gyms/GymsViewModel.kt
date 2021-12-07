package com.team4.boulderbuild.ui.gyms

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.team4.boulderbuild.ui.common.Event
import com.team4.boulderbuild.ui.common.ScopedViewModel
import com.team4.domain.Gym

import com.team4.usecases.GetAllGyms
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class GymsViewModel(private val allGyms: GetAllGyms,
                    override val uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

    private val _gyms = MutableLiveData<List<Gym>>()
    val gyms : LiveData<List<Gym>> get() = _gyms

    private val _navigateToGym = MutableLiveData<Event<Int>>()
    val navigateToGym: LiveData<Event<Int>> get() = _navigateToGym

    init {
        initScope()
        refresh()
    }

    private fun refresh() {
        launch {
            //ApplicationDI.getGymsRepository()?.getAllGyms()
            Log.d("Form", "oncreate!!!!!!!!!!!!!!!!!!!!!!!!!!!!" )
            _gyms.value = allGyms.invoke()
        }
    }

    fun onMovieClicked(gym: Gym) {
       _navigateToGym.value = Event(gym.id)
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}