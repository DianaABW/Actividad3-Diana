package com.example.actividad3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private EditText  etDocumento, etUsuario, etNombres, etApellidos, etContra;
    private ListView listaUsuarios;
    private  GestionDB gestionDB;
    private UsuarioDao usuarioDao;
    int documento;
    String usuario, nombres, apellidos, contra;
    SQLiteDatabase baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializador();
    }
    private void inicializador(){
        etDocumento = findViewById(R.id.doc);
        etUsuario = findViewById(R.id.usu);
        etNombres = findViewById(R.id.name);
        etApellidos = findViewById(R.id.apell);
        etContra = findViewById(R.id.pas);
        listaUsuarios = findViewById(R.id.LV_Lista);

        this.listarUsuario();
    }
    private void listarUsuario(){
        usuarioDao=new UsuarioDao(this, findViewById(R.id.LV_Lista));
        ArrayList<Usuario> userList= usuarioDao.getUserList();
        ArrayAdapter<Usuario> adapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,userList);
        listaUsuarios.setAdapter(adapter);
    }
    public void accionRegistrar(View v){
        boolean permisoReg=false;
        seteardatos(permisoReg);
        if(permisoReg== true) {
            UsuarioDao usuarioDao = new UsuarioDao(this, v);
            Usuario usuario1 = new Usuario();
            usuario1.setNombres(this.nombres);
            usuario1.setApellidos(this.apellidos);
            usuario1.setUsuario(this.usuario);
            usuario1.setDocumento(this.documento);
            usuario1.setContra(this.contra);
            usuarioDao.insertUser(usuario1);
            Toast.makeText(this, "Registro de usuario exitoso", Toast.LENGTH_LONG).show();
            this.listarUsuario();
        }else{
            //mensaje de registro invalido
            Toast.makeText(this, "Registro invalido", Toast.LENGTH_LONG).show();
        }

    }
    public boolean seteardatos(boolean permiso){
        //Validaciones
        this.nombres=etNombres.getText().toString();
        if(this.nombres.length()<3 || this.nombres.length()>25){
            //mensaje de nombre y registro invalido
            Toast.makeText(this, "Nombre y registro invalido", Toast.LENGTH_SHORT).show();
            return(permiso);
        }
        this.apellidos=etApellidos.getText().toString();
        if(this.apellidos.length()<3 || this.apellidos.length()>25){
            //mensaje de apellidos y registro invalido
            Toast.makeText(this, "Apellidos y registro invalido", Toast.LENGTH_SHORT).show();
            return(permiso);
        }
        this.usuario=etUsuario.getText().toString();
        if(this.usuario.length()<3 || this.usuario.length()>25){
            //mensaje de usuario y registro invalido
            Toast.makeText(this, "Usuario y registro invalido", Toast.LENGTH_SHORT).show();
            return(permiso);
        }
        this.documento=Integer.parseInt(etDocumento.getText().toString());
        if(this.documento == 000 || this.documento <  100){
            //mensaje de documento y registro invalido
            Toast.makeText(this, "Numero de documento y registro invalido", Toast.LENGTH_SHORT).show();
            return(permiso);
        }
        this.contra=etContra.getText().toString();
        if(this.contra.length()> 5 || this.contra.length()< 10){
            validarContraseña(this.contra, permiso);
        }else{
            //mensaje de contraseña y registro invalido
            Toast.makeText(this, "Contraseña no cumple con las requerimientos de seguridad. Registro invalido", Toast.LENGTH_SHORT).show();
            return(permiso);
        }
        return (permiso);
    }
    public boolean validarContraseña (String dato, boolean permiso){
        String regex="(?=.*[0-9])" +
                "(?=.*[a-z])(?=.*[A-Z])" +
                "(?=.*[@#%&/=+*.])";
        Pattern p= Pattern.compile(regex);
        Matcher m= p.matcher(dato);
        if(m.matches()==true){
            //contraseña validada y aprovada
            permiso=true;
        }else{
            //mensaje de contraseña invalida
            Toast.makeText(this, "Contraseña no cumple con las requerimientos de seguridad", Toast.LENGTH_SHORT).show();
        }
        return (permiso);
    }
    public void buscarPorDoc(View v){
        Toast.makeText(this, "Numero de documento es necesario para realizar la busqueda", Toast.LENGTH_SHORT).show();
        etDocumento = findViewById(R.id.doc);
        this.documento=Integer.parseInt(etDocumento.getText().toString());
        if(this.documento == 000 || this.documento <  100 ){
            //mensaje de documento invalido en la base de datos
            Toast.makeText(this, "Numero de documento invalido", Toast.LENGTH_SHORT).show();
        }
        UsuarioDao usuarioDao = new UsuarioDao(this, v);
        usuarioDao.BuscarPorDoc(this.documento);
        this.listarUsuario();
    }
    public void listar (View v){
        listarUsuario();
    }

}