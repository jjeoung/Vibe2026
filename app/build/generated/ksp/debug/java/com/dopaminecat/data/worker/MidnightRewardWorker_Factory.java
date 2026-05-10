package com.dopaminecat.data.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.dopaminecat.data.service.NotificationHelper;
import com.dopaminecat.domain.usecase.ClaimMidnightRewardUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class MidnightRewardWorker_Factory {
  private final Provider<ClaimMidnightRewardUseCase> claimMidnightRewardUseCaseProvider;

  private final Provider<WorkScheduler> workSchedulerProvider;

  private final Provider<NotificationHelper> notificationHelperProvider;

  public MidnightRewardWorker_Factory(
      Provider<ClaimMidnightRewardUseCase> claimMidnightRewardUseCaseProvider,
      Provider<WorkScheduler> workSchedulerProvider,
      Provider<NotificationHelper> notificationHelperProvider) {
    this.claimMidnightRewardUseCaseProvider = claimMidnightRewardUseCaseProvider;
    this.workSchedulerProvider = workSchedulerProvider;
    this.notificationHelperProvider = notificationHelperProvider;
  }

  public MidnightRewardWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, claimMidnightRewardUseCaseProvider.get(), workSchedulerProvider.get(), notificationHelperProvider.get());
  }

  public static MidnightRewardWorker_Factory create(
      Provider<ClaimMidnightRewardUseCase> claimMidnightRewardUseCaseProvider,
      Provider<WorkScheduler> workSchedulerProvider,
      Provider<NotificationHelper> notificationHelperProvider) {
    return new MidnightRewardWorker_Factory(claimMidnightRewardUseCaseProvider, workSchedulerProvider, notificationHelperProvider);
  }

  public static MidnightRewardWorker newInstance(Context context, WorkerParameters params,
      ClaimMidnightRewardUseCase claimMidnightRewardUseCase, WorkScheduler workScheduler,
      NotificationHelper notificationHelper) {
    return new MidnightRewardWorker(context, params, claimMidnightRewardUseCase, workScheduler, notificationHelper);
  }
}
