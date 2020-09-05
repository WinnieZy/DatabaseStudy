package com.github.winniezy.database.db;

public interface IBaseDao<T> {
    long insert(T entity);
}
