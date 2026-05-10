package com.dopaminecat.domain.usecase;

import com.dopaminecat.domain.repository.CatRepository;
import com.dopaminecat.domain.repository.CoinRepository;
import com.dopaminecat.domain.repository.GoalRepository;
import com.dopaminecat.domain.repository.UsageRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class ClaimMidnightRewardUseCase_Factory implements Factory<ClaimMidnightRewardUseCase> {
  private final Provider<GoalRepository> goalRepositoryProvider;

  private final Provider<UsageRepository> usageRepositoryProvider;

  private final Provider<CatRepository> catRepositoryProvider;

  private final Provider<CoinRepository> coinRepositoryProvider;

  public ClaimMidnightRewardUseCase_Factory(Provider<GoalRepository> goalRepositoryProvider,
      Provider<UsageRepository> usageRepositoryProvider,
      Provider<CatRepository> catRepositoryProvider,
      Provider<CoinRepository> coinRepositoryProvider) {
    this.goalRepositoryProvider = goalRepositoryProvider;
    this.usageRepositoryProvider = usageRepositoryProvider;
    this.catRepositoryProvider = catRepositoryProvider;
    this.coinRepositoryProvider = coinRepositoryProvider;
  }

  @Override
  public ClaimMidnightRewardUseCase get() {
    return newInstance(goalRepositoryProvider.get(), usageRepositoryProvider.get(), catRepositoryProvider.get(), coinRepositoryProvider.get());
  }

  public static ClaimMidnightRewardUseCase_Factory create(
      Provider<GoalRepository> goalRepositoryProvider,
      Provider<UsageRepository> usageRepositoryProvider,
      Provider<CatRepository> catRepositoryProvider,
      Provider<CoinRepository> coinRepositoryProvider) {
    return new ClaimMidnightRewardUseCase_Factory(goalRepositoryProvider, usageRepositoryProvider, catRepositoryProvider, coinRepositoryProvider);
  }

  public static ClaimMidnightRewardUseCase newInstance(GoalRepository goalRepository,
      UsageRepository usageRepository, CatRepository catRepository, CoinRepository coinRepository) {
    return new ClaimMidnightRewardUseCase(goalRepository, usageRepository, catRepository, coinRepository);
  }
}
