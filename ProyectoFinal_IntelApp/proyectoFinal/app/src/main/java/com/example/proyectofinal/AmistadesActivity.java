package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.proyectofinal.Database.UsersDBHelper;
import com.example.proyectofinal.Entities.Usuario;
import com.example.proyectofinal.Utilities.UsuariosListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AmistadesActivity extends AppCompatActivity {
    UsersDBHelper conexion;
    ListView usuariosListview;
    ArrayList<Usuario> usuarios;
    ArrayList<Usuario> amistadesUsuarioActual;
    ArrayList<String> nombresUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amistades);
        conexion = new UsersDBHelper(this, "usuarios_db", null, 14);

        Bundle intentExtras = getIntent().getExtras();
        final Usuario usuarioActual = (Usuario) intentExtras.getSerializable("usuario");

        usuariosListview = findViewById(R.id.listviewUsuarios);

        getAmigos(usuarioActual);
        consultarBd(usuarioActual);


        UsuariosListAdapter adapter = new UsuariosListAdapter(this, R.layout.adapter_view_layout, usuarios);
        usuariosListview.setAdapter(adapter);
        usuariosListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent(AmistadesActivity.this, AgregarAmigoActivity.class);
                Bundle userInfo = getIntent().getExtras();
                Usuario usuario = (Usuario) userInfo.getSerializable("usuario");
                Usuario usuarioElegido = new Usuario(usuarios.get(i).getId(), usuarios.get(i).getNombre(), usuarios.get(i).getNombre_usuario(), usuarios.get(i).getCorreo(), usuarios.get(i).getClave());
                usuario.setAmistades(usuarios.get(i).getAmistades());
                usuario.setResultados(usuarios.get(i).getResultados());
                Bundle bundle = new Bundle();
                bundle.putSerializable("usuario", usuario);
                bundle.putSerializable("usuarioElegido", usuarioElegido);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void getAmigos(Usuario usuarioActual) {
        amistadesUsuarioActual = new ArrayList<>();
        nombresUsuario = new ArrayList<>();
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
                    nombresUsuario.add(usuario.getNombre_usuario());
                    Log.i("Nombre: ", jsonObject.getString("nombre"));
                }
            } catch (Exception e) {
                Log.i("Error filtrarAmigos ", e.getMessage());

            }

        }


    }

    private void consultarBd(Usuario usuarioActual) {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Usuario usuario = null;
        usuarios = new ArrayList<Usuario>();

        try {

            String[] params = {usuarioActual.getId().toString()};
            Cursor cursor = db.rawQuery("SELECT id,nombre,nombre_usuario,correo,clave,amistades,resultados FROM usuarios WHERE id !=?", params);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    usuario = new Usuario(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))), cursor.getString(cursor.getColumnIndex("nombre")), cursor.getString(cursor.getColumnIndex("nombre_usuario")), cursor.getString(cursor.getColumnIndex("correo")), cursor.getString(cursor.getColumnIndex("clave")));
                    usuario.setResultados(cursor.getString(cursor.getColumnIndex("resultados")));
                    usuario.setAmistades(cursor.getString(cursor.getColumnIndex("amistades")));
                    usuarios.add(usuario);


                    cursor.moveToNext();
                }
                cursor.close();
                db.close();
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error buscando usuarios.", Toast.LENGTH_LONG).show();
        }

    }


}