package com.example.project_solo_72dcht21.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class DataBase  {
    public void taoDataBase(SQLiteDatabase mydb) {
        try {
            String sqlLop = "CREATE TABLE IF NOT EXISTS Lop(maLop VARCHAR PRIMARY KEY, tenLop VARCHAR," +
                    " maNganh VARCHAR, tenNganh VARCHAR, khoa VARCHAR)";
            mydb.execSQL(sqlLop);

            String sqlSV = "CREATE TABLE IF NOT EXISTS SinhVien(maSv VARCHAR PRIMARY KEY, hoTen VARCHAR ," +
                    "tenLop VARCHAR, ngaySinh VARCHAR, gioiTinh VARCHAR, sdt INTEGER)";
            mydb.execSQL(sqlSV);

            String sqlMH = "CREATE TABLE IF NOT EXISTS MonHoc(maMon VARCHAR PRIMARY KEY, tenMon VARCHAR, " +
                    "tinChi INTEGER, hocKy INTEGER)";
            mydb.execSQL(sqlMH);

            String sqlD ="CREATE TABLE IF NOT EXISTS Diema(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "maSv VARCHAR,hoTen VARCHAR, maMon VARCHAR, tenMon VARCHAR, diemCC FLOAT, diemGK FLOAT," +
                    " diemCK FLOAT,hocKy FLOAT)";
            mydb.execSQL(sqlD);

            String sqlDTK ="CREATE TABLE IF NOT EXISTS TK(id INTEGER PRIMARY KEY AUTOINCREMENT,diemTK FLOAT," +
                    " diemChu VARCHAR, danhGia VARCHAR)";
            mydb.execSQL(sqlDTK);

        } catch (Exception e) {
            Log.e("Error", "Không thể tạo bảng");
        }
    }


}
