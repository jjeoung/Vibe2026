package com.dopaminecat.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.dopaminecat.data.local.entity.CatEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CatDao_Impl implements CatDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CatEntity> __insertionAdapterOfCatEntity;

  public CatDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCatEntity = new EntityInsertionAdapter<CatEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `cat` (`id`,`happiness`,`trashCount`,`lastUpdatedAt`) VALUES (?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CatEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getHappiness());
        statement.bindLong(3, entity.getTrashCount());
        statement.bindLong(4, entity.getLastUpdatedAt());
      }
    };
  }

  @Override
  public Object upsert(final CatEntity cat, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfCatEntity.insert(cat);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<CatEntity> getCatStream() {
    final String _sql = "SELECT `cat`.`id` AS `id`, `cat`.`happiness` AS `happiness`, `cat`.`trashCount` AS `trashCount`, `cat`.`lastUpdatedAt` AS `lastUpdatedAt` FROM cat WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"cat"}, new Callable<CatEntity>() {
      @Override
      @Nullable
      public CatEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfHappiness = 1;
          final int _cursorIndexOfTrashCount = 2;
          final int _cursorIndexOfLastUpdatedAt = 3;
          final CatEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpHappiness;
            _tmpHappiness = _cursor.getInt(_cursorIndexOfHappiness);
            final int _tmpTrashCount;
            _tmpTrashCount = _cursor.getInt(_cursorIndexOfTrashCount);
            final long _tmpLastUpdatedAt;
            _tmpLastUpdatedAt = _cursor.getLong(_cursorIndexOfLastUpdatedAt);
            _result = new CatEntity(_tmpId,_tmpHappiness,_tmpTrashCount,_tmpLastUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getCat(final Continuation<? super CatEntity> $completion) {
    final String _sql = "SELECT `cat`.`id` AS `id`, `cat`.`happiness` AS `happiness`, `cat`.`trashCount` AS `trashCount`, `cat`.`lastUpdatedAt` AS `lastUpdatedAt` FROM cat WHERE id = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<CatEntity>() {
      @Override
      @Nullable
      public CatEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfHappiness = 1;
          final int _cursorIndexOfTrashCount = 2;
          final int _cursorIndexOfLastUpdatedAt = 3;
          final CatEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpHappiness;
            _tmpHappiness = _cursor.getInt(_cursorIndexOfHappiness);
            final int _tmpTrashCount;
            _tmpTrashCount = _cursor.getInt(_cursorIndexOfTrashCount);
            final long _tmpLastUpdatedAt;
            _tmpLastUpdatedAt = _cursor.getLong(_cursorIndexOfLastUpdatedAt);
            _result = new CatEntity(_tmpId,_tmpHappiness,_tmpTrashCount,_tmpLastUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
