package com.guuguo.learnsave.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.guuguo.androidlib.utils.CommonUtil;
import com.guuguo.learnsave.app.MyApplication;
import com.litesuits.orm.BuildConfig;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBaseConfig;
import com.litesuits.orm.db.assit.SQLiteHelper;

import java.io.File;
import java.util.List;

/**
 * author: baiiu
 * date: on 16/5/18 17:43
 * description:
 *
 * 封装数据库,对外提供基础操作
 *
 *
 * LiteOrm支持级联查询,对象关系映射为数据库关系
 */
public enum UOrm implements SQLiteHelper.OnUpdateListener {
  INSTANCE;

  String DB_NAME_PATH = MyApplication.getInstance().getDir("db", Context.MODE_PRIVATE).getAbsolutePath();
  String DB_NAME = "uOrm.db";
  private LiteOrm mLiteOrm;

  UOrm() {
    DataBaseConfig config = new DataBaseConfig(MyApplication.getInstance());
    config.dbName = DB_NAME_PATH + File.separator + DB_NAME;
    config.dbVersion = 1;
    config.onUpdateListener = this;
    config.debugged = BuildConfig.DEBUG;
    mLiteOrm = LiteOrm.newCascadeInstance(config);
  }

  @Override public void onUpdate(SQLiteDatabase sqLiteDatabase, int i, int i1) {

  }
  public UOrm single(){
    mLiteOrm.single();
    return this;
  }
  public UOrm cascade(){
    mLiteOrm.cascade();
    return this;
  }
  public void save(Object o) {
    if (o == null) {
      return;
    }

    mLiteOrm.save(o);
  }

  public <T> void save(List<T> collection) {
    if (CommonUtil.isEmpty(collection)) {
      return;
    }

    mLiteOrm.save(collection);
  }

  public <T> void delete(Class<T> tClass) {
    if (tClass == null) {
      return;
    }

    mLiteOrm.delete(tClass);
  }

  public <T> List<T> queryAll(Class<T> tClass) {
    if (tClass == null) {
      return null;
    }

    return mLiteOrm.query(tClass);
  }

}
