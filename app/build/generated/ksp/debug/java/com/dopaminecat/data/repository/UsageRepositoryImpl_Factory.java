package com.dopaminecat.data.repository;

import android.app.usage.UsageStatsManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("com.dopaminecat.di.IoDispatcher")
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
public final class UsageRepositoryImpl_Factory implements Factory<UsageRepositoryImpl> {
  private final Provider<UsageStatsManager> usageStatsManagerProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public UsageRepositoryImpl_Factory(Provider<UsageStatsManager> usageStatsManagerProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.usageStatsManagerProvider = usageStatsManagerProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public UsageRepositoryImpl get() {
    return newInstance(usageStatsManagerProvider.get(), ioDispatcherProvider.get());
  }

  public static UsageRepositoryImpl_Factory create(
      Provider<UsageStatsManager> usageStatsManagerProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new UsageRepositoryImpl_Factory(usageStatsManagerProvider, ioDispatcherProvider);
  }

  public static UsageRepositoryImpl newInstance(UsageStatsManager usageStatsManager,
      CoroutineDispatcher ioDispatcher) {
    return new UsageRepositoryImpl(usageStatsManager, ioDispatcher);
  }
}
