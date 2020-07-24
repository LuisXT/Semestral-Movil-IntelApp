package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.proyectofinal.Database.UsersDBHelper;
import com.example.proyectofinal.Entities.Usuario;
import com.example.proyectofinal.Utilities.MisAmigosListAdapter;
import com.example.proyectofinal.Utilities.UsuariosListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MisAmigosActivity extends AppCompatActivity {

    ListView misAmigosLv;
    UsersDBHelper conexion;
    ArrayList<Usuario> amistadesUsuarioActual;
    Button regresar, verUsuarios;
    Usuario usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_amigos);

        conexion = new UsersDBHelper(this, "usuarios_db", null, 14);
        misAmigosLv = findViewById(R.id.misAmigosLV);
        verUsuarios = findViewById(R.id.buttonVerUsuarios);
        Bundle intentExtras = getIntent().getExtras();
        usuarioActual = (Usuario) intentExtras.getSerializable("usuario");


        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MisAmigosActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

        verUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MisAmigosActivity.this, AmistadesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario", usuarioActual);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        getAmigos(usuarioActual);


        MisAmigosListAdapter adapter = new MisAmigosListAdapter(this, R.layout.amigos_view_adapter, amistadesUsuarioActual);
        misAmigosLv.setAdapter(adapter);


    }

    private void getAmigos(Usuario usuarioActual) {
        amistadesUsuarioActual = new ArrayList<>();

        Usuario usuario = null;

        if (usuarioActual.getAmistades() != null) {

            try {
                JSONArray jsonArray = new JSONArray(usuarioActual.getAmistades());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    usuario = new Usuario(Integer.parseInt(jsonObject.getString("id")), jsonObject.getString("nombre"), jsonObject.getString("nombre_usuario"), jsonObject.getString("correo"), jsonObject.getString("clave"));
                    usuario.setAmistades(jsonObject.getString("amistades"));
                    usuario.setResultados(jsonObject.getString("resultados"));
                    amistadesUsuarioActual.add(usuario);
                    Log.i("Nombre getAmigos ", jsonObject.getString("resultados"));
                }
            } catch (Exception e) {
                Log.i("Error filtrarAmigos ", e.getMessage());

            }

        }


    }
}