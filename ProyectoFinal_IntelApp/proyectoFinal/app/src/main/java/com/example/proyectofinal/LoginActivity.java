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
import com.example.proyectofinal.Entities.Usuario;
import com.example.proyectofinal.Utilities.Utilities;

import org.json.JSONArray;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    EditText nombre_usuario, clave;
    Button ingresar, registar;
    UsersDBHelper conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        conexion = new UsersDBHelper(this, "usuarios_db", null, 14);
        nombre_usuario = findViewById(R.id.editTextTextUsuario);
        clave = findViewById(R.id.editTextTextClave);
        ingresar = findViewById(R.id.buttonIngresar);
        registar = findViewById(R.id.buttonRegistrar);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = conexion.getReadableDatabase();
                String[] params = {nombre_usuario.getText().toString(), clave.getText().toString()};
                JSONArray jsonArray = new JSONArray();
                try {
                    Cursor cursor = db.rawQuery("SELECT id,nombre,nombre_usuario,correo,clave,amistades FROM usuarios WHERE nombre_usuario=? AND clave=?", params);
                    cursor.moveToFirst();

                    Log.i("nombre", cursor.getString(0));
                    Log.i("nombre_usuario", cursor.getString(1));
                    Log.i("correo", cursor.getString(2));
                    Log.i("amistades", cursor.getString(3));


                    HashMap<String, String> sessionInfo = new HashMap<>();
                    sessionInfo.put("nombre_usuario", nombre_usuario.getText().toString());
                    sessionInfo.put("clave", clave.getText().toString());
                    sessionInfo.put("tipoAuth", "login");

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("usuario", sessionInfo);
                    intent.putExtras(bundle);
                    startActivity(intent);

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Credenciales incorrectos inténtelo denuevo.", Toast.LENGTH_LONG).show();
                }


            }
        });

        registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                Bundle bundle = new Bundle();
                startActivity(intent);
            }
        });

    }
}