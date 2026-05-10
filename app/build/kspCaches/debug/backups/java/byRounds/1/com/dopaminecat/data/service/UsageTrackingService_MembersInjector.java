package com.dopaminecat.data.service;

import com.dopaminecat.domain.usecase.CheckGoalExceedUseCase;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class UsageTrackingService_MembersInjector implements MembersInjector<UsageTrackingService> {
  private final Provider<CheckGoalExceedUseCase> checkGoalExceedUseCaseProvider;

  private final Provider<NotificationHelper> notificationHelperProvider;

  public UsageTrackingService_MembersInjector(
      Provider<CheckGoalExceedUseCase> checkGoalExceedUseCaseProvider,
      Provider<NotificationHelper> notificationHelperProvider) {
    this.checkGoalExceedUseCaseProvider = checkGoalExceedUseCaseProvider;
    this.notificationHelperProvider = notificationHelperProvider;
  }

  public static MembersInjector<UsageTrackingService> create(
      Provider<CheckGoalExceedUseCase> checkGoalExceedUseCaseProvider,
      Provider<NotificationHelper> notificationHelperProvider) {
    return new UsageTrackingService_MembersInjector(checkGoalExceedUseCaseProvider, notificationHelperProvider);
  }

  @Override
  public void injectMembers(UsageTrackingService instance) {
    injectCheckGoalExceedUseCase(instance, checkGoalExceedUseCaseProvider.get());
    injectNotificationHelper(instance, notificationHelperProvider.get());
  }

  @InjectedFieldSignature("com.dopaminecat.data.service.UsageTrackingService.checkGoalExceedUseCase")
  public static void injectCheckGoalExceedUseCase(UsageTrackingService instance,
      CheckGoalExceedUseCase checkGoalExceedUseCase) {
    instance.checkGoalExceedUseCase = checkGoalExceedUseCase;
  }

  @InjectedFieldSignature("com.dopaminecat.data.service.UsageTrackingService.notificationHelper")
  public static void injectNotificationHelper(UsageTrackingService instance,
      NotificationHelper notificationHelper) {
    instance.notificationHelper = notificationHelper;
  }
}
