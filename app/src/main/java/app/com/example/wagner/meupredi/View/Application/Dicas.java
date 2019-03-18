package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import app.com.example.wagner.meupredi.R;

/**
 * Created by leandro on 17/04/18.
 */

public class Dicas extends Activity {

    private TextView titulo, subtitulo, mensagem;
    private ImageView imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.croqui);


        //DisplayMetrics dm = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(dm);

        //int width = dm.widthPixels;
        //int height = dm.heightPixels;

        //getWindow().setLayout((int) ( width*.8), (int) (height*.75));


    }
}
