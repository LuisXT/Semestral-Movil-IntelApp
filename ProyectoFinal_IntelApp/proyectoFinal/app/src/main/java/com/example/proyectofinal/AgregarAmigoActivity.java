package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectofinal.Database.UsersDBHelper;
import com.example.proyectofinal.Entities.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AgregarAmigoActivity extends AppCompatActivity {

    TextView display_nombre_usuario;
    Button agregar_usuario;
    UsersDBHelper conexion;
    ArrayList<Usuario> amistades = new ArrayList<>();
    Usuario usuario = null;
    ArrayList<String> nombresUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_amigo);
        conexion = new UsersDBHelper(this, "usuarios_db", null, 14);

        display_nombre_usuario = findViewById(R.id.NomUsuario);
        agregar_usuario = findViewById(R.id.Agregar);
        Bundle userInfo = getIntent().getExtras();
        usuario = (Usuario) userInfo.getSerializable("usuario");
        final Usuario usuarioElegido = (Usuario) userInfo.getSerializable("usuarioElegido");
        display_nombre_usuario.setText(usuarioElegido.getNombre_usuario());


        agregar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarAmistades();
                SQLiteDatabase db = conexion.getWritableDatabase();

                if (!nombresUsuario.contains(usuarioElegido.getNombre_usuario())) {
                    amistades.add(usuarioElegido);
                    String[] params = {usuario.getId().toString()};
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < amistades.size(); i++) {
                        JSONObject item = new JSONObject();
                        try {
                            item.put("id", amistades.get(i).getId());
                            item.put("nombre", amistades.get(i).getNombre());
                            item.put("nombre_usuario", amistades.get(i).getNombre_usuario());
                            item.put("correo", amistades.get(i).getCorreo());
                            item.put("clave", amistades.get(i).getClave());
                            item.put("amistades", amistades.get(i).getAmistades());
                            item.put("resultados", amistades.get(i).getResultados());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jsonArray.put(item);
                    }

                    try {
                        String insert_arr = jsonArray.toString();
                        db.execSQL("UPDATE usuarios SET amistades= '" + insert_arr + "' WHERE id=?", params);

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Error agregando amigo." + e.getMessage(), Toast.LENGTH_LONG).show();
                        Log.i("Error message", e.getMessage());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "El usuario ya fue agregado a su lista de amigos!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    private void buscarAmistades() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        String[] params = {usuario.getId().toString()};
        nombresUsuario = new ArrayList<>();
        Usuario usuario = null;
        try {
            Cursor cursor = db.rawQuery("SELECT amistades FROM usuarios WHERE id=?", params);
            cursor.moveToFirst();

            if (cursor.getString(0) != null) {
                Log.i("Amistades", cursor.getString(0));

                String amistadesUsuario = cursor.getString(0);

                JSONArray jsonArray = new JSONArray(amistadesUsuario);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    usuario = new Usuario(Integer.parseInt(jsonObject.getString("id")), jsonObject.getString("nombre"), jsonObject.getString("nombre_usuario"), jsonObject.getString("correo"), jsonObject.getString("clave"));

                    amistades.add(usuario);
                    nombresUsuario.add(usuario.getNombre_usuario());
                }



            }


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error buscando sus amistades.", Toast.LENGTH_LONG).show();
        }
    }
}