package com.dedeandres.covnews.domain.dashboard.usecase

import com.dedeandres.covnews.domain.dashboard.entity.mapToResult
import com.dedeandres.covnews.domain.dashboard.repository.DashboardRepository
import com.dedeandres.covnews.presenter.dashboard.entity.NewsResult
import com.dedeandres.covnews.util.SingleUseCase
import io.reactivex.Single

class NewsUseCase (
    private val dashboardRepository: DashboardRepository
): SingleUseCase<List<NewsResult>>() {

    override fun buildUseCaseSingle(data: Map<String, Any?>): Single<List<NewsResult>> {
        return dashboardRepository.getNews().map { list ->
            list.map {
                it.mapToResult()
            }
        }
    }
}