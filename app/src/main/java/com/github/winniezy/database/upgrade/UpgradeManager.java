package com.github.winniezy.database.upgrade;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.winniezy.database.bean.User;
import com.github.winniezy.database.db.BaseDaoFactory;
import com.github.winniezy.database.db.UserDao;

import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class UpgradeManager {

    private List<User> userList;
    public void startUpgradeDb(Context context){
        UserDao userDao = BaseDaoFactory.getInstance().getBaseDao(UserDao.class, User.class);
        userList = userDao.query(new User());
        //解析xml文件
        UpgradeXml upgradeXml = readDbXml(context);
        //拿到当前版本信息
        UpgradeStep upgradeStep = analyseUpgradeStep(upgradeXml);
        if (upgradeStep == null) {
            return;
        }
        //获得更新用的对象
        List<UpgradeDb> upgradeDbs = upgradeStep.getUpgradeDbs();
        for (User user : userList) {
            // 得到每个用户的数据库对象
            SQLiteDatabase database = getDb(user.getId());
            if (database == null){
                continue;
            }
            for (UpgradeDb upgradeDb : upgradeDbs) {
                String sql_rename = upgradeDb.getSql_rename();
                String sql_create = upgradeDb.getSql_create();
                String sql_insert = upgradeDb.getSql_insert();
                String sql_delete = upgradeDb.getSql_delete();
                String[] sqls = new String[]{sql_rename, sql_create, sql_insert, sql_delete};
                executeSql(database, sqls);
            }
        }
    }

    private void executeSql(SQLiteDatabase database, String[] sqls) {
        if (sqls == null || sqls.length == 0){
            return;
        }
        database.beginTransaction();
        for (String sql : sqls) {
            sql = sql.replace("\r\n", " ");
            sql = sql.replace("\n", " ");
            if (sql.trim().length() > 0){
                database.execSQL(sql);
            }
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    private SQLiteDatabase getDb(Integer id) {
        File file = new File("data/data/com.github.winniezy.database/u_"+id+"_private.db");
        if (!file.exists()) {
            Log.w("winnie", "数据库不存在");
            return null;
        }
        return SQLiteDatabase.openOrCreateDatabase(file, null);
    }

    private UpgradeStep analyseUpgradeStep(UpgradeXml upgradeXml) {
        UpgradeStep thisStep = null;
        if (upgradeXml == null){
            return null;
        }
        List<UpgradeStep> steps = upgradeXml.getUpgradeSteps();
        if (steps == null || steps.size() == 0){
            return null;
        }
        for (UpgradeStep step : steps) {
            if (step.getVersionFrom() != null && step.getVersionTo() != null){
                String[] versionArray = step.getVersionFrom().split(",");
                if (versionArray != null && versionArray.length > 0){
                    for (int i = 0; i < versionArray.length; i++) {
                        if ("V002".equalsIgnoreCase(versionArray[i]) && "V003".equalsIgnoreCase(step.getVersionTo())){
                            thisStep = step;
                            break;
                        }
                    }
                }
            }
        }
        return thisStep;
    }

    private UpgradeXml readDbXml(Context context) {
        InputStream inputStream = null;
        Document document = null;
        try {
            inputStream = context.getAssets().open("upgradeXml.xml");
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = builder.parse(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (document == null){
                return null;
            }
        }
        UpgradeXml upgradeXml = new UpgradeXml(document);
        return upgradeXml;
    }
}
