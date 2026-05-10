package com.dopaminecat.data.repository;

import com.dopaminecat.data.local.dao.CoinDao;
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
public final class CoinRepositoryImpl_Factory implements Factory<CoinRepositoryImpl> {
  private final Provider<CoinDao> coinDaoProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public CoinRepositoryImpl_Factory(Provider<CoinDao> coinDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.coinDaoProvider = coinDaoProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public CoinRepositoryImpl get() {
    return newInstance(coinDaoProvider.get(), ioDispatcherProvider.get());
  }

  public static CoinRepositoryImpl_Factory create(Provider<CoinDao> coinDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new CoinRepositoryImpl_Factory(coinDaoProvider, ioDispatcherProvider);
  }

  public static CoinRepositoryImpl newInstance(CoinDao coinDao, CoroutineDispatcher ioDispatcher) {
    return new CoinRepositoryImpl(coinDao, ioDispatcher);
  }
}
