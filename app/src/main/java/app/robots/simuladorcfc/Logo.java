package app.robots.simuladorcfc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class Logo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {

                Login();

            }

        }, 1500);

    }

    protected void Login() {
        SharedPreferences prefs = getSharedPreferences("cfc", MODE_PRIVATE);
        String logado = prefs.getString("logado", "x");
        Log.i("Login", "[" + logado + "]");

        if (logado.equals("x")) {
            startActivity(new Intent(getApplicationContext(), Login.class));
        } else {
            Intent intent;
            intent = new Intent(getApplicationContext(), Simulacao.class);
            startActivity(intent);
        }
    }
}