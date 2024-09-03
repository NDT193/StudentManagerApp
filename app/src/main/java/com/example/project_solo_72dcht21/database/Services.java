package com.example.project_solo_72dcht21.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;

import com.example.project_solo_72dcht21.constant.*;

import java.util.ArrayList;
import java.util.List;

public class Services {
    private SQLiteDatabase database;
    private final String[] MonHocColumns = {"maMon", "tenMon", "tinChi", "hocKy"};
    private final String[] SinhVienColumns = {"maSv","hoTen","tenLop","ngaySinh","gioiTinh","sdt"};
    private final String[] LopColumns = {"maLop","tenLop","maNganh","tenNganh","khoa"};
    private final String[] DiemColumns = {"maSv","hoTen","maMon","tenMon","diemCC","diemGK","diemCK","hocKy"};


    public Services() {
    }

    public List<String> selectAll(String tableName, SQLiteDatabase database) {
        List<String> data = new ArrayList<>();
        Cursor values = database.rawQuery("SELECT * FROM " + tableName, null);

        int TableColumns = getTableColumns(tableName);
        if (values.moveToFirst()) {
            do {
                /*data.add(values.getString(0) + "-" +
                        values.getString(1) + "-" +
                        values.getString(2) + "-" +
                        values.getString(3));*/
                String itemview = "";
                for (int i=0; i<TableColumns; i++)
                {
                     if (i < TableColumns-1) {
                        itemview += values.getString(i)+ "-";
                    }
                    else {
                        itemview += values.getString(i);
                    }
                }

                data.add(itemview);
            } while (values.moveToNext());
        }
        values.close();

        return data;
    }

    private int getTableColumns(String tableName){
        int TableColumns;
        if (tableName.equals(DatabaseConst.TABLE_MonHoc))
            TableColumns= 4;
        else if (tableName.equals(DatabaseConst.TABLE_Lop))
            TableColumns= 5;
        else if (tableName.equals(DatabaseConst.TABLE_SinhVien))
            TableColumns= 6;
        else
            TableColumns= 9;
        return TableColumns;
    }

    public boolean insert(String tableName, List<EditText> listEditText, SQLiteDatabase database) {
        ContentValues contentValues = buildContentValues(tableName, listEditText);

        if (database.insert(tableName, null, contentValues) == -1)
            return false;
        else
            return true;
    }


    public boolean updateOne(String tableName, List<EditText> listEditText, String key, SQLiteDatabase database) {
        ContentValues contentValues = buildContentValues(tableName, listEditText);
        String whereClause = getWhereClause(tableName);

        if (database.update(tableName, contentValues, whereClause, new String[]{key}) == 0)
            return false;
        else
            return true;
    }


    public boolean deleteOne(String tableName, String key, SQLiteDatabase database) {
        String whereClause = getWhereClause(tableName);

        if (database.delete(tableName, whereClause, new String[]{key}) == 0)
            return false;
        else
            return true;
    }

    public String[] splitString(String inputString) {
        String[] splitArray = inputString.split("-");
        return splitArray;
    }

// ==================================== PRIVATE FUNCTION ====================================

    private ContentValues buildContentValues(String tableName, List<EditText> listEditText) {
        ContentValues contentValues = new ContentValues();
        List<String> editTextValues = getEditTextValues(listEditText);

        // nếu là môn học
         if (tableName.equals(DatabaseConst.TABLE_MonHoc)) {

            for (int i = 0; i < MonHocColumns.length; i++) {

                String key = MonHocColumns[i];
                String value = editTextValues.get(i);
                contentValues.put(key, value);

            }
         }
         else if (tableName.equals(DatabaseConst.TABLE_Lop)) {

             for (int i = 0; i < LopColumns.length; i++) {

                 String key = LopColumns[i];
                 String value = editTextValues.get(i);
                 contentValues.put(key, value);

             }
         }else if (tableName.equals(DatabaseConst.TABLE_Diem)) {

             for (int i = 0; i < DiemColumns.length; i++) {

                 String key = DiemColumns[i];
                 String value = editTextValues.get(i);
                 contentValues.put(key, value);
             }
         }else if (tableName.equals(DatabaseConst.TABLE_SinhVien)) {

             for (int i = 0; i < SinhVienColumns.length; i++) {

                 String key = SinhVienColumns[i];
                 String value = editTextValues.get(i);
                 contentValues.put(key, value);

             }
         }


            return contentValues;
        }
        //lấy giá trị edittext
        private List<String> getEditTextValues (List < EditText > listEditText) {
            List<String> values = new ArrayList<>();
            for (EditText items : listEditText) {
                values.add(items.getText().toString());
            }
            return values;
        }

        //hàm lấy điều kiện
        private String getWhereClause (String tableName){
            String whereClause = null;
            if (tableName.equals(DatabaseConst.TABLE_MonHoc)) {
                whereClause = "maMon = ?";
            } else if (tableName.equals(DatabaseConst.TABLE_Lop)) {
                whereClause = "maLop = ?";
            }else if (tableName.equals(DatabaseConst.TABLE_SinhVien)) {
                whereClause = "maSv = ?";
            }else if (tableName.equals(DatabaseConst.TABLE_Diem)) {
                whereClause = "id = ?";
            }
            return whereClause;
        }
    }


