package com.example.proyectofinal.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.proyectofinal.Entities.Resultado;
import com.example.proyectofinal.Entities.Usuario;
import com.example.proyectofinal.Utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UsersDBHelper extends SQLiteOpenHelper {

    String tablaUsuarios = Utilities.CREAR_TABLA_USUARIOS;

    public UsersDBHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version) {
        super(context, name, cursorFactory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(tablaUsuarios);

        ArrayList<Usuario> adminArr = new ArrayList<>();
        ArrayList<Resultado> resultadoArr = new ArrayList<>();
        Usuario amigoAdmin = new Usuario(590, "Admin", "ad", "admin@admin.com", "456");
        Resultado resultadoBase = new Resultado(0.0, 0.0, 0.0, "N/A");
        adminArr.add(amigoAdmin);
        resultadoArr.add(resultadoBase);

        JSONArray dbAmrray = new JSONArray();

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
            dbAmrray.put(item);
        }


        JSONArray dbRArray = new JSONArray();

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

        String insertAmmigo = dbAmrray.toString();
        String resultadoDomingo = dbRArray.toString();
        Usuario daniel = new Usuario(500, "Daniel Justavino", "dj", "d@d.com", "123465");
        Usuario luis = new Usuario(300, "Luis Torres", "lt", "l@l.com", "456789");
        Usuario domingo = new Usuario(400, "Domingo Stanziola", "ds", "s@s.com", "123789");

        domingo.setAmistades(insertAmmigo);
        domingo.setResultados(resultadoDomingo);

        Resultado rDaniel = new Resultado(1.5, 2.5, 3.0, "Kinestésico");
        Resultado rLuis = new Resultado(2.5, 4.5, 3.0, "Auditivo");
        Resultado rDomingo = new Resultado(3.5, 2.5, 1.0, "Visual");


        JSONArray dbArray = new JSONArray();
        JSONArray resultadosArray = new JSONArray();

        ArrayList<Usuario> amigosDaniel = new ArrayList<>();
        ArrayList<Resultado> resultados = new ArrayList<>();

        resultados.add(rDaniel);
        resultados.add(rLuis);
        resultados.add(rDomingo);


        amigosDaniel.add(domingo);

        for (int i = 0; i < amigosDaniel.size(); i++) {
            JSONObject item = new JSONObject();
            try {
                item.put("id", amigosDaniel.get(i).getId());
                item.put("nombre", amigosDaniel.get(i).getNombre());
                item.put("nombre_usuario", amigosDaniel.get(i).getNombre_usuario());
                item.put("correo", amigosDaniel.get(i).getCorreo());
                item.put("clave", amigosDaniel.get(i).getClave());
                item.put("amistades", amigosDaniel.get(i).getAmistades());
                item.put("resultados", amigosDaniel.get(i).getResultados());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dbArray.put(item);
        }

        for (int i = 0; i < resultados.size(); i++) {
            JSONObject item = new JSONObject();
            try {

                item.put("puntajeVisual", resultados.get(i).getPuntajeVisual());
                item.put("puntajeAuditivo", resultados.get(i).getPuntajeAuditivo());
                item.put("puntajeKine", resultados.get(i).getPuntajeKine());
                item.put("tipoInteligencia", resultados.get(i).getTipoInteligencia());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            resultadosArray.put(item);
        }

        String insertAmigo = dbArray.toString();
        String insertResultados = resultadosArray.toString();

        String insertDaniel = "INSERT INTO " + Utilities.TABLA + " (" + Utilities.NOMBRE + "," + Utilities.NOMBRE_USUARIO + "," + Utilities.CORREO + "," + Utilities.CLAVE + ", " + Utilities.AMISTADES + "," + Utilities.RESULTADOS + ") VALUES ('Daniel Justavino','dj','d@d.com','123','" + insertAmigo + "','" + insertResultados + "')";
        String insertLuis = "INSERT INTO " + Utilities.TABLA + " (" + Utilities.NOMBRE + "," + Utilities.NOMBRE_USUARIO + "," + Utilities.CORREO + "," + Utilities.CLAVE + ", " + Utilities.AMISTADES + "," + Utilities.RESULTADOS + ") VALUES ('Luis Torres','lt','l@l.com','231','" + insertAmigo + "','" + insertResultados + "')";


        db.execSQL(insertDaniel);
        db.execSQL(insertLuis);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Utilities.TABLA);
        onCreate(db);

    }
}
