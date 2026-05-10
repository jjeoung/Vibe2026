package com.dopaminecat;

import androidx.hilt.work.HiltWorkerFactory;
import com.dopaminecat.data.service.NotificationHelper;
import com.dopaminecat.data.worker.WorkScheduler;
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
public final class DopamineCatApplication_MembersInjector implements MembersInjector<DopamineCatApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  private final Provider<NotificationHelper> notificationHelperProvider;

  private final Provider<WorkScheduler> workSchedulerProvider;

  public DopamineCatApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<NotificationHelper> notificationHelperProvider,
      Provider<WorkScheduler> workSchedulerProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
    this.notificationHelperProvider = notificationHelperProvider;
    this.workSchedulerProvider = workSchedulerProvider;
  }

  public static MembersInjector<DopamineCatApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<NotificationHelper> notificationHelperProvider,
      Provider<WorkScheduler> workSchedulerProvider) {
    return new DopamineCatApplication_MembersInjector(workerFactoryProvider, notificationHelperProvider, workSchedulerProvider);
  }

  @Override
  public void injectMembers(DopamineCatApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
    injectNotificationHelper(instance, notificationHelperProvider.get());
    injectWorkScheduler(instance, workSchedulerProvider.get());
  }

  @InjectedFieldSignature("com.dopaminecat.DopamineCatApplication.workerFactory")
  public static void injectWorkerFactory(DopamineCatApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }

  @InjectedFieldSignature("com.dopaminecat.DopamineCatApplication.notificationHelper")
  public static void injectNotificationHelper(DopamineCatApplication instance,
      NotificationHelper notificationHelper) {
    instance.notificationHelper = notificationHelper;
  }

  @InjectedFieldSignature("com.dopaminecat.DopamineCatApplication.workScheduler")
  public static void injectWorkScheduler(DopamineCatApplication instance,
      WorkScheduler workScheduler) {
    instance.workScheduler = workScheduler;
  }
}
