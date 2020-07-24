package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectofinal.Database.UsersDBHelper;
import com.example.proyectofinal.Entities.Resultado;
import com.example.proyectofinal.Entities.Usuario;
import com.example.proyectofinal.Utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    EditText nombre, nombre_usuario, correo, clave;
    Button registrarse;
    UsersDBHelper conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        conexion = new UsersDBHelper(this, "usuarios_db", null, 14);
        nombre = findViewById(R.id.editTextTextNombreR);
        nombre_usuario = findViewById(R.id.editTextTextNombreUsuarioR);
        correo = findViewById(R.id.editTextTextCorreoR);
        clave = findViewById(R.id.editTextTextClaveR);
        registrarse = findViewById(R.id.buttonRegistrarseR);

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = conexion.getWritableDatabase();


                try {
                    if (nombre.getText().toString().equals("") || nombre_usuario.getText().toString().equals("") || correo.getText().toString().equals("") || clave.getText().toString().length() < 3) {
                        Toast.makeText(getApplicationContext(), "Ninguno de los campos puede estar vacio y la clave debe ser mayor a 3 caracteres.", Toast.LENGTH_LONG).show();
                    } else {
                        ArrayList<Usuario> adminArr = new ArrayList<>();
                        ArrayList<Resultado> resultadoArr = new ArrayList<>();
                        Usuario amigoAdmin = new Usuario(590, "Admin", "ad", "admin@admin.com", "456");
                        Resultado resultadoBase = new Resultado(0.0, 0.0, 0.0, "N/A");
                        adminArr.add(amigoAdmin);
                        resultadoArr.add(resultadoBase);
                        JSONArray dbRArray = new JSONArray();
                        JSONArray dbArray = new JSONArray();

                        for (int i = 0; i < resultadoArr.size(); i++) {
                            JSONObject item = new JSONObject();
                            try {
                                item.put("puntajeVisual", resultadoArr.get(i).getPuntajeVisual());
                                item.put("puntajeAuditivo", resultadoArr.get(i).getPuntajeAuditivo());
                                item.put("puntajeKine", resultadoArr.get(i).getPuntajeKine());
                                item.put("tipoInteligencia", resultadoArr.get(i).getTipoInteligencia());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            dbRArray.put(item);
                        }

                        for (int i = 0; i < adminArr.size(); i++) {
                            JSONObject item = new JSONObject();
                            try {
                                item.put("id", adminArr.get(i).getId());
                                item.put("nombre", adminArr.get(i).getNombre());
                                item.put("nombre_usuario", adminArr.get(i).getNombre_usuario());
                                item.put("correo", adminArr.get(i).getCorreo());
                                item.put("clave", adminArr.get(i).getClave());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            dbArray.put(item);
                        }
                        String insertAmigo = dbArray.toString();
                        String insertResultados = dbRArray.toString();
                        String query = "INSERT INTO " + Utilities.TABLA + " (" + Utilities.NOMBRE + "," + Utilities.NOMBRE_USUARIO + "," + Utilities.CORREO + "," + Utilities.CLAVE + ", " + Utilities.AMISTADES + "," + Utilities.RESULTADOS + ") VALUES ('" + nombre.getText().toString() + "','" + nombre_usuario.getText().toString() + "','" + correo.getText().toString() + "','" + clave.getText().toString() + "','" + insertAmigo + "','" + insertResultados + "')";
                        db.execSQL(query);


                        HashMap<String, String> sessionInfo = new HashMap<>();
                        sessionInfo.put("nombre_usuario", nombre_usuario.getText().toString());
                        sessionInfo.put("clave", clave.getText().toString());
                        sessionInfo.put("tipoAuth", "registro");
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("usuario", sessionInfo);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error guardando al usuario en la base de datos.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}