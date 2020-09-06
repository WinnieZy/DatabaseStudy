package com.github.winniezy.database.db;

import com.github.winniezy.database.bean.User;

import java.io.File;

// 用来产生私有数据库存放的位置
public enum PrivateDatabaseEnums {

    database("");

    private String value;
    PrivateDatabaseEnums(String value){

    }

    public String getValue(){
        UserDao userDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class, User.class);
        if (userDao != null) {
            User curUser = userDao.getCurrentUser();
            if (curUser != null){
                File file = new File("data/data/com.github.winniezy.database");
                if (!file.exists()){
                    file.mkdir();
                }
                return file.getAbsolutePath()+"/u_"+curUser.getId()+"_private.db";
            }
        }
        return null;
    }
}
