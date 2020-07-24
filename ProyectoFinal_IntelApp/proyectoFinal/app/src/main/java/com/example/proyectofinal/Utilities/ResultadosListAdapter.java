package com.example.proyectofinal.Utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proyectofinal.Entities.Resultado;
import com.example.proyectofinal.R;

import java.util.ArrayList;

public class ResultadosListAdapter extends ArrayAdapter<Resultado> {
    private static final String TAG = "ResultadosListAdapter";
    private Context mcontext;
    int mResource;


    public ResultadosListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Resultado> objects) {
        super(context, resource, objects);
        mcontext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String puntajeVisual = String.valueOf(getItem(position).getPuntajeVisual());
        String puntajeAuditivo = String.valueOf(getItem(position).getPuntajeAuditivo());
        String puntajeKine = String.valueOf(getItem(position).getPuntajeKine());
        String tipoInteligencia = String.valueOf(getItem(position).getTipoInteligencia());


        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView puntajeVisualTV = (TextView) convertView.findViewById(R.id.puntajeVisualH);
        TextView puntajeAuditivoTV = (TextView) convertView.findViewById(R.id.puntajeAuditivoH);
        TextView puntajeKineTV = (TextView) convertView.findViewById(R.id.puntajeKineH);
        TextView tipoInteligenciaTV = (TextView) convertView.findViewById(R.id.tipoInteligenciaH);

        puntajeVisualTV.setText(puntajeVisual);
        puntajeAuditivoTV.setText(puntajeAuditivo);
        puntajeKineTV.setText(puntajeKine);
        tipoInteligenciaTV.setText(tipoInteligencia);
        return convertView;

    }
}
