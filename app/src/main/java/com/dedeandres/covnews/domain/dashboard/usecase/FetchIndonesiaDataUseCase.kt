package com.dedeandres.covnews.domain.dashboard.usecase

import com.dedeandres.covnews.domain.dashboard.entity.mapToResult
import com.dedeandres.covnews.domain.dashboard.repository.DashboardRepository
import com.dedeandres.covnews.presenter.dashboard.entity.IndonesiaDataResult
import com.dedeandres.covnews.util.SingleUseCase
import io.reactivex.Single

class FetchIndonesiaDataUseCase(
    private val dashboardRepository: DashboardRepository
) : SingleUseCase<List<IndonesiaDataResult>>() {

    override fun buildUseCaseSingle(data: Map<String, Any?>): Single<List<IndonesiaDataResult>> {
        return dashboardRepository.getIndonesiaData().map {
            it.mapToResult()
        }
    }

}