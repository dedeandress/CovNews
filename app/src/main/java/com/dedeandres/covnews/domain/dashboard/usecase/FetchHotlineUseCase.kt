package com.dedeandres.covnews.domain.dashboard.usecase

import com.dedeandres.covnews.data.dashboard.entity.Hotline
import com.dedeandres.covnews.domain.dashboard.repository.DashboardRepository
import com.dedeandres.covnews.util.SingleUseCase
import io.reactivex.Single

class FetchHotlineUseCase(
    private val dashboardRepository: DashboardRepository
) : SingleUseCase<List<Hotline>>() {

    override fun buildUseCaseSingle(data: Map<String, Any?>): Single<List<Hotline>> {
        return dashboardRepository.getHotline()
    }
}