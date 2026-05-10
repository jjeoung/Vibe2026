// filepath: app/src/main/kotlin/com/dopaminecat/domain/usecase/ObserveCatStateUseCase.kt
package com.dopaminecat.domain.usecase

import com.dopaminecat.domain.model.Cat
import com.dopaminecat.domain.repository.CatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 고양이 상태를 실시간 스트림으로 관찰한다.
 *
 * ViewModel 에서 collectAsStateWithLifecycle() 로 수집하며,
 * DB 변경이 발생할 때마다 자동으로 UI를 갱신시킨다.
 */
class ObserveCatStateUseCase @Inject constructor(
    private val catRepository: CatRepository,
) {
    operator fun invoke(): Flow<Cat> = catRepository.getCatStream()
}
