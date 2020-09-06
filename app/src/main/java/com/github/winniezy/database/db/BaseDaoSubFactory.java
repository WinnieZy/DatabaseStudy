package com.github.winniezy.database.db;

import android.database.sqlite.SQLiteDatabase;

public class BaseDaoSubFactory extends BaseDaoFactory {

    // 定义一个用户实现数据库分库的对象
    protected SQLiteDatabase subSqLiteDatabase;

    private static class Holder {
        private static final BaseDaoSubFactory INSTANCE = new BaseDaoSubFactory();
    }

    public static final BaseDaoSubFactory getInstance(){
        return Holder.INSTANCE;
    }

    // 根据所需BaseDao子类类型生产BaseDao对象
    public <T extends BaseDao<M>, M> T getBaseDao(Class<T> daoClass, Class<M> entityClass){
        BaseDao baseDao = null;
        if (PrivateDatabaseEnums.database.getValue() == null){
            return null;
        }
        if (map.get(PrivateDatabaseEnums.database.getValue()) != null){
            return (T) map.get(PrivateDatabaseEnums.database.getValue());
        }
        subSqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(PrivateDatabaseEnums.database.getValue(),null);
        try {
            baseDao = daoClass.newInstance();
            baseDao.init(subSqLiteDatabase, entityClass);
            map.put(PrivateDatabaseEnums.database.getValue(), baseDao);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return (T) baseDao;
    }
}
