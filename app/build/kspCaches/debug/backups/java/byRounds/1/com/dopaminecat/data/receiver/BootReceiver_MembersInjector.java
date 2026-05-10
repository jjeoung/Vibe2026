package com.dopaminecat.data.receiver;

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
public final class BootReceiver_MembersInjector implements MembersInjector<BootReceiver> {
  private final Provider<WorkScheduler> workSchedulerProvider;

  public BootReceiver_MembersInjector(Provider<WorkScheduler> workSchedulerProvider) {
    this.workSchedulerProvider = workSchedulerProvider;
  }

  public static MembersInjector<BootReceiver> create(
      Provider<WorkScheduler> workSchedulerProvider) {
    return new BootReceiver_MembersInjector(workSchedulerProvider);
  }

  @Override
  public void injectMembers(BootReceiver instance) {
    injectWorkScheduler(instance, workSchedulerProvider.get());
  }

  @InjectedFieldSignature("com.dopaminecat.data.receiver.BootReceiver.workScheduler")
  public static void injectWorkScheduler(BootReceiver instance, WorkScheduler workScheduler) {
    instance.workScheduler = workScheduler;
  }
}
