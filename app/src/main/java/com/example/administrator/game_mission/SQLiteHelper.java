package com.example.administrator.game_mission;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lake
 * 此类的功能：第三页的缓存数据的库
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "Library";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "custom";

    //创建数据库，里面添加了3个参数，分别是：Msgone VARCHAR类型，30长度当然这了可以自定义
    //Msgtwo VARCHAR(20)   Msgthree VARCHAR(30))  NOT NULL不能为空
    String sql = "CREATE TABLE " + TABLE_NAME
            + "(_id INTEGER PRIMARY KEY,"
            + " Content VARCHAR(100)  NOT NULL)";

    //构造函数，创建数据库
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    //获取游标
    public Cursor select() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    //插入一条记录
    public long insert(String msg1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Content", msg1);
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }

    //根据条件查询
    public Cursor query(String[] args) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE BookName LIKE ?", args);
        return cursor;
    }

    //删除记录
    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "_id = ?";
        String[] whereValue = {Integer.toString(id)};
        db.delete(TABLE_NAME, where, whereValue);
    }

    //更新记录
    public void update(int id, String msg1) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "_id = ?";
        String[] whereValue = {Integer.toString(id)};
        ContentValues cv = new ContentValues();
        cv.put("Content", msg1);
        db.update(TABLE_NAME, cv, where, whereValue);
    }
}
