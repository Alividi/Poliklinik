package com.example.poliklinik.pasien;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
public class DBHelper extends SQLiteOpenHelper {
    public static  final  String TABLE_NAME = "tbl_tekanan_darah";
    public static  final  String COLUMN_ID = "id";
    public static  final  String COLUMN_TANGGAL ="tanggal_tekanan_darah";
    public static  final  String COLUMN_WAKTU = "waktu_tekanan_darah";
    public static  final  String COLUMN_ATAS ="batas_atas_tekanan";
    public static  final  String COLUMN_BAWAH ="batas_bawah_tekanan";
    public static  final  String COLUMN_KOMENTAR ="komentar_tekanan_darah";
    private static final String db_name = "tekanandarah.db";
    private static final int db_version = 1;
    private static final String db_create = "create table " +
            TABLE_NAME + "(" +
            COLUMN_ID   + " integer primary key autoincrement, "+
            COLUMN_TANGGAL + " varchar(50) not null, "+
            COLUMN_WAKTU + " varchar(50) not null, "+
            COLUMN_ATAS + " varchar(50) not null, "+
            COLUMN_BAWAH + " varchar(50) not null, "+
            COLUMN_KOMENTAR + " varchar(50) not null);";
    public DBHelper(@Nullable Context context){
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(db_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),"Upgrade db dari versi "+ oldVersion +" ke "+ newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
