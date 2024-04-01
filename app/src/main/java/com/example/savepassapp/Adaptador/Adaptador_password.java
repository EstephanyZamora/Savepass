package com.example.savepassapp.Adaptador;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savepassapp.Detalle.Detalle_registro;
import com.example.savepassapp.Modelo.Password;
import com.example.savepassapp.R;

import java.util.ArrayList;

public class Adaptador_password extends RecyclerView.Adapter<Adaptador_password.HolderPassword>{

    private Context context;
    private ArrayList<Password> passwordList;

    Dialog dialog;

    public Adaptador_password (Context context, ArrayList<Password> passwordList){
        this.context =context;
        this.passwordList = passwordList;
        dialog = new Dialog(context);

    }

    @NonNull
    @Override
    public HolderPassword onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        /*INFLAMOS EL ITEM*/
        View view = LayoutInflater.from(context).inflate(R.layout.item_password, parent, false);
        return new HolderPassword(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderPassword holder, int position) {
        Password modelo_password =passwordList.get(position);
        String id =modelo_password.getId();
        String titulo = modelo_password.getTitulo();
        String cuenta = modelo_password.getCuenta();
        String nombre_usuario = modelo_password.getNombre_usuario();
        String password = modelo_password.getPassword();
        String sitio_web = modelo_password.getSitio_web();
        String nota = modelo_password.getNota();
        String tiempo_registro = modelo_password.getT_registro();
        String tiempo_actualizacion = modelo_password.getT_actualiacion();

        holder.Item_titulo.setText(titulo);
        holder.Item_cuenta.setText(cuenta);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Detalle_registro.class);
                /*ENVIAMOS EL DATO ID A LA ACTIVIDAD DETALLE_REGISTRO*/
                intent.putExtra("Id_registro", id);
                context.startActivity(intent);
            }
        });

        holder.ib_mas_opciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Opciones_Editar_Eliminar();

            }
        });

    }

    @Override
    public int getItemCount() {
        /*TAMAÑO DE LA LISTA*/
        return passwordList.size();
    }

    class HolderPassword extends RecyclerView.ViewHolder{
        TextView Item_titulo, Item_cuenta, Item_nombre_usuario, Item_password, Item_sitio_web, Item_nota;
        ImageButton ib_mas_opciones;


        public HolderPassword(@NonNull View itemView) {
            super(itemView);

            Item_titulo = itemView.findViewById(R.id.item_titulo);
            Item_cuenta = itemView.findViewById(R.id.item_cuenta);
            Item_nombre_usuario = itemView.findViewById(R.id.item_nombre_usuario);
            Item_password = itemView.findViewById(R.id.item_password);
            Item_sitio_web = itemView.findViewById(R.id.item_sitio_web);
            Item_nota = itemView.findViewById(R.id.item_nota);

            ib_mas_opciones = itemView.findViewById(R.id.ib_mas_opciones);

        }
    }

     private void Opciones_Editar_Eliminar(){
         Button Btn_Editar_Registro, Btn_Eliminar_Registro;

         dialog.setContentView(R.layout.cuadro_dialogo_editar_eliminar);

         Btn_Editar_Registro = dialog.findViewById(R.id.Btn_Editar_Registro);
         Btn_Eliminar_Registro = dialog.findViewById(R.id.Btn_Eliminar_Registro);

         Btn_Editar_Registro.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Toast.makeText(context, "Editar registro: ", Toast.LENGTH_SHORT).show();
                 dialog.dismiss();

             }
         });
         Btn_Eliminar_Registro.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Toast.makeText(context, "Eliminar registro: ", Toast.LENGTH_SHORT).show();
                 dialog.dismiss();

             }
         });

         dialog.show();
         dialog.setCancelable(true);

     }


}
