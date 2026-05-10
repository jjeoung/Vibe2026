package com.dopaminecat.domain.usecase;

import com.dopaminecat.domain.repository.CatRepository;
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
public final class ObserveCatStateUseCase_Factory implements Factory<ObserveCatStateUseCase> {
  private final Provider<CatRepository> catRepositoryProvider;

  public ObserveCatStateUseCase_Factory(Provider<CatRepository> catRepositoryProvider) {
    this.catRepositoryProvider = catRepositoryProvider;
  }

  @Override
  public ObserveCatStateUseCase get() {
    return newInstance(catRepositoryProvider.get());
  }

  public static ObserveCatStateUseCase_Factory create(
      Provider<CatRepository> catRepositoryProvider) {
    return new ObserveCatStateUseCase_Factory(catRepositoryProvider);
  }

  public static ObserveCatStateUseCase newInstance(CatRepository catRepository) {
    return new ObserveCatStateUseCase(catRepository);
  }
}
