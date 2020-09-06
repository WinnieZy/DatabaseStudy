package com.github.winniezy.database;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.winniezy.database.bean.Photo;
import com.github.winniezy.database.bean.User;
import com.github.winniezy.database.db.BaseDao;
import com.github.winniezy.database.db.BaseDaoFactory;
import com.github.winniezy.database.db.BaseDaoSubFactory;
import com.github.winniezy.database.db.PhotoDao;
import com.github.winniezy.database.db.PrivateDatabaseEnums;
import com.github.winniezy.database.db.UserDao;
import com.github.winniezy.database.upgrade.UpgradeManager;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int i = 0;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class, User.class);
        initView();
    }

    private void initView() {
        findViewById(R.id.insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
                //没有status字段
                Log.i("winnie", "insert 1 = " + baseDao.insert(new User(1, "winnie1", "123456")));
                Log.i("winnie", "insert 2 = " + baseDao.insert(new User(2, "winnie2", "123456")));
                Log.i("winnie", "insert 3 = " + baseDao.insert(new User(3, "winnie3", "123456")));
                Log.i("winnie", "insert 4 = " + baseDao.insert(new User(4, "winnie4", "123456")));
                Log.i("winnie", "insert 5 = " + baseDao.insert(new User(5, "winnie5", "123456")));
                Log.i("winnie", "insert 6 = " + baseDao.insert(new User(6, "winnie6", "123456")));
            }
        });
        findViewById(R.id.select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
                User where = new User();
                where.setPassword("123456");
                List<User> list = baseDao.query(where);
                Log.i("winnie", "database size = " + list.size());
                for (User user : list) {
                    Log.i("winnie", user.toString());
                }
            }
        });
        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
                User user = new User();
                user.setId(2);
                user.setUsername("winnieyzhou");
                user.setPassword("123456");

                User where = new User();
                where.setId(2);
                baseDao.update(user,where);
            }
        });
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);
                User where = new User();
                where.setPassword("123456");
                baseDao.delete(where);
                // BaseDao类型扩展实例
                // OrderDao orderDao = BaseDaoFactory.getInstance().getBaseDao(OrderDao.class, User.class);
            }
        });
        //模仿登录切换账号状态
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 服务器返回的用户信息
                User user = new User();
                user.setId(i);
                user.setUsername("winnie"+(i++));
                user.setPassword("123456");
                List<User> query = userDao.query(user);
                if (query.size() > 0){
                    userDao.updateStatus(user);
                }else {
                    userDao.insert(user);
                }
            }
        });
        //分库插入
        findViewById(R.id.subInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo photo = new Photo();
                photo.setPath("data/data/xxx.jpg");
                photo.setTime(new Date().toString());
                PhotoDao photoDao = BaseDaoSubFactory.getInstance().getBaseDao(PhotoDao.class, Photo.class);
                photoDao.insert(photo);

                List<Photo> list = photoDao.query(new Photo());
                for (Photo photo1 : list) {
                    Log.i("winnie", photo1.toString());
                }
            }
        });
        //升级
        findViewById(R.id.upgrade).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取得所有的列名
                PhotoDao photoDao = BaseDaoSubFactory.getInstance().getBaseDao(PhotoDao.class, Photo.class);
                String[] columnNames = photoDao.getColumnNames();
                for (String columnName : columnNames) {
                    Log.i("winnie", columnName);
                }
                UpgradeManager upgradeManager = new UpgradeManager();
                upgradeManager.startUpgradeDb(MainActivity.this);
                SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(PrivateDatabaseEnums.database.getValue(), null);
                Cursor cursor = database.rawQuery("select * from tb_photo", null);
                String[] columnNames2 = cursor.getColumnNames();
                for (String columnName : columnNames2) {
                    Log.i("winnie", columnName);
                }
            }
        });
    }
}