package com.team4.boulderBuild

import android.app.Application

import com.team4.boulderBuild.model.data.database.GymDatabase
import com.team4.boulderBuild.model.data.database.GymsRoomDataSource
import com.team4.boulderBuild.model.data.server.GymsRemoteDataSourceFake
import com.team4.boulderBuild.ui.dashboard.DashboardFragment
import com.team4.boulderBuild.ui.dashboard.DashboardViewModel
import com.team4.boulderBuild.ui.gymform.GymFormFragment
import com.team4.boulderBuild.ui.gymform.GymFormViewModel
import com.team4.boulderBuild.ui.gyms.GymsFragment
import com.team4.boulderBuild.ui.gyms.GymsViewModel
import com.team4.boulderBuild.ui.notifications.NotificationsFragment
import com.team4.boulderBuild.ui.notifications.NotificationsViewModel
import com.team4.data.repository.GymsRepository
import com.team4.data.source.GymsLocalDataSource
import com.team4.data.source.GymsRemoteDataSource
import com.team4.usecases.FindGymById
import com.team4.usecases.UpdateGym
import com.team4.usecases.GetAllGyms

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {

    single { GymDatabase.build(get()) }
    factory<GymsLocalDataSource> { GymsRoomDataSource(get()) }
    factory<GymsRemoteDataSource> { GymsRemoteDataSourceFake() }

    single<CoroutineDispatcher> { Dispatchers.Main }
}

val dataModule = module {
    factory { GymsRepository(get(), get()) }
}

private val scopesModule = module {

    scope(named<MainActivity>()) {
        //viewModel { MainViewModel(get(), get()) }
       //scoped { GetPopularMovies(get()) }
    }

    scope(named<GymsFragment>()) {
        viewModel { GymsViewModel(get(),get()) }
        scoped { GetAllGyms(get()) }
    }

    scope(named<DashboardFragment>()) {
        viewModel { DashboardViewModel() }
    }

    scope(named<NotificationsFragment>()) {
        viewModel { NotificationsViewModel() }
    }

    scope(named<GymFormFragment>()) {
        viewModel { (id: Int) -> GymFormViewModel(id, get(), get(), get()) }
        scoped { FindGymById(get()) }
        scoped { UpdateGym(get()) }
    }
}