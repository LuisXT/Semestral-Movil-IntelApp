package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.proyectofinal.Database.UsersDBHelper;
import com.example.proyectofinal.Entities.Resultado;
import com.example.proyectofinal.Entities.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PruebasActivity extends AppCompatActivity {
    ScrollView sVPreguntas;
    Button enviar;
    Spinner visual1, visual2, visual3, visual4, visual5, auditivo1, auditivo2, auditivo3, auditivo4, auditivo5, kine1, kine2, kine3, kine4, kine5;
    ArrayList<String> respuestas = new ArrayList<>();
    Double resultadoVisual = 0.0, resultadoAuditivo = 0.0, resultadoKine = 0.0;
    String tipoInteligencia;
    UsersDBHelper conexion;
    ArrayList<Resultado> resultados = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas);

        conexion = new UsersDBHelper(this, "usuarios_db", null, 14);

        enviar = findViewById(R.id.Enviar);
        sVPreguntas = findViewById(R.id.PreguntasScrollView);

        visual1 = findViewById(R.id.visual1);
        visual2 = findViewById(R.id.visual2);
        visual3 = findViewById(R.id.visual3);
        visual4 = findViewById(R.id.visual4);
        visual5 = findViewById(R.id.visual5);


        auditivo1 = findViewById(R.id.auditivo1);
        auditivo2 = findViewById(R.id.auditivo2);
        auditivo3 = findViewById(R.id.auditivo3);
        auditivo4 = findViewById(R.id.auditivo4);
        auditivo5 = findViewById(R.id.auditivo5);

        kine1 = findViewById(R.id.kine1);
        kine2 = findViewById(R.id.kine2);
        kine3 = findViewById(R.id.kine3);
        kine4 = findViewById(R.id.kine4);
        kine5 = findViewById(R.id.kine5);

        respuestas.add("Seleccione una respuesta");
        respuestas.add("No");
        respuestas.add("Mas o menos");
        respuestas.add("Si");


        setVisual();
        setAuditivo();
        setKine();


        Bundle userInfo = getIntent().getExtras();
        final Usuario usuario = (Usuario) userInfo.getSerializable("usuario");

        String resultadosPrevios = usuario.getResultados();

        try {
            JSONArray jsonArray = new JSONArray(resultadosPrevios);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                resultados.add(new Resultado(Double.parseDouble(jsonObject.getString("puntajeVisual")), Double.parseDouble(jsonObject.getString("puntajeAuditivo")), Double.parseDouble(jsonObject.getString("puntajeKine")), jsonObject.getString("tipoInteligencia")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = conexion.getWritableDatabase();
                Intent intent = new Intent(PruebasActivity.this, ResultadosActivity.class);
                Bundle bundle = new Bundle();
                HashMap<String, Double> puntajes = new HashMap<>();
                puntajes.put("resultadoVisual", resultadoVisual);
                puntajes.put("resultadoAuditivo", resultadoAuditivo);
                puntajes.put("resultadoKine", resultadoKine);


                if (resultadoVisual > resultadoAuditivo && resultadoVisual > resultadoKine) {
                    tipoInteligencia = "Visual";


                } else if (resultadoAuditivo > resultadoVisual && resultadoAuditivo > resultadoKine) {
                    tipoInteligencia = "Auditiva";

                } else if (resultadoAuditivo == resultadoKine) {
                    tipoInteligencia = "Auditiva y Kinestésica";

                } else if (resultadoVisual == resultadoAuditivo) {
                    tipoInteligencia = "Auditiva y Visual";


                } else if (resultadoVisual == resultadoKine) {
                    tipoInteligencia = "Visual y Kinestésica";

                } else {
                    tipoInteligencia = "Kinestésica";
                }

                if (resultadoAuditivo > 1.5 || resultadoVisual > 1.5 || resultadoKine > 1.5) {

                    resultados.add(new Resultado(resultadoVisual, resultadoAuditivo, resultadoKine, tipoInteligencia));
                    JSONArray jsonArray = new JSONArray();
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

                        jsonArray.put(item);
                    }
                    try {


                        String insert_arr = jsonArray.toString();
                        String[] params = {usuario.getId().toString()};
                        Log.i("MY QUERY!!!", "UPDATE usuarios SET resultados= '" + insert_arr + "' WHERE id=?" + usuario.getId().toString());
                        db.execSQL("UPDATE usuarios SET resultados= '" + insert_arr + "' WHERE id=?", params);
                        db.close();


                        bundle.putSerializable("puntajes", puntajes);
                        bundle.putSerializable("tipoInteligencia", tipoInteligencia);
                        intent.putExtras(bundle);
                        startActivity(intent);


                    } catch (Exception e) {
                        Log.i("Error", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Debe responder las preguntas!", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    private void setVisual() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, respuestas);

        visual1.setAdapter(adapter);

        visual1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Respuestas seleccionada", adapterView.getItemAtPosition(i).toString());

                if (adapterView.getItemAtPosition(i).toString().equals("Si")) {

                    resultadoVisual += 1.0;

                } else if (adapterView.getItemAtPosition(i).toString().equals("Mas o menos")) {
                    resultadoVisual += 0.5;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una respuesta!.", Toast.LENGTH_LONG).show();
            }
        });

        visual2.setAdapter(adapter);

        visual2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Respustas seleccionada", adapterView.getItemAtPosition(i).toString());

                if (adapterView.getItemAtPosition(i).toString().equals("Si")) {

                    resultadoVisual += 1.0;

                } else if (adapterView.getItemAtPosition(i).toString().equals("Mas o menos")) {
                    resultadoVisual += 0.5;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una respuesta!.", Toast.LENGTH_LONG).show();
            }
        });

        visual3.setAdapter(adapter);

        visual3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Respustas seleccionada", adapterView.getItemAtPosition(i).toString());

                if (adapterView.getItemAtPosition(i).toString().equals("Si")) {

                    resultadoVisual += 1.0;

                } else if (adapterView.getItemAtPosition(i).toString().equals("Mas o menos")) {
                    resultadoVisual += 0.5;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una respuesta!.", Toast.LENGTH_LONG).show();
            }
        });

        visual4.setAdapter(adapter);

        visual4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Respustas seleccionada", adapterView.getItemAtPosition(i).toString());

                if (adapterView.getItemAtPosition(i).toString().equals("Si")) {

                    resultadoVisual += 1.0;

                } else if (adapterView.getItemAtPosition(i).toString().equals("Mas o menos")) {
                    resultadoVisual += 0.5;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una respuesta!.", Toast.LENGTH_LONG).show();
            }
        });

        visual5.setAdapter(adapter);

        visual5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Respustas seleccionada", adapterView.getItemAtPosition(i).toString());

                if (adapterView.getItemAtPosition(i).toString().equals("Si")) {

                    resultadoVisual += 1.0;

                } else if (adapterView.getItemAtPosition(i).toString().equals("Mas o menos")) {
                    resultadoVisual += 0.5;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una respuesta!.", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setAuditivo() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, respuestas);

        auditivo1.setAdapter(adapter);

        auditivo1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Respustas seleccionada", adapterView.getItemAtPosition(i).toString());

                if (adapterView.getItemAtPosition(i).toString().equals("Si")) {

                    resultadoAuditivo += 1.0;

                } else if (adapterView.getItemAtPosition(i).toString().equals("Mas o menos")) {
                    resultadoVisual += 0.5;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una respuesta!.", Toast.LENGTH_LONG).show();
            }
        });

        auditivo2.setAdapter(adapter);

        auditivo2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Respustas seleccionada", adapterView.getItemAtPosition(i).toString());
                if (adapterView.getItemAtPosition(i).toString().equals("Si")) {

                    resultadoAuditivo += 1.0;

                } else if (adapterView.getItemAtPosition(i).toString().equals("Mas o menos")) {
                    resultadoVisual += 0.5;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una respuesta!.", Toast.LENGTH_LONG).show();

            }
        });

        auditivo3.setAdapter(adapter);

        auditivo3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Respustas seleccionada", adapterView.getItemAtPosition(i).toString());
                if (adapterView.getItemAtPosition(i).toString().equals("Si")) {

                    resultadoAuditivo += 1.0;

                } else if (adapterView.getItemAtPosition(i).toString().equals("Mas o menos")) {
                    resultadoVisual += 0.5;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una respuesta!.", Toast.LENGTH_LONG).show();

            }
        });

        auditivo4.setAdapter(adapter);

        auditivo4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Respustas seleccionada", adapterView.getItemAtPosition(i).toString());
                if (adapterView.getItemAtPosition(i).toString().equals("Si")) {

                    resultadoAuditivo += 1.0;

                } else if (adapterView.getItemAtPosition(i).toString().equals("Mas o menos")) {
                    resultadoVisual += 0.5;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una respuesta!.", Toast.LENGTH_LONG).show();
            }
        });

        auditivo5.setAdapter(adapter);

        auditivo5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Respustas seleccionada", adapterView.getItemAtPosition(i).toString());
                if (adapterView.getItemAtPosition(i).toString().equals("Si")) {

                    resultadoAuditivo += 1.0;

                } else if (adapterView.getItemAtPosition(i).toString().equals("Mas o menos")) {
                    resultadoVisual += 0.5;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una respuesta!.", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void setKine() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, respuestas);

        kine1.setAdapter(adapter);

        kine1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Respustas seleccionada", adapterView.getItemAtPosition(i).toString());

                if (adapterView.getItemAtPosition(i).toString().equals("Si")) {

                    resultadoKine += 1.0;

                } else if (adapterView.getItemAtPosition(i).toString().equals("Mas o menos")) {
                    resultadoVisual += 0.5;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una respuesta!.", Toast.LENGTH_LONG).show();
            }
        });

        kine2.setAdapter(adapter);

        kine2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Respustas seleccionada", adapterView.getItemAtPosition(i).toString());

                if (adapterView.getItemAtPosition(i).toString().equals("Si")) {

                    resultadoKine += 1.0;

                } else if (adapterView.getItemAtPosition(i).toString().equals("Mas o menos")) {
                    resultadoVisual += 0.5;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una respuesta!.", Toast.LENGTH_LONG).show();

            }
        });

        kine3.setAdapter(adapter);

        kine3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Respustas seleccionada", adapterView.getItemAtPosition(i).toString());

                if (adapterView.getItemAtPosition(i).toString().equals("Si")) {

                    resultadoKine += 1.0;

                } else if (adapterView.getItemAtPosition(i).toString().equals("Mas o menos")) {
                    resultadoVisual += 0.5;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una respuesta!.", Toast.LENGTH_LONG).show();

            }
        });

        kine4.setAdapter(adapter);

        kine4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Respustas seleccionada", adapterView.getItemAtPosition(i).toString());

                if (adapterView.getItemAtPosition(i).toString().equals("Si")) {

                    resultadoKine += 1.0;

                } else if (adapterView.getItemAtPosition(i).toString().equals("Mas o menos")) {
                    resultadoVisual += 0.5;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una respuesta!.", Toast.LENGTH_LONG).show();

            }
        });

        kine5.setAdapter(adapter);
        kine5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Respustas seleccionada", adapterView.getItemAtPosition(i).toString());

                if (adapterView.getItemAtPosition(i).toString().equals("Si")) {

                    resultadoKine += 1.0;

                } else if (adapterView.getItemAtPosition(i).toString().equals("Mas o menos")) {
                    resultadoVisual += 0.5;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "Debe seleccionar una respuesta!.", Toast.LENGTH_LONG).show();

            }
        });

    }
}