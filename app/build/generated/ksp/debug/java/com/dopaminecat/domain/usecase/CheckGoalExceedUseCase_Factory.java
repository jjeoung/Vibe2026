package com.dopaminecat.domain.usecase;

import com.dopaminecat.domain.repository.CatRepository;
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
public final class CheckGoalExceedUseCase_Factory implements Factory<CheckGoalExceedUseCase> {
  private final Provider<GoalRepository> goalRepositoryProvider;

  private final Provider<UsageRepository> usageRepositoryProvider;

  private final Provider<CatRepository> catRepositoryProvider;

  public CheckGoalExceedUseCase_Factory(Provider<GoalRepository> goalRepositoryProvider,
      Provider<UsageRepository> usageRepositoryProvider,
      Provider<CatRepository> catRepositoryProvider) {
    this.goalRepositoryProvider = goalRepositoryProvider;
    this.usageRepositoryProvider = usageRepositoryProvider;
    this.catRepositoryProvider = catRepositoryProvider;
  }

  @Override
  public CheckGoalExceedUseCase get() {
    return newInstance(goalRepositoryProvider.get(), usageRepositoryProvider.get(), catRepositoryProvider.get());
  }

  public static CheckGoalExceedUseCase_Factory create(
      Provider<GoalRepository> goalRepositoryProvider,
      Provider<UsageRepository> usageRepositoryProvider,
      Provider<CatRepository> catRepositoryProvider) {
    return new CheckGoalExceedUseCase_Factory(goalRepositoryProvider, usageRepositoryProvider, catRepositoryProvider);
  }

  public static CheckGoalExceedUseCase newInstance(GoalRepository goalRepository,
      UsageRepository usageRepository, CatRepository catRepository) {
    return new CheckGoalExceedUseCase(goalRepository, usageRepository, catRepository);
  }
}
