package com.example.actividad3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class GestionDB extends SQLiteOpenHelper{
    private static final String DATABASE_USERS ="dbUsuarios";
    private static final int VERSION=1;

    private static final String TABLE_USERS="usuarios";
    private static final String CREATE_TABLE_USERS="CREATE TABLE " +TABLE_USERS+
            " (USU_DOCUMENTO INTEGER PRIMARY KEY," +
            " USU_USUARIO varchar(50) NOT NULL," +
            " USU_NOMBRES varchar(150) NOT NULL," +
            " USU_APELLIDOS varchar(150) NOT NULL," +
            " USU_CONTRA varchar(25) NOT NULL)";

    private static final String DELETE_TABLE="DROP TABLE IF EXISTS "+TABLE_USERS;


    public GestionDB(@Nullable Context context) {
        super(context, DATABASE_USERS, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        Log.i("Base de datos", "se creo la base de datos");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db , int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);


    }
}
