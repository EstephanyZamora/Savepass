package com.example.savepassapp.Fragmentos;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.savepassapp.Adaptador.Adaptador_password;
import com.example.savepassapp.BaseDeDatos.BDHelper;
import com.example.savepassapp.BaseDeDatos.Constants;
import com.example.savepassapp.OpcionesPassword.Agregar_Password;
import com.example.savepassapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class F_Todas extends Fragment {
    BDHelper helper;
    FloatingActionButton FAB_AgregarPassword;
    RecyclerView recyclerView_Registros;

    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_f__todas, container, false);

        recyclerView_Registros = view.findViewById(R.id.recyclerView_Registros);
        FAB_AgregarPassword = view.findViewById(R.id.FAB_AgregaPassword);
        helper = new BDHelper(getActivity());
        dialog = new Dialog(getActivity());

        CargarRegistros();

        FAB_AgregarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(new Intent(getActivity(), Agregar_Password.class));

                Intent intent = new Intent(getActivity(), Agregar_Password.class);
                startActivity(intent);

            }
        });
        return view;
    }

    private void CargarRegistros() {
        Adaptador_password adaptador_password = new Adaptador_password(getActivity(), helper.ObtenerTodosRegistros(
                Constants.C_TITULO + " ASC"));
        recyclerView_Registros.setAdapter(adaptador_password);
    }

    private void BuscarRegistros(String consulta){
        Adaptador_password adaptador_password = new Adaptador_password(getActivity(), helper.BuscarRegistros(consulta));
        recyclerView_Registros.setAdapter(adaptador_password);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragmento_todos, menu);

        MenuItem item = menu.findItem(R.id.menu_Buscar_registros);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /*REALIZA LA BUSQUEDA DE UN REGISTRO CUANDO EL USUARIO PRESIONA EL BOTON DE BUSQUEDA
                * EN EL TECLADO DEL DISPOSITIVO*/
                BuscarRegistros(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                /* realiza la  buscqueda miesntras el usuario esta escribiendo*/
                BuscarRegistros(newText);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_Numero_registros) {
           Visualizar_Total_Registros();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    private void Visualizar_Total_Registros() {
        TextView Total;
        Button BtnEntendidoTotal;

        dialog.setContentView(R.layout.cuadro_dialogo_total_registros);

        Total = dialog.findViewById(R.id.Total);
        BtnEntendidoTotal = dialog.findViewById(R.id.BtnEntendidoTotal);

        //Obtener el valor entero de registros
        int total = helper.ObtenerNumeroRegistros();
        //Convertir a cadena el número total de registros
        String total_string = String.valueOf(total);
        Total.setText(total_string);

        BtnEntendidoTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.setCancelable(false);


    }
}