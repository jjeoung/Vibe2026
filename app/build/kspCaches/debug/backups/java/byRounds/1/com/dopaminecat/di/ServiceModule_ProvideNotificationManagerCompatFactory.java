package com.dopaminecat.di;

import android.content.Context;
import androidx.core.app.NotificationManagerCompat;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class ServiceModule_ProvideNotificationManagerCompatFactory implements Factory<NotificationManagerCompat> {
  private final Provider<Context> contextProvider;

  public ServiceModule_ProvideNotificationManagerCompatFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public NotificationManagerCompat get() {
    return provideNotificationManagerCompat(contextProvider.get());
  }

  public static ServiceModule_ProvideNotificationManagerCompatFactory create(
      Provider<Context> contextProvider) {
    return new ServiceModule_ProvideNotificationManagerCompatFactory(contextProvider);
  }

  public static NotificationManagerCompat provideNotificationManagerCompat(Context context) {
    return Preconditions.checkNotNullFromProvides(ServiceModule.INSTANCE.provideNotificationManagerCompat(context));
  }
}
