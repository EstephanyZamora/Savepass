package com.example.savepassapp.Fragmentos;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.savepassapp.BaseDeDatos.BDHelper;
import com.example.savepassapp.BaseDeDatos.Constants;
import com.example.savepassapp.MainActivity;
import com.example.savepassapp.Modelo.Password;
import com.example.savepassapp.R;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;


public class F_Ajustes extends Fragment {

    TextView Eliminar_Todos_Registros, Exportar_Archivo, Importar_Archivo;
    Dialog dialog;

    boolean carpetaCreada = false;
    BDHelper bdHelper;
    String ordenarTituloAsc = Constants.C_TITULO + " ASC";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_f__ajustes, container, false);

        Eliminar_Todos_Registros = view.findViewById(R.id.Eliminar_Todos_Registros);
        Exportar_Archivo = view.findViewById(R.id.Exportar_Archivo);
        Importar_Archivo = view.findViewById(R.id.Importar_Archivo);
        dialog = new Dialog(getActivity());
        bdHelper = new BDHelper(getActivity());
        Eliminar_Todos_Registros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog_Eliminar_Registros();

            }
        });

        Exportar_Archivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Exportar archivo", Toast.LENGTH_SHORT).show();
                ExportarRegistros();

            }
        });

        Importar_Archivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Importar archivo", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
    private void Dialog_Eliminar_Registros() {
        Button Btn_Si, Btn_Cancelar;

        dialog.setContentView(R.layout.cuadro_dialogo_eliminar_todos_registros);


        Btn_Si = dialog.findViewById(R.id.Btn_Si);
        Btn_Cancelar = dialog.findViewById(R.id.Btn_Cancelar);

        Btn_Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bdHelper.EliminarTodosRegistros();
                startActivity(new Intent(getActivity(), MainActivity.class));
                Toast.makeText(getActivity(), "Se ha eliminado todos los registros", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        Btn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setCancelable(false);

    }

    private void ExportarRegistros() {
        //Nombre de la carpeta
        File carpeta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Savepass App");

        if (!carpeta.exists()){
            //Si la carpeta no existe, creamos una nueva
            carpetaCreada = carpeta.mkdirs();
        }

        //Nombre del archivo
        String csvnombreArchivo = "Registro.csv";

        //Concatenar el nombre de la carpeta y del archivo
        String Carpeta_Archivo = carpeta + "/" + csvnombreArchivo;

        /*Obtener el registro que vamos a exportar*/
        ArrayList<Password> registroList = new ArrayList<>();
        registroList.clear();
        registroList = bdHelper.ObtenerTodosRegistros(ordenarTituloAsc);

        try {
            //Escribir en el archivo
            FileWriter fileWriter = new FileWriter(Carpeta_Archivo);
            for (int i = 0; i <registroList.size(); i++){
                fileWriter.append("" + registroList.get(i).getId());
                fileWriter.append(",");
                fileWriter.append("" + registroList.get(i).getTitulo());
                fileWriter.append(",");
                fileWriter.append("" + registroList.get(i).getCuenta());
                fileWriter.append(",");
                fileWriter.append("" + registroList.get(i).getNombre_usuario());
                fileWriter.append(",");
                fileWriter.append("" + registroList.get(i).getPassword());
                fileWriter.append(",");
                fileWriter.append("" + registroList.get(i).getSitio_web());
                fileWriter.append(",");
                fileWriter.append("" + registroList.get(i).getNota());
                fileWriter.append(",");
                fileWriter.append("" + registroList.get(i).getImagen());
                fileWriter.append(",");
                fileWriter.append("" + registroList.get(i).getT_registro());
                fileWriter.append(",");
                fileWriter.append("" + registroList.get(i).getT_actualiacion());
                fileWriter.append("\n");
            }
            fileWriter.flush();
            fileWriter.close();
            Toast.makeText(getActivity(), "Se ha exportado el archivo CSV con éxito", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Log.d("Ruta de archivo", Carpeta_Archivo);

            Toast.makeText(getActivity(), "Error al exportar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}