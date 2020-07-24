package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyectofinal.Database.UsersDBHelper;
import com.example.proyectofinal.Entities.Usuario;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button prueba, resultados, recomendaciones, tipos, amigos;
    UsersDBHelper conexion;
    Usuario usuario = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conexion = new UsersDBHelper(this, "usuarios_db", null, 14);
        prueba = findViewById(R.id.buttonPrueba);
        resultados = findViewById(R.id.buttonResultados);
        recomendaciones = findViewById(R.id.buttonRecomemdaciones);
        tipos = findViewById(R.id.buttonTipos);
        amigos = findViewById(R.id.buttonAmigos);

        Bundle intentExtas = getIntent().getExtras();
        HashMap<String, String> sessionInfo = (HashMap<String, String>) intentExtas.getSerializable("usuario");


        try {
            SQLiteDatabase db = conexion.getReadableDatabase();
            String[] params = {sessionInfo.get("nombre_usuario"), sessionInfo.get("clave")};

            Cursor cursor = db.rawQuery("SELECT id,nombre,nombre_usuario,correo,clave,amistades,resultados FROM usuarios WHERE nombre_usuario=? AND clave=?", params);
            cursor.moveToFirst();

            Log.i("ID", cursor.getString(0));
            Log.i("nombre", cursor.getString(1));
            Log.i("nombre_usuario", cursor.getString(2));
            Log.i("correo", cursor.getString(3));
            Log.i("clave", cursor.getString(4));
            Log.i("amistades", cursor.getString(5));
            Log.i("resultados", cursor.getString(6));
            String amistadesUsuario = cursor.getString(5);
            String resultadosUsuario = cursor.getString(6);

            usuario = new Usuario(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            usuario.setAmistades(amistadesUsuario);
            usuario.setResultados(resultadosUsuario);


            Log.i("RESULTADOS TEST", usuario.getResultados());

        } catch (Exception e) {

            String err = (e.getMessage() == null) ? "SD Card failed" : e.getMessage();
            Log.e("sdcard-err2:", err);
            Toast.makeText(getApplicationContext(), "Error buscando usuario." + e.getMessage(), Toast.LENGTH_LONG).show();
        }


        prueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PruebasActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario", usuario);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        resultados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, HistorialResultadosActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario", usuario);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        recomendaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecomendacionesActivity.class);
                startActivity(intent);
            }
        });

        tipos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TiposActivity.class);
                startActivity(intent);
            }
        });

        amigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AmistadesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario", usuario);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }
}