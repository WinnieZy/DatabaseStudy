package com.github.winniezy.database.db;

import android.util.Log;

import com.github.winniezy.database.bean.User;

import java.util.List;

// 维护用户的共有数据
public class UserDao extends BaseDao<User>{

    @Override
    public long insert(User entity) {
        List<User> list = query(new User());
        User where = null;
        for (User user : list) {
            where = new User();
            where.setId(user.getId());
            user.setStatus(0);
            update(user,where);
            Log.i("winnie",user.getUsername()+" insert set status=0");
        }
        entity.setStatus(1);
        Log.i("winnie",entity.getUsername()+" insert set status=1");
        return super.insert(entity);
    }

    public long updateStatus(User entity) {
        List<User> list = query(new User());
        User allWhere = null;
        for (User user : list) {
            allWhere = new User();
            allWhere.setId(user.getId());
            user.setStatus(0);
            super.update(user,allWhere);
            Log.i("winnie",user.getUsername()+" update set status=0");
        }
        User where = new User();
        where.setId(entity.getId());
        where.setUsername(entity.getUsername());
        where.setPassword(entity.getPassword());
        where.setStatus(entity.getStatus());
        entity.setStatus(1);
        Log.i("winnie",entity.getUsername()+" update set status=1");
        return super.update(entity, where);
    }

    // 获取当前用户
    public User getCurrentUser(){
        User user = new User();
        user.setStatus(1);
        List<User> list = query(user);
        if (list.size() > 0){
            return list.get(0);
        }
        return null;
    }
}
