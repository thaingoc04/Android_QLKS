package com.example.qlks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Sqlite_De02 extends SQLiteOpenHelper {
    public static final String tableName = "MinhAnh_0909";

    public static final String Id = "Id";
    public static final String Name = "Name";
    public static final String RoomId = "RoomId";
    public static final String Day = "Day";
    public static final String Price = "Price";

    public Sqlite_De02(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "Create table if not exists " + tableName + "("
                +Id +" Integer primary key autoincrement, "
                +Name +" Text,"
                +RoomId +" Integer,"
                +Price +" Integer,"
                +Day +" Integer)";
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists "+ tableName);
        onCreate(db);
    }
    public void addContact(MinhAnh_0909 item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Name, item.getName());
        values.put(RoomId, item.getRoomId());
        values.put(Price, item.getPrice());
        values.put(Day, item.getDay());
        db.insert(tableName, null, values);
        db.close();
    }
    public void updateContact(int id, MinhAnh_0909 item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Id, item.getId());
        values.put(Name, item.getName());
        values.put(RoomId, item.getRoomId());
        values.put(Price, item.getPrice());
        values.put(Day, item.getDay());
        db.update(tableName, values, Id +"=?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Delete From "+ tableName +" Where Id= " +Id;
        db.execSQL(sql);
        db.close();
    }
    public ArrayList<MinhAnh_0909> getAllContact(){
        ArrayList<MinhAnh_0909> list = new ArrayList<>();
        String sql = "Select * from "+tableName +" Order By RoomId DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor != null){
            while (cursor.moveToNext()){
                MinhAnh_0909 item = new MinhAnh_0909(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                        cursor.getInt(3), cursor.getInt(4) );
                list.add(item);
            }
        }
        return list;
    }

    public ArrayList<MinhAnh_0909> getContactBySearch(String s){
        if(s.equals("")){
            ArrayList<MinhAnh_0909> list = getAllContact();
            return list;
        }
        int money = Integer.parseInt(s);
        ArrayList<MinhAnh_0909> list = new ArrayList<>();
        String sql = "Select * from "+ tableName + " Where Price*Day >" + money+ " Order by RoomId DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor != null){
            while (cursor.moveToNext()){
                MinhAnh_0909 item = new MinhAnh_0909(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                        cursor.getInt(3), cursor.getInt(4) );
                list.add(item);
            }
        }
        return list;
    }

}
