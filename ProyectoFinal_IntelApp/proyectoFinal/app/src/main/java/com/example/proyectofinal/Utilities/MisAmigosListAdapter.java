package com.example.proyectofinal.Utilities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proyectofinal.Entities.Usuario;
import com.example.proyectofinal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MisAmigosListAdapter extends ArrayAdapter<Usuario> {

    private static final String TAG = "MisAmigosListAdapter";
    private Context mcontext;
    int mResource;


    public MisAmigosListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Usuario> objects) {
        super(context, resource, objects);
        mcontext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String nombre = getItem(position).getNombre();
        String nombre_usuario = getItem(position).getNombre_usuario();
        String resultados = getItem(position).getResultados();
        String ultimoResultado = null;

        Log.i("Resultados test", resultados);

        try {
            JSONArray jsonArray = new JSONArray(resultados);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                ultimoResultado = jsonObject.getString("tipoInteligencia");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvNombre = (TextView) convertView.findViewById(R.id.textViewANombre);
        TextView tvNombreUsuario = (TextView) convertView.findViewById(R.id.textViewANombreUsuario);
        TextView tvTipoI = (TextView) convertView.findViewById(R.id.textViewATI);

        tvNombre.setText(nombre);
        tvNombreUsuario.setText(nombre_usuario);
        tvTipoI.setText(ultimoResultado);

        return convertView;

    }
}
