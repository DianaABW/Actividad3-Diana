package com.example.actividad3;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;
public class UsuarioDao {
    private GestionDB gestionDB;
    Context context;
    View view;
    Usuario usuario;

    public View getView() {
        return view;
    }
    public void setView(View view) {
        this.view = view;
    }
    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }

    public UsuarioDao (Context context, View view){
        this.context=context;
        this.view=view;
        gestionDB=new GestionDB(this.context);

    }

    //Insertar datos en la tabla Usuarios

    public void insertUser(Usuario usuario ){
        try {
            SQLiteDatabase db = gestionDB.getWritableDatabase();
            if (db != null) {
                ContentValues values = new ContentValues();
                values.put("USU_DOCUMENTO", usuario.getDocumento());
                values.put("USU_USUARIO", usuario.getUsuario());
                values.put("USU_NOMBRES", usuario.getNombres());
                values.put("USU_APELLIDOS", usuario.getApellidos());
                values.put("USU_CONTRA", usuario.getContra());
                long respuesta = db.insert("usuarios", null, values);
                Snackbar.make(this.view, "Se ha registrado el usuario " + respuesta, Snackbar.LENGTH_LONG).show();
                db.close();
            } else {
                Snackbar.make(this.view, "No ha registrado el usuario ", Snackbar.LENGTH_LONG).show();
            }
        }catch (SQLException sqlException){
            Log.i("DB ",""+sqlException);
        }
    }

    public ArrayList<Usuario> getUserList() {
        SQLiteDatabase db = gestionDB.getReadableDatabase();
        String query = "select * from usuarios";
        ArrayList<Usuario> userList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                usuario= new Usuario();
                usuario.setDocumento(cursor.getInt(0));
                usuario.setUsuario(cursor.getString(1));
                usuario.setNombres(cursor.getString(2));
                usuario.setApellidos(cursor.getString(3));
                usuario.setContra(cursor.getString(4));
                userList.add(usuario);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }
    public ArrayList<Usuario> BuscarPorDoc (int docu){
        SQLiteDatabase db = gestionDB.getReadableDatabase();
        String query = "select * from usuarios where USU_DOCUMENTO = "+docu;
        ArrayList<Usuario> userList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                usuario= new Usuario();
                usuario.setDocumento(cursor.getInt(0));
                usuario.setUsuario(cursor.getString(1));
                usuario.setNombres(cursor.getString(2));
                usuario.setApellidos(cursor.getString(3));
                usuario.setContra(cursor.getString(4));
                userList.add(usuario);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

}
