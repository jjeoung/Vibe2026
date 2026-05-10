package com.dopaminecat.data.service;

import android.content.Context;
import androidx.core.app.NotificationManagerCompat;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class NotificationHelper_Factory implements Factory<NotificationHelper> {
  private final Provider<Context> contextProvider;

  private final Provider<NotificationManagerCompat> notificationManagerProvider;

  public NotificationHelper_Factory(Provider<Context> contextProvider,
      Provider<NotificationManagerCompat> notificationManagerProvider) {
    this.contextProvider = contextProvider;
    this.notificationManagerProvider = notificationManagerProvider;
  }

  @Override
  public NotificationHelper get() {
    return newInstance(contextProvider.get(), notificationManagerProvider.get());
  }

  public static NotificationHelper_Factory create(Provider<Context> contextProvider,
      Provider<NotificationManagerCompat> notificationManagerProvider) {
    return new NotificationHelper_Factory(contextProvider, notificationManagerProvider);
  }

  public static NotificationHelper newInstance(Context context,
      NotificationManagerCompat notificationManager) {
    return new NotificationHelper(context, notificationManager);
  }
}
