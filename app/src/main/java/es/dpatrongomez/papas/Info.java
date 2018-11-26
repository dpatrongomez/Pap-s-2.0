package es.dpatrongomez.papas;

import androidx.appcompat.app.AppCompatActivity;
import me.jfenn.attribouter.Attribouter;

import android.os.Bundle;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Attribouter.from(this)
                .withFile(R.xml.about)
                .show();

        finish();
    }
}
