package com.github.winniezy.database.bean;

import com.github.winniezy.database.annotation.DbTable;

@DbTable("tb_photo")
public class Photo {

    private String time;
    private String path;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "time='" + time + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
