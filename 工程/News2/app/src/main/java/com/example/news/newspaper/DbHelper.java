package com.example.news.newspaper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.news.bean.NewsPaperBean;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "news.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "newspaper";

    private static DbHelper instance;

    public static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME + "("
            + "id integer primary key autoincrement,"
            + "city varchar(20) not null,"
            + "newspaper_name varchar(225) not null,"
            + "newspaper_url varchar(225) not null,"
            + "hot integer not null"
            + ");";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DbHelper getInstance(Context context){
        if (instance == null)
            return new DbHelper(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //插入数据
    public static void insertNewsPaper(Context context, NewsPaperBean.ResultBean bean){
        ContentValues contentValues = new ContentValues();
        contentValues.put("city", bean.getCity());
        contentValues.put("newspaper_name", bean.getNewspaper());
        contentValues.put("newspaper_url", bean.getNewspaper_url());
        contentValues.put("hot", bean.getHot());

        SQLiteDatabase database = DbHelper.getInstance(context).getWritableDatabase();
        database.insert(DbHelper.TABLE_NAME, null, contentValues);
        database.close();
    }

    public static ArrayList<String> queryCity(Context context){
        ArrayList<String> list = new ArrayList<>(0);
        SQLiteDatabase database = DbHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = database.rawQuery("select distinct city from newspaper", null);
        while (cursor.moveToNext()){
            String str = cursor.getString(cursor.getColumnIndex("city"));
            list.add(str);
        }
        cursor.close();
        database.close();
        return list;
    }

    //查询该城市的报纸
    public static ArrayList<String> queryNewsPaper(Context context, String cityname){
        ArrayList<String> list = new ArrayList<>(0);
        SQLiteDatabase database = DbHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(
                "select newspaper_name from newspaper where city = ?",new String[]{cityname});
        while (cursor.moveToNext()){
            String str = cursor.getString(cursor.getColumnIndex("newspaper_name"));
            list.add(str);
        }
        cursor.close();
        database.close();
        return list;
    }

    //查询报纸的地址
    public static String queryUrl(Context context, String newspaper){
        String str = null;
        SQLiteDatabase database = DbHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(
                "select newspaper_url from newspaper where newspaper_name = ?",new String[]{newspaper});
        while (cursor.moveToNext()){
            str = cursor.getString(cursor.getColumnIndex("newspaper_url"));
        }
        cursor.close();
        database.close();
        return str;
    }

    //查询热门报纸
    public static ArrayList<String> queryHot(Context context, String hot){
        ArrayList<String> list = new ArrayList<>(0);
        SQLiteDatabase database = DbHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = database.rawQuery(
                "select newspaper_name from newspaper where hot = ?",new String[]{hot});
        while (cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex("newspaper_name")));
        }
        cursor.close();
        database.close();
        return list;
    }

    //清空表数据
    public static void deleteTable(Context context){
        SQLiteDatabase database = DbHelper.getInstance(context).getWritableDatabase();
        //清空表数据
        database.execSQL("delete from " + TABLE_NAME);
        database.delete(TABLE_NAME, null, null);
//        database.notifyAll();
        Log.d("delete","删除");
        //将自增列置零
        database.execSQL("update sqlite_sequence set seq = 0 where name = " + "'"+ TABLE_NAME + "'");
        database.execSQL("delete from sqlite_sequence where name = " + "'"+ TABLE_NAME + "'");
        database.execSQL("delete from sqlite_sequence");
        database.close();
    }
}
