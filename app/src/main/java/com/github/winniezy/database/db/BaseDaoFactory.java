package com.github.winniezy.database.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BaseDaoFactory {

    private SQLiteDatabase sqLiteDatabase;
    private String sqLitePath;

    // 设计要给数据库的连接池，一次new多次使用，考虑多线程问题
    protected Map<String, BaseDao> map = Collections.synchronizedMap(new HashMap<String, BaseDao>());

    protected BaseDaoFactory(){
        sqLitePath = "data/data/com.github.winniezy.database/winniezy.db";
        sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqLitePath, null);
    }

    private static class Holder{
        private static final BaseDaoFactory INSTANCE = new BaseDaoFactory();
    }
    public static BaseDaoFactory getInstance(){
        return Holder.INSTANCE;
    }

    // 生产BaseDao对象
    public <T> BaseDao<T> getBaseDao(Class<T> entityClass){
        BaseDao baseDao = null;
        try {
            baseDao = BaseDao.class.newInstance();
            baseDao.init(sqLiteDatabase, entityClass);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return baseDao;
    }

    // 根据所需BaseDao子类类型生产BaseDao对象
    public <T extends BaseDao<M>, M> T getBaseDao(Class<T> daoClass, Class<M> entityClass){
        BaseDao baseDao = null;
        if (map.get(daoClass.getSimpleName()) != null){
            return (T) map.get(daoClass.getSimpleName());
        }
        try {
            baseDao = daoClass.newInstance();
            baseDao.init(sqLiteDatabase, entityClass);
            map.put(daoClass.getSimpleName(), baseDao);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (T) baseDao;
    }
}
