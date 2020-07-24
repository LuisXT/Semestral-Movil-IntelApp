package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

public class ResultadosActivity extends AppCompatActivity {

    TextView resultadoVisual, resultadoAuditivo, resultadoKine, resultadoTipoI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        resultadoVisual = findViewById(R.id.textViewResultadoVisual);
        resultadoAuditivo = findViewById(R.id.textViewResultadoAuditivo);
        resultadoKine = findViewById(R.id.textViewResultadoKine);
        resultadoTipoI = findViewById(R.id.textViewResultadoTipo);

        Bundle resultadosUsuario = getIntent().getExtras();
        HashMap<String, String> puntajes = (HashMap<String, String>) resultadosUsuario.getSerializable("puntajes");
        String resultadoTipo = (String) resultadosUsuario.getSerializable("tipoInteligencia");

        resultadoVisual.setText(String.valueOf(puntajes.get("resultadoVisual")));
        resultadoAuditivo.setText(String.valueOf(puntajes.get("resultadoAuditivo")));
        resultadoKine.setText(String.valueOf(puntajes.get("resultadoKine")));
        resultadoTipoI.setText(resultadoTipo);

    }
}