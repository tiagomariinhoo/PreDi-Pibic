package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.NumberPicker;

import app.com.example.wagner.meupredi.R;

/**
 * Created by leandro on 13/04/18.
 */

public class NumberPickerPeso extends Activity {

    NumberPicker pesoPicker = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_picker_peso);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) ( width*.8), (int) (height*.35));

        }

}
