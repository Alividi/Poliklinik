package com.example.poliklinik.pasien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBDataSource {

    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = {DBHelper.COLUMN_ID, DBHelper.COLUMN_TANGGAL, DBHelper.COLUMN_WAKTU,
            DBHelper.COLUMN_ATAS, DBHelper.COLUMN_BAWAH, DBHelper.COLUMN_KOMENTAR};
    public DBDataSource(Context context){
        dbHelper = new DBHelper(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public void close(){
        dbHelper.close();
    }
    public TekananDarah createTekananDarah(String tanggal, String waktu, String atas, String bawah, String komentar){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TANGGAL, tanggal);
        values.put(DBHelper.COLUMN_WAKTU, waktu);
        values.put(DBHelper.COLUMN_ATAS, atas);
        values.put(DBHelper.COLUMN_BAWAH, bawah);
        values.put(DBHelper.COLUMN_KOMENTAR, komentar);

        long insertId = database.insert(DBHelper.TABLE_NAME, null, values);
        Cursor cursor = database.query(DBHelper.TABLE_NAME, allColumns, DBHelper.COLUMN_ID+" = "+insertId,
                null, null, null, null);
        cursor.moveToFirst();
        TekananDarah newTekananDarah = cursorToTekananDarah(cursor);

        return newTekananDarah;
    }
    private TekananDarah cursorToTekananDarah(Cursor cursor){
        TekananDarah tekananDarah = new TekananDarah();
        tekananDarah.setId(cursor.getLong(0));
        tekananDarah.setTanggal_tekanan_darah(cursor.getString(1));
        tekananDarah.setWaktu_tekanan_darah(cursor.getString(2));
        tekananDarah.setBatas_atas_tekanan(cursor.getString(3));
        tekananDarah.setBatas_bawah_tekanan(cursor.getString(4));
        tekananDarah.setKomentar_tekanan_darah(cursor.getString(5));
        return tekananDarah;
    }
    public ArrayList<TekananDarah> getAllTekananDarah(){
        SQLiteDatabase readDB = dbHelper.getReadableDatabase();

        Cursor cursorTD = readDB.rawQuery("SELECT * FROM " + DBHelper.TABLE_NAME, null);

        ArrayList<TekananDarah> tekananDarahArrayList = new ArrayList<>();
        if (cursorTD.moveToFirst()) {
            do {
                tekananDarahArrayList.add(new TekananDarah(
                        cursorTD.getLong(0),
                        cursorTD.getString(1),
                        cursorTD.getString(2),
                        cursorTD.getString(3),
                        cursorTD.getString(4),
                        cursorTD.getString(5)));
            } while (cursorTD.moveToNext());
        }
        cursorTD.close();
        return tekananDarahArrayList;
    }
    public TekananDarah getTekananDarah(long id){
        TekananDarah TD = new TekananDarah();
        open();
        Cursor cursor = database.query(DBHelper.TABLE_NAME, allColumns, "id ="+id,
                null, null, null, null);
        cursor.moveToFirst();
        TD = cursorToTekananDarah(cursor);
        cursor.close();
        return TD;
    }
    public void UpdateTekananDarah(TekananDarah td){
        String strfilter = "id="+td.getId();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TANGGAL, td.getTanggal_tekanan_darah());
        values.put(DBHelper.COLUMN_WAKTU, td.getWaktu_tekanan_darah());
        values.put(DBHelper.COLUMN_ATAS, td.getBatas_atas_tekanan());
        values.put(DBHelper.COLUMN_BAWAH, td.getBatas_bawah_tekanan());
        values.put(DBHelper.COLUMN_KOMENTAR, td.getKomentar_tekanan_darah());
        database.update(DBHelper.TABLE_NAME, values,strfilter,null);
    }
    public void DeleteTekananDarah(long id){
        String strfilter = "id="+id;
        database.delete(DBHelper.TABLE_NAME,strfilter,null);
    }

}
