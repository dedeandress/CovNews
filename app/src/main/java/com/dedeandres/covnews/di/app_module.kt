package com.dedeandres.covnews.di

import com.dedeandres.covnews.data.dashboard.repository.DashboardRepositoryImpl
import com.dedeandres.covnews.domain.dashboard.repository.DashboardRepository
import com.dedeandres.covnews.domain.dashboard.usecase.DashboardUseCase
import com.dedeandres.covnews.domain.dashboard.usecase.HotlineUseCase
import com.dedeandres.covnews.domain.dashboard.usecase.NewsUseCase
import com.dedeandres.covnews.presenter.dashboard.DashboardViewModel
import com.dedeandres.covnews.util.base.BaseViewModel
import com.dedeandres.covnews.util.rx.ApplicationSchedulerProvider
import com.dedeandres.covnews.util.rx.SchedulerProvider
import org.koin.dsl.module.applicationContext

val covNewsModule = applicationContext {

    bean {
        DashboardRepositoryImpl(get(), get()) as DashboardRepository
    }

    bean {
        DashboardUseCase(get()) as DashboardUseCase
    }

    bean {
        NewsUseCase(get()) as NewsUseCase
    }

    bean {
        HotlineUseCase(get()) as HotlineUseCase
    }

    bean {
        DashboardViewModel(get(), get(), get(), get()) as DashboardViewModel
    }
}

val rxModule = applicationContext {

    bean {
        ApplicationSchedulerProvider() as SchedulerProvider
    }
}

val covNewsApp = listOf(covNewsModule, rxModule)