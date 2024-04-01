package com.example.savepassapp.OpcionesPassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.savepassapp.BaseDeDatos.BDHelper;
import com.example.savepassapp.MainActivity;
import com.example.savepassapp.R;

public class Agregar_Password extends AppCompatActivity {
    EditText EtTitulo, EtCuenta, EtNombreUsuario, EtPassword, EtSitioWeb, EtNota;

    String id, titulo, cuenta, nombre_usuario, password, sitio_web, nota, tiempo_registro, tiempo_actualizacion;
    private BDHelper bdHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_password);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("");

        InicializarVariables();
    }

    private void InicializarVariables(){
        EtTitulo = findViewById(R.id.EtTitulo);
        EtCuenta = findViewById(R.id.EtCuenta);
        EtNombreUsuario = findViewById(R.id.EtNombreUsuario);
        EtPassword = findViewById(R.id.EtPassword);
        EtSitioWeb = findViewById(R.id.EtSitioWeb);
        EtNota = findViewById(R.id.EtNota);

        bdHelper = new BDHelper(this);
    }

    private void Guardar_Password(){
        /*OBTENER DATOS DE ENTRADA */
        titulo = EtTitulo.getText().toString().trim();
        cuenta = EtCuenta.getText().toString().trim();
        nombre_usuario = EtNombreUsuario.getText().toString().trim();
        password = EtPassword.getText().toString().trim();
        sitio_web = EtSitioWeb.getText().toString().trim();
        nota = EtNota.getText().toString().trim();

        if (!titulo.equals("")){
            //Actualizar el registro
            /*Tiempo del dispositivo*/
            String tiempo_actual = ""+ System.currentTimeMillis();
            long id= bdHelper.insertarRegistro(
                    ""+ titulo,
                    ""+ cuenta,
                    ""+nombre_usuario,
                    ""+password,
                    ""+sitio_web,
                    ""+nota,
                    ""+tiempo_actual,
                    ""+tiempo_actual

            );

            Toast.makeText(this, "Se ha guardado la contraseña: "+id, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Agregar_Password.this, MainActivity.class));
            finish();

        }
        else{
            EtTitulo.setError("Campo obligatorio");
            EtTitulo.setFocusable(true);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_guardar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.Guardar_Password){
            Guardar_Password();
        }

        return super.onOptionsItemSelected(item);
    }
}