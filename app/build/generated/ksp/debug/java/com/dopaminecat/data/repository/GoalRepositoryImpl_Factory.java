package com.dopaminecat.data.repository;

import com.dopaminecat.data.local.dao.GoalDao;
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
public final class GoalRepositoryImpl_Factory implements Factory<GoalRepositoryImpl> {
  private final Provider<GoalDao> goalDaoProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public GoalRepositoryImpl_Factory(Provider<GoalDao> goalDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.goalDaoProvider = goalDaoProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public GoalRepositoryImpl get() {
    return newInstance(goalDaoProvider.get(), ioDispatcherProvider.get());
  }

  public static GoalRepositoryImpl_Factory create(Provider<GoalDao> goalDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new GoalRepositoryImpl_Factory(goalDaoProvider, ioDispatcherProvider);
  }

  public static GoalRepositoryImpl newInstance(GoalDao goalDao, CoroutineDispatcher ioDispatcher) {
    return new GoalRepositoryImpl(goalDao, ioDispatcher);
  }
}
