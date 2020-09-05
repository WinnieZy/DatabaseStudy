package com.github.winniezy.database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.github.winniezy.database.bean.User;
import com.github.winniezy.database.db.BaseDao;
import com.github.winniezy.database.db.BaseDaoFactory;

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
                baseDao.insert(new User(1, "winnie", "123"));
            }
        });
    }
}