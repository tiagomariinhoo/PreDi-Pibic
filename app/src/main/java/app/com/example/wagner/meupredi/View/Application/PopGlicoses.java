package app.com.example.wagner.meupredi.View.Application;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TabHost;

import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.Tabs.Informativo.TabGlicosesApos75g;
import app.com.example.wagner.meupredi.View.Application.Tabs.Informativo.TabGlicosesHemoglobinaGlicada;
import app.com.example.wagner.meupredi.View.Application.Tabs.Informativo.TabGlicosesJejum;

public class PopGlicoses extends ActivityGroup {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_informativo_glicose);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        //getWindow().setLayout((int) (width*8), (int) (height*.50));

        TabHost abas = (TabHost) findViewById(R.id.tabhostGlicoses);
        abas.setup(this.getLocalActivityManager());

        TabHost.TabSpec descritor = abas.newTabSpec("aba1");

        Intent intent = new Intent(this, TabGlicosesJejum.class);

        descritor.setContent(intent);
        descritor.setIndicator("JEJUM");
        abas.addTab(descritor);

        descritor = abas.newTabSpec("aba2");

        intent = new Intent(this, TabGlicosesApos75g.class);

        descritor.setContent(intent);
        descritor.setIndicator("APÓS 75g");
        abas.addTab(descritor);

        descritor = abas.newTabSpec("aba3");

        intent = new Intent(this, TabGlicosesHemoglobinaGlicada.class);

        descritor.setContent(intent);
        descritor.setIndicator("GLICADA");
        abas.addTab(descritor);
    }
}
