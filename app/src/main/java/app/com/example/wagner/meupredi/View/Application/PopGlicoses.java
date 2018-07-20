package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import app.com.example.wagner.meupredi.R;

public class PopGlicoses extends Activity {

    RadioGroup radioGroupGlicose;
    RadioButton radioButton;
    private TextView coluna1, coluna2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_informativo_glicose);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) ( width*.95), (int) (height*.35));

        radioGroupGlicose = (RadioGroup) findViewById(R.id.radio_group_glicoses);
        coluna1 = (TextView) findViewById(R.id.text_table_glicoses_col1);
        coluna2 = (TextView) findViewById(R.id.text_table_glicoses_col2);

        radioButton = (RadioButton) findViewById(radioGroupGlicose.getCheckedRadioButtonId());
        /*
        if(radioButton.getText() == "Jejum" & radioButton.isChecked()){
            coluna1.setText("Glicose em Jejum");
        }
        else if(radioButton.getText() == "Após 75g" & radioButton.isChecked()){
            coluna1.setText("Glicose após 75g");
        }
        else if(radioButton.getText() == "Hemoglobina Glicolisada" & radioButton.isChecked()){
            coluna1.setText("Hemoglobina Glicolisada");
        }*/
    }

    public void checkButton(View v){
        int radioId = radioGroupGlicose.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(radioId);
    }
}
