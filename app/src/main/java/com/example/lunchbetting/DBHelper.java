package com.example.lunchbetting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper { // 추상메소드라 업그레이드 오버라이드가 필요
    public DBHelper(Context context) {
        super(context, "myDB", null, 1);
    }
    SQLiteDatabase sqLiteDatabase;
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table customers (name text)"); // SQL 테이블 생성(commit) 함수 //text: 자료형(string과 유사)
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    // 고객 정보 저장하는 메소드 정의하기(레코드 추가하기)
    public boolean insertData(String name){
        // 낱개 하나 하나씩 저장하는 것은 비효율이기 때문에 묶어서(object화 시켜서) 저장
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        // sqlLiteDatabase 쓰기 모드상태로 변경
        // sqLiteDatabase 적용 메소드 사용
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.insert("customers",null,contentValues);
        return true;
    }
    // 조회하기 메소드
    public Cursor selectAll(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from customers",null);
        return cursor;
    }
    // 이름으로 조회하기 메소드
    public Cursor selectData(String name){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from customers where name = '"+name+"'",null);
        // sql에서 값을 표시할 때는 ''를 사용한다.
        return cursor;
    }
    // 수정하기 메소드
    public	boolean	updateData(String newName, String name)
    {
        SQLiteDatabase	sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",	newName);
        sqLiteDatabase.update("customers",contentValues,"name=?",new	String[]{name});
        // contentValues = 저장할 object, ? 값은 newString[]{name}이 들어감
        return	true;
    }
    //	일괄삭제하기 메소드
    public	boolean deleteData(String	name) {
        SQLiteDatabase	sqLiteDatabase	=	getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE	FROM customers");
        return	true;
    }
}
