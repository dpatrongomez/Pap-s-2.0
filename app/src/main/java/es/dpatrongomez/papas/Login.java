package es.dpatrongomez.papas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import es.dpatrongomez.papas.modelo.UserData;
import es.dpatrongomez.papas.utils.MyConnectivityChecker;


public class Login extends AppCompatActivity {
    private EditText user, password;
    private TextView recuperar;
    private Intent i;
    private UserData datos;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        if (MyConnectivityChecker.isConnected(this)) {
            setTheme(R.style.AppTheme);
            super.onCreate(savedInstanceState);
            Objects.requireNonNull(getSupportActionBar()).hide();
            setContentView(R.layout.activity_login);

            user = findViewById(R.id.user);
            password = findViewById(R.id.password);
            recuperar = findViewById(R.id.lblRecuperar);


            i = new Intent(Login.this, MainActivity.class);

            user.requestFocus();
            if (user.requestFocus()) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }

            recuperar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    i.putExtra("url", "https://papas.jccm.es/accesopapas/ciudadanoPublico/recuperarClave.xhtml");
                    datos = new UserData(null, null);
                    i.putExtra("datos", datos);
                    startActivity(i);
                }

            });


            Button login = findViewById(R.id.button);
            login.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (user.length() > 0 && password.length() > 0) {
                        datos = new UserData(user.getText().toString(), password.getText().toString());
                        System.out.println(datos);

                        i.putExtra("datos", datos);
                        startActivity(i);
                        finish();

                    } else {
                        Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), R.string.error_campos_vacios, Snackbar.LENGTH_LONG).show();
                    }
                }

            });
        } else {
            setTheme(R.style.AppTheme);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.noconnection);
            Button retry = findViewById(R.id.btnRetry);
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            });
        }


    }
}