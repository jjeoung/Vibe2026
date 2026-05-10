package com.dopaminecat.di;

import com.dopaminecat.data.local.AppDatabase;
import com.dopaminecat.data.local.dao.CoinDao;
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
public final class DatabaseModule_ProvideCoinDaoFactory implements Factory<CoinDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideCoinDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public CoinDao get() {
    return provideCoinDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideCoinDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideCoinDaoFactory(dbProvider);
  }

  public static CoinDao provideCoinDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCoinDao(db));
  }
}
