package com.example.savepassapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import com.example.savepassapp.Fragmentos.F_Ajustes;
import com.example.savepassapp.Fragmentos.F_Todas;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawer;
    boolean DobleToqueParaSalir = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Toolbar toolbar = findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView =findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        
        /*este es el fragmento que se ejecuta*/
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new F_Todas()).commit();
            navigationView.setCheckedItem(R.id.Opcion_Todas);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.Opcion_Todas){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new F_Todas()).commit();

        }
        if(id == R.id.Opcion_Ajustes){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new F_Ajustes()).commit();

        }
        if(id == R.id.Opcion_Salir){
            Toast.makeText(this, "Cerraste sesión", Toast.LENGTH_SHORT).show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (DobleToqueParaSalir) {
            super.onBackPressed(); // Si ya se ha presionado dos veces, permite que el comportamiento predeterminado de onBackPressed cierre la actividad
            Toast.makeText(this, "Saliste de la aplicación ", Toast.LENGTH_SHORT).show();
            return;
        }

        this.DobleToqueParaSalir = true;
        Toast.makeText(this, "Presiona de nuevo para salir", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                DobleToqueParaSalir = false; // Reinicia la variable después de 2 segundos
            }
        }, 2000); // 2 segundos
    }


}