package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class RecomendacionesActivity extends AppCompatActivity {
    TextView r1,r2,r3,link1,link2,link3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendaciones);

        r1 = findViewById(R.id.textViewRecomendacion1);
        link1 = findViewById(R.id.textViewRecomendacion1Link);
        String linkText1 = "Visite esta <a href='https://sites.google.com/site/conectateyentrenatumente/home/las-inteligencias-multiples/inteligencia-auditiva-musical'> pagina </a>";
        link1.setText(Html.fromHtml(linkText1));
        link1.setMovementMethod(LinkMovementMethod.getInstance());

        r2 = findViewById(R.id.textViewRecomendacion2);
        link2 = findViewById(R.id.textViewRecomendacion2Link);
        String linkText2 = "Visite esta <a href='https://sites.google.com/site/conectateyentrenatumente/home/las-inteligencias-multiples/inteligencia-corporal-kinestesica'> pagina </a>";
        link2.setText(Html.fromHtml(linkText2));
        link2.setMovementMethod(LinkMovementMethod.getInstance());

        r3 = findViewById(R.id.textViewRecomendacion3);
        link3 = findViewById(R.id.textViewRecomendacion3Link);
        String linkText3 = "Visite esta<a href='https://sites.google.com/site/conectateyentrenatumente/home/las-inteligencias-multiples/inteligencia-visual-espacial'>pagina</a>";
        link3.setText(Html.fromHtml(linkText3));
        link3.setMovementMethod(LinkMovementMethod.getInstance());


    }
}