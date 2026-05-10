package com.dopaminecat.di;

import com.dopaminecat.data.local.AppDatabase;
import com.dopaminecat.data.local.dao.CatDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideCatDaoFactory implements Factory<CatDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideCatDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CatDao get() {
    return provideCatDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideCatDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideCatDaoFactory(dbProvider);
  }

  public static CatDao provideCatDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCatDao(db));
  }
}
