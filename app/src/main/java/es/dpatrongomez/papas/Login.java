package es.dpatrongomez.papas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Login extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login);

        EditText user = findViewById(R.id.user);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (user.length()>0 && password.length()>0) {
                    Intent i = new Intent(Login.this, MainActivity.class);
                    i.putExtra("user", user.getText());
                    i.putExtra("paswword", password.getText());
                    startActivity(i);
                }
            }

        });






    }
}