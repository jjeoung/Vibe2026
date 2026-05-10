package com.dopaminecat.presentation.home;

import com.dopaminecat.domain.repository.CatRepository;
import com.dopaminecat.domain.repository.CoinRepository;
import com.dopaminecat.domain.repository.GoalRepository;
import com.dopaminecat.domain.repository.UsageRepository;
import com.dopaminecat.domain.usecase.ObserveCatStateUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

@ScopeMetadata
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
public final class CatRoomViewModel_Factory implements Factory<CatRoomViewModel> {
  private final Provider<CatRepository> catRepositoryProvider;

  private final Provider<GoalRepository> goalRepositoryProvider;

  private final Provider<CoinRepository> coinRepositoryProvider;

  private final Provider<UsageRepository> usageRepositoryProvider;

  private final Provider<ObserveCatStateUseCase> observeCatStateUseCaseProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public CatRoomViewModel_Factory(Provider<CatRepository> catRepositoryProvider,
      Provider<GoalRepository> goalRepositoryProvider,
      Provider<CoinRepository> coinRepositoryProvider,
      Provider<UsageRepository> usageRepositoryProvider,
      Provider<ObserveCatStateUseCase> observeCatStateUseCaseProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.catRepositoryProvider = catRepositoryProvider;
    this.goalRepositoryProvider = goalRepositoryProvider;
    this.coinRepositoryProvider = coinRepositoryProvider;
    this.usageRepositoryProvider = usageRepositoryProvider;
    this.observeCatStateUseCaseProvider = observeCatStateUseCaseProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public CatRoomViewModel get() {
    return newInstance(catRepositoryProvider.get(), goalRepositoryProvider.get(), coinRepositoryProvider.get(), usageRepositoryProvider.get(), observeCatStateUseCaseProvider.get(), ioDispatcherProvider.get());
  }

  public static CatRoomViewModel_Factory create(Provider<CatRepository> catRepositoryProvider,
      Provider<GoalRepository> goalRepositoryProvider,
      Provider<CoinRepository> coinRepositoryProvider,
      Provider<UsageRepository> usageRepositoryProvider,
      Provider<ObserveCatStateUseCase> observeCatStateUseCaseProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new CatRoomViewModel_Factory(catRepositoryProvider, goalRepositoryProvider, coinRepositoryProvider, usageRepositoryProvider, observeCatStateUseCaseProvider, ioDispatcherProvider);
  }

  public static CatRoomViewModel newInstance(CatRepository catRepository,
      GoalRepository goalRepository, CoinRepository coinRepository, UsageRepository usageRepository,
      ObserveCatStateUseCase observeCatStateUseCase, CoroutineDispatcher ioDispatcher) {
    return new CatRoomViewModel(catRepository, goalRepository, coinRepository, usageRepository, observeCatStateUseCase, ioDispatcher);
  }
}
