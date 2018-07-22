package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

import app.com.example.wagner.meupredi.R;

public class PopGlicoses extends ActivityGroup {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_informativo_glicose);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width), (int) (height));

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
