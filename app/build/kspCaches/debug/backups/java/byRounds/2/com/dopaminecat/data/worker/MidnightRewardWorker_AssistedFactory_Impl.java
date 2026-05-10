package com.dopaminecat.data.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MidnightRewardWorker_AssistedFactory_Impl implements MidnightRewardWorker_AssistedFactory {
  private final MidnightRewardWorker_Factory delegateFactory;

  MidnightRewardWorker_AssistedFactory_Impl(MidnightRewardWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public MidnightRewardWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<MidnightRewardWorker_AssistedFactory> create(
      MidnightRewardWorker_Factory delegateFactory) {
    return InstanceFactory.create(new MidnightRewardWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<MidnightRewardWorker_AssistedFactory> createFactoryProvider(
      MidnightRewardWorker_Factory delegateFactory) {
    return InstanceFactory.create(new MidnightRewardWorker_AssistedFactory_Impl(delegateFactory));
  }
}
