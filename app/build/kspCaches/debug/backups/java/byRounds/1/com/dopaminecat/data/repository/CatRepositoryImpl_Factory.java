package com.dopaminecat.data.repository;

import com.dopaminecat.data.local.dao.CatDao;
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
public final class CatRepositoryImpl_Factory implements Factory<CatRepositoryImpl> {
  private final Provider<CatDao> catDaoProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public CatRepositoryImpl_Factory(Provider<CatDao> catDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.catDaoProvider = catDaoProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public CatRepositoryImpl get() {
    return newInstance(catDaoProvider.get(), ioDispatcherProvider.get());
  }

  public static CatRepositoryImpl_Factory create(Provider<CatDao> catDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new CatRepositoryImpl_Factory(catDaoProvider, ioDispatcherProvider);
  }

  public static CatRepositoryImpl newInstance(CatDao catDao, CoroutineDispatcher ioDispatcher) {
    return new CatRepositoryImpl(catDao, ioDispatcher);
  }
}
