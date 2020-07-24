package com.example.proyectofinal.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proyectofinal.Entities.Usuario;
import com.example.proyectofinal.R;

import java.util.ArrayList;


public class UsuariosListAdapter extends ArrayAdapter<Usuario> {
    private static final String TAG = "UsuariosListAdapter";
    private Context mcontext;
    int mResource;


    public UsuariosListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Usuario> objects) {
        super(context, resource, objects);
        mcontext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Integer id = getItem(position).getId();
        String nombre = getItem(position).getNombre();
        String nombre_usuario = getItem(position).getNombre_usuario();
        String correo = getItem(position).getCorreo();
        String clave = getItem(position).getClave();

        Usuario usuario = new Usuario(id, nombre, nombre_usuario, correo, clave);
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvNombre = (TextView) convertView.findViewById(R.id.customLVText);
        TextView tvNombreUsuario = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvCorreo = (TextView) convertView.findViewById(R.id.textView3);

        tvNombre.setText(nombre);
        tvNombreUsuario.setText(nombre_usuario);
        tvCorreo.setText(correo);

        return convertView;

    }
}
