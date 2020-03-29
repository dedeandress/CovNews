package com.dedeandres.covnews.di

import com.dedeandres.covnews.data.dashboard.repository.DashboardRepositoryImpl
import com.dedeandres.covnews.domain.dashboard.repository.DashboardRepository
import com.dedeandres.covnews.domain.dashboard.usecase.FetchDashboardUseCase
import com.dedeandres.covnews.domain.dashboard.usecase.FetchIndonesiaDataUseCase
import com.dedeandres.covnews.domain.dashboard.usecase.FetchHotlineUseCase
import com.dedeandres.covnews.domain.dashboard.usecase.FetchNewsUseCase
import com.dedeandres.covnews.presenter.dashboard.SharedViewModel
import com.dedeandres.covnews.util.rx.ApplicationSchedulerProvider
import com.dedeandres.covnews.util.rx.SchedulerProvider
import org.koin.dsl.module.applicationContext

val covNewsModule = applicationContext {

    bean {
        DashboardRepositoryImpl(get(), get()) as DashboardRepository
    }

    bean {
        FetchDashboardUseCase(get()) as FetchDashboardUseCase
    }

    bean {
        FetchNewsUseCase(get()) as FetchNewsUseCase
    }

    bean {
        FetchHotlineUseCase(get()) as FetchHotlineUseCase
    }

    bean {
        FetchIndonesiaDataUseCase(get()) as FetchIndonesiaDataUseCase
    }

    bean {
        SharedViewModel(get(), get(), get(), get(), get()) as SharedViewModel
    }
}

val rxModule = applicationContext {

    bean {
        ApplicationSchedulerProvider() as SchedulerProvider
    }
}

val covNewsApp = listOf(covNewsModule, rxModule)