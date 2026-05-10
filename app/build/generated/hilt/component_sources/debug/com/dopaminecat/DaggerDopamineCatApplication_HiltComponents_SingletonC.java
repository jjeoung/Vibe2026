package com.dopaminecat;

import android.app.Activity;
import android.app.Service;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.view.View;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.hilt.work.WorkerAssistedFactory;
import androidx.hilt.work.WorkerFactoryModule_ProvideFactoryFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.dopaminecat.data.local.AppDatabase;
import com.dopaminecat.data.local.dao.CatDao;
import com.dopaminecat.data.local.dao.CoinDao;
import com.dopaminecat.data.local.dao.GoalDao;
import com.dopaminecat.data.receiver.BootReceiver;
import com.dopaminecat.data.receiver.BootReceiver_MembersInjector;
import com.dopaminecat.data.repository.CatRepositoryImpl;
import com.dopaminecat.data.repository.CoinRepositoryImpl;
import com.dopaminecat.data.repository.GoalRepositoryImpl;
import com.dopaminecat.data.repository.UsageRepositoryImpl;
import com.dopaminecat.data.service.NotificationHelper;
import com.dopaminecat.data.service.UsageTrackingService;
import com.dopaminecat.data.service.UsageTrackingService_MembersInjector;
import com.dopaminecat.data.worker.MidnightRewardWorker;
import com.dopaminecat.data.worker.MidnightRewardWorker_AssistedFactory;
import com.dopaminecat.data.worker.WorkScheduler;
import com.dopaminecat.di.DatabaseModule_ProvideAppDatabaseFactory;
import com.dopaminecat.di.DatabaseModule_ProvideCatDaoFactory;
import com.dopaminecat.di.DatabaseModule_ProvideCoinDaoFactory;
import com.dopaminecat.di.DatabaseModule_ProvideGoalDaoFactory;
import com.dopaminecat.di.DispatcherModule_ProvideIoDispatcherFactory;
import com.dopaminecat.di.ServiceModule_ProvideNotificationManagerCompatFactory;
import com.dopaminecat.di.UsageStatsModule_ProvideUsageStatsManagerFactory;
import com.dopaminecat.domain.repository.CatRepository;
import com.dopaminecat.domain.repository.CoinRepository;
import com.dopaminecat.domain.usecase.CheckGoalExceedUseCase;
import com.dopaminecat.domain.usecase.ClaimMidnightRewardUseCase;
import com.dopaminecat.domain.usecase.ObserveCatStateUseCase;
import com.dopaminecat.presentation.MainActivity;
import com.dopaminecat.presentation.home.CatRoomViewModel;
import com.dopaminecat.presentation.home.CatRoomViewModel_HiltModules;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import dagger.internal.SingleCheck;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerDopamineCatApplication_HiltComponents_SingletonC {
  private DaggerDopamineCatApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public DopamineCatApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements DopamineCatApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public DopamineCatApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements DopamineCatApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public DopamineCatApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements DopamineCatApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public DopamineCatApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements DopamineCatApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public DopamineCatApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements DopamineCatApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public DopamineCatApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements DopamineCatApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public DopamineCatApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements DopamineCatApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public DopamineCatApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends DopamineCatApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends DopamineCatApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends DopamineCatApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends DopamineCatApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(Collections.<String, Boolean>singletonMap(LazyClassKeyProvider.com_dopaminecat_presentation_home_CatRoomViewModel, CatRoomViewModel_HiltModules.KeyModule.provide()));
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_dopaminecat_presentation_home_CatRoomViewModel = "com.dopaminecat.presentation.home.CatRoomViewModel";

      @KeepFieldType
      CatRoomViewModel com_dopaminecat_presentation_home_CatRoomViewModel2;
    }
  }

  private static final class ViewModelCImpl extends DopamineCatApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<CatRoomViewModel> catRoomViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private ObserveCatStateUseCase observeCatStateUseCase() {
      return new ObserveCatStateUseCase(singletonCImpl.catRepositoryImplProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.catRoomViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(Collections.<String, javax.inject.Provider<ViewModel>>singletonMap(LazyClassKeyProvider.com_dopaminecat_presentation_home_CatRoomViewModel, ((Provider) catRoomViewModelProvider)));
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_dopaminecat_presentation_home_CatRoomViewModel = "com.dopaminecat.presentation.home.CatRoomViewModel";

      @KeepFieldType
      CatRoomViewModel com_dopaminecat_presentation_home_CatRoomViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.dopaminecat.presentation.home.CatRoomViewModel 
          return (T) new CatRoomViewModel(singletonCImpl.catRepositoryImplProvider.get(), singletonCImpl.goalRepositoryImplProvider.get(), singletonCImpl.coinRepositoryImplProvider.get(), singletonCImpl.usageRepositoryImplProvider.get(), viewModelCImpl.observeCatStateUseCase(), DispatcherModule_ProvideIoDispatcherFactory.provideIoDispatcher());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends DopamineCatApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends DopamineCatApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    private CheckGoalExceedUseCase checkGoalExceedUseCase() {
      return new CheckGoalExceedUseCase(singletonCImpl.goalRepositoryImplProvider.get(), singletonCImpl.usageRepositoryImplProvider.get(), singletonCImpl.catRepositoryImplProvider.get());
    }

    @Override
    public void injectUsageTrackingService(UsageTrackingService usageTrackingService) {
      injectUsageTrackingService2(usageTrackingService);
    }

    private UsageTrackingService injectUsageTrackingService2(UsageTrackingService instance) {
      UsageTrackingService_MembersInjector.injectCheckGoalExceedUseCase(instance, checkGoalExceedUseCase());
      UsageTrackingService_MembersInjector.injectNotificationHelper(instance, singletonCImpl.notificationHelperProvider.get());
      return instance;
    }
  }

  private static final class SingletonCImpl extends DopamineCatApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<AppDatabase> provideAppDatabaseProvider;

    private Provider<GoalRepositoryImpl> goalRepositoryImplProvider;

    private Provider<UsageStatsManager> provideUsageStatsManagerProvider;

    private Provider<UsageRepositoryImpl> usageRepositoryImplProvider;

    private Provider<CatRepositoryImpl> catRepositoryImplProvider;

    private Provider<CoinRepositoryImpl> coinRepositoryImplProvider;

    private Provider<WorkScheduler> workSchedulerProvider;

    private Provider<NotificationManagerCompat> provideNotificationManagerCompatProvider;

    private Provider<NotificationHelper> notificationHelperProvider;

    private Provider<MidnightRewardWorker_AssistedFactory> midnightRewardWorker_AssistedFactoryProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private GoalDao goalDao() {
      return DatabaseModule_ProvideGoalDaoFactory.provideGoalDao(provideAppDatabaseProvider.get());
    }

    private CatDao catDao() {
      return DatabaseModule_ProvideCatDaoFactory.provideCatDao(provideAppDatabaseProvider.get());
    }

    private CoinDao coinDao() {
      return DatabaseModule_ProvideCoinDaoFactory.provideCoinDao(provideAppDatabaseProvider.get());
    }

    private ClaimMidnightRewardUseCase claimMidnightRewardUseCase() {
      return new ClaimMidnightRewardUseCase(goalRepositoryImplProvider.get(), usageRepositoryImplProvider.get(), catRepositoryImplProvider.get(), coinRepositoryImplProvider.get());
    }

    private Map<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>> mapOfStringAndProviderOfWorkerAssistedFactoryOf(
        ) {
      return Collections.<String, javax.inject.Provider<WorkerAssistedFactory<? extends ListenableWorker>>>singletonMap("com.dopaminecat.data.worker.MidnightRewardWorker", ((Provider) midnightRewardWorker_AssistedFactoryProvider));
    }

    private HiltWorkerFactory hiltWorkerFactory() {
      return WorkerFactoryModule_ProvideFactoryFactory.provideFactory(mapOfStringAndProviderOfWorkerAssistedFactoryOf());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideAppDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 2));
      this.goalRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<GoalRepositoryImpl>(singletonCImpl, 1));
      this.provideUsageStatsManagerProvider = DoubleCheck.provider(new SwitchingProvider<UsageStatsManager>(singletonCImpl, 4));
      this.usageRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<UsageRepositoryImpl>(singletonCImpl, 3));
      this.catRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<CatRepositoryImpl>(singletonCImpl, 5));
      this.coinRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<CoinRepositoryImpl>(singletonCImpl, 6));
      this.workSchedulerProvider = DoubleCheck.provider(new SwitchingProvider<WorkScheduler>(singletonCImpl, 7));
      this.provideNotificationManagerCompatProvider = DoubleCheck.provider(new SwitchingProvider<NotificationManagerCompat>(singletonCImpl, 9));
      this.notificationHelperProvider = DoubleCheck.provider(new SwitchingProvider<NotificationHelper>(singletonCImpl, 8));
      this.midnightRewardWorker_AssistedFactoryProvider = SingleCheck.provider(new SwitchingProvider<MidnightRewardWorker_AssistedFactory>(singletonCImpl, 0));
    }

    @Override
    public void injectDopamineCatApplication(DopamineCatApplication dopamineCatApplication) {
      injectDopamineCatApplication2(dopamineCatApplication);
    }

    @Override
    public void injectBootReceiver(BootReceiver bootReceiver) {
      injectBootReceiver2(bootReceiver);
    }

    @Override
    public CatRepository catRepository() {
      return catRepositoryImplProvider.get();
    }

    @Override
    public CoinRepository coinRepository() {
      return coinRepositoryImplProvider.get();
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private DopamineCatApplication injectDopamineCatApplication2(DopamineCatApplication instance) {
      DopamineCatApplication_MembersInjector.injectWorkerFactory(instance, hiltWorkerFactory());
      DopamineCatApplication_MembersInjector.injectNotificationHelper(instance, notificationHelperProvider.get());
      DopamineCatApplication_MembersInjector.injectWorkScheduler(instance, workSchedulerProvider.get());
      return instance;
    }

    private BootReceiver injectBootReceiver2(BootReceiver instance) {
      BootReceiver_MembersInjector.injectWorkScheduler(instance, workSchedulerProvider.get());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.dopaminecat.data.worker.MidnightRewardWorker_AssistedFactory 
          return (T) new MidnightRewardWorker_AssistedFactory() {
            @Override
            public MidnightRewardWorker create(Context context, WorkerParameters params) {
              return new MidnightRewardWorker(context, params, singletonCImpl.claimMidnightRewardUseCase(), singletonCImpl.workSchedulerProvider.get(), singletonCImpl.notificationHelperProvider.get());
            }
          };

          case 1: // com.dopaminecat.data.repository.GoalRepositoryImpl 
          return (T) new GoalRepositoryImpl(singletonCImpl.goalDao(), DispatcherModule_ProvideIoDispatcherFactory.provideIoDispatcher());

          case 2: // com.dopaminecat.data.local.AppDatabase 
          return (T) DatabaseModule_ProvideAppDatabaseFactory.provideAppDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 3: // com.dopaminecat.data.repository.UsageRepositoryImpl 
          return (T) new UsageRepositoryImpl(singletonCImpl.provideUsageStatsManagerProvider.get(), DispatcherModule_ProvideIoDispatcherFactory.provideIoDispatcher());

          case 4: // android.app.usage.UsageStatsManager 
          return (T) UsageStatsModule_ProvideUsageStatsManagerFactory.provideUsageStatsManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // com.dopaminecat.data.repository.CatRepositoryImpl 
          return (T) new CatRepositoryImpl(singletonCImpl.catDao(), DispatcherModule_ProvideIoDispatcherFactory.provideIoDispatcher());

          case 6: // com.dopaminecat.data.repository.CoinRepositoryImpl 
          return (T) new CoinRepositoryImpl(singletonCImpl.coinDao(), DispatcherModule_ProvideIoDispatcherFactory.provideIoDispatcher());

          case 7: // com.dopaminecat.data.worker.WorkScheduler 
          return (T) new WorkScheduler(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 8: // com.dopaminecat.data.service.NotificationHelper 
          return (T) new NotificationHelper(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideNotificationManagerCompatProvider.get());

          case 9: // androidx.core.app.NotificationManagerCompat 
          return (T) ServiceModule_ProvideNotificationManagerCompatFactory.provideNotificationManagerCompat(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
