package com.dedeandres.covnews.domain.dashboard.usecase

import com.dedeandres.covnews.domain.dashboard.entity.mapToResult
import com.dedeandres.covnews.domain.dashboard.repository.DashboardRepository
import com.dedeandres.covnews.presenter.dashboard.entity.GlobalDataResult
import com.dedeandres.covnews.util.SingleUseCase
import io.reactivex.Single

class FetchDashboardUseCase(
    private val dashboardRepository: DashboardRepository
) : SingleUseCase<List<GlobalDataResult>>() {
    override fun buildUseCaseSingle(data: Map<String, Any?>): Single<List<GlobalDataResult>> {
        return dashboardRepository
            .getGlobalData()
            .map {
                it.mapToResult()
            }
    }
}