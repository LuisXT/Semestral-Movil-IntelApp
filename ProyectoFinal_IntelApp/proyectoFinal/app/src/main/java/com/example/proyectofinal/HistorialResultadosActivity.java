package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.proyectofinal.Database.UsersDBHelper;
import com.example.proyectofinal.Entities.Resultado;
import com.example.proyectofinal.Entities.Usuario;
import com.example.proyectofinal.Utilities.ResultadosListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class HistorialResultadosActivity extends AppCompatActivity {

    ListView lvHistorial;
    ArrayList<Resultado> resultados;
    String resultadosUsuario = "";
    UsersDBHelper conexion;
    Usuario usuario = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_resultados);

        conexion = new UsersDBHelper(this, "usuarios_db", null, 14);
        lvHistorial = findViewById(R.id.listViewHistorial);
        Bundle intentExtras = getIntent().getExtras();
        usuario = (Usuario) intentExtras.getSerializable("usuario");
        resultadosUsuario = usuario.getResultados();

        buscarResultados();

        ResultadosListAdapter adapter = new ResultadosListAdapter(this, R.layout.historial_view_layout, resultados);
        lvHistorial.setAdapter(adapter);


    }

    private void buscarResultados() {
        Resultado resultado = null;
        resultados = new ArrayList<>();
        SQLiteDatabase db = conexion.getReadableDatabase();
        String[] params = {String.valueOf(usuario.getId())};


        try {
            Cursor cursor = db.rawQuery("SELECT resultados FROM usuarios WHERE id=?", params);
            cursor.moveToFirst();
            cursor.getString(0);
            Log.i("Resultados======>", cursor.getString(0));


            try {
                JSONArray jsonArray = new JSONArray(cursor.getString(0));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    resultado = new Resultado(Double.parseDouble(jsonObject.getString("puntajeVisual")), Double.parseDouble(jsonObject.getString("puntajeAuditivo")), Double.parseDouble(jsonObject.getString("puntajeKine")), jsonObject.getString("tipoInteligencia"));
                    resultados.add(resultado);
                }
                Collections.reverse(resultados);
                if (resultados.size() > 1) {
                    resultados.remove(resultados.size() - 1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            Log.i("Error buscarResultados", e.getMessage());
        }


//        try {
//            JSONArray jsonArray = new JSONArray(resultadosUsuario);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
//                resultado = new Resultado(Double.parseDouble(jsonObject.getString("puntajeVisual")), Double.parseDouble(jsonObject.getString("puntajeAuditivo")), Double.parseDouble(jsonObject.getString("puntajeKine")), jsonObject.getString("tipoInteligencia"));
//                resultados.add(resultado);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }


}