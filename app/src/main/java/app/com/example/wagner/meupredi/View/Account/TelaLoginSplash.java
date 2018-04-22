package app.com.example.wagner.meupredi.View.Account;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import app.com.example.wagner.meupredi.R;

/**
 * Created by LeandroDias1 on 05/10/2017.
 */

public class TelaLoginSplash extends AppCompatActivity{

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(TelaLoginSplash.this, TelaLogin.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

}
