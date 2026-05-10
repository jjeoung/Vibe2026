package com.dopaminecat.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.dopaminecat.data.local.dao.CatDao;
import com.dopaminecat.data.local.dao.CatDao_Impl;
import com.dopaminecat.data.local.dao.CoinDao;
import com.dopaminecat.data.local.dao.CoinDao_Impl;
import com.dopaminecat.data.local.dao.GoalDao;
import com.dopaminecat.data.local.dao.GoalDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile CatDao _catDao;

  private volatile GoalDao _goalDao;

  private volatile CoinDao _coinDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `cat` (`id` INTEGER NOT NULL, `happiness` INTEGER NOT NULL, `trashCount` INTEGER NOT NULL, `lastUpdatedAt` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `goal` (`packageName` TEXT NOT NULL, `appName` TEXT NOT NULL, `dailyLimitMinutes` INTEGER NOT NULL, `isActive` INTEGER NOT NULL, PRIMARY KEY(`packageName`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `coin_transaction` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `amount` INTEGER NOT NULL, `reason` TEXT NOT NULL, `createdAt` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '18d002557f65047bc76d90113c68fdc7')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `cat`");
        db.execSQL("DROP TABLE IF EXISTS `goal`");
        db.execSQL("DROP TABLE IF EXISTS `coin_transaction`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsCat = new HashMap<String, TableInfo.Column>(4);
        _columnsCat.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCat.put("happiness", new TableInfo.Column("happiness", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCat.put("trashCount", new TableInfo.Column("trashCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCat.put("lastUpdatedAt", new TableInfo.Column("lastUpdatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCat = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCat = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCat = new TableInfo("cat", _columnsCat, _foreignKeysCat, _indicesCat);
        final TableInfo _existingCat = TableInfo.read(db, "cat");
        if (!_infoCat.equals(_existingCat)) {
          return new RoomOpenHelper.ValidationResult(false, "cat(com.dopaminecat.data.local.entity.CatEntity).\n"
                  + " Expected:\n" + _infoCat + "\n"
                  + " Found:\n" + _existingCat);
        }
        final HashMap<String, TableInfo.Column> _columnsGoal = new HashMap<String, TableInfo.Column>(4);
        _columnsGoal.put("packageName", new TableInfo.Column("packageName", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGoal.put("appName", new TableInfo.Column("appName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGoal.put("dailyLimitMinutes", new TableInfo.Column("dailyLimitMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGoal.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGoal = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGoal = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGoal = new TableInfo("goal", _columnsGoal, _foreignKeysGoal, _indicesGoal);
        final TableInfo _existingGoal = TableInfo.read(db, "goal");
        if (!_infoGoal.equals(_existingGoal)) {
          return new RoomOpenHelper.ValidationResult(false, "goal(com.dopaminecat.data.local.entity.GoalEntity).\n"
                  + " Expected:\n" + _infoGoal + "\n"
                  + " Found:\n" + _existingGoal);
        }
        final HashMap<String, TableInfo.Column> _columnsCoinTransaction = new HashMap<String, TableInfo.Column>(4);
        _columnsCoinTransaction.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCoinTransaction.put("amount", new TableInfo.Column("amount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCoinTransaction.put("reason", new TableInfo.Column("reason", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCoinTransaction.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCoinTransaction = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCoinTransaction = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCoinTransaction = new TableInfo("coin_transaction", _columnsCoinTransaction, _foreignKeysCoinTransaction, _indicesCoinTransaction);
        final TableInfo _existingCoinTransaction = TableInfo.read(db, "coin_transaction");
        if (!_infoCoinTransaction.equals(_existingCoinTransaction)) {
          return new RoomOpenHelper.ValidationResult(false, "coin_transaction(com.dopaminecat.data.local.entity.CoinTransactionEntity).\n"
                  + " Expected:\n" + _infoCoinTransaction + "\n"
                  + " Found:\n" + _existingCoinTransaction);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "18d002557f65047bc76d90113c68fdc7", "460b40163f3284f8f5134af3a5274142");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "cat","goal","coin_transaction");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `cat`");
      _db.execSQL("DELETE FROM `goal`");
      _db.execSQL("DELETE FROM `coin_transaction`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(CatDao.class, CatDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(GoalDao.class, GoalDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CoinDao.class, CoinDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public CatDao catDao() {
    if (_catDao != null) {
      return _catDao;
    } else {
      synchronized(this) {
        if(_catDao == null) {
          _catDao = new CatDao_Impl(this);
        }
        return _catDao;
      }
    }
  }

  @Override
  public GoalDao goalDao() {
    if (_goalDao != null) {
      return _goalDao;
    } else {
      synchronized(this) {
        if(_goalDao == null) {
          _goalDao = new GoalDao_Impl(this);
        }
        return _goalDao;
      }
    }
  }

  @Override
  public CoinDao coinDao() {
    if (_coinDao != null) {
      return _coinDao;
    } else {
      synchronized(this) {
        if(_coinDao == null) {
          _coinDao = new CoinDao_Impl(this);
        }
        return _coinDao;
      }
    }
  }
}
