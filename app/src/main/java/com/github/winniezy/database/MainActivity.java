package com.github.winniezy.database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.winniezy.database.bean.User;
import com.github.winniezy.database.db.BaseDao;
import com.github.winniezy.database.db.BaseDaoFactory;
import com.github.winniezy.database.db.OrderDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseDao baseDao = BaseDaoFactory.getInstance().getBaseDao(User.class);

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
                where.setUsername("winnieyzhou");
                // BaseDao类型扩展实例
                // OrderDao orderDao = BaseDaoFactory.getInstance().getBaseDao(OrderDao.class, User.class);
            }
        });
    }
}