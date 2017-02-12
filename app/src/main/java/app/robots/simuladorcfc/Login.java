package app.robots.simuladorcfc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {

    private EditText usuario;
    private EditText senha;
    private TextView status;
    private RequestQueue requestQueue;
    private static final String URL = "http://signsonapp.com/php_cfc/cUsuario.php";
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //layout items
        usuario = (EditText) findViewById(R.id.usuarioEdt);
        senha = (EditText) findViewById(R.id.senhaEdt);

        final ImageButton login = (ImageButton) findViewById(R.id.loginBtn);
        final ImageButton novo = (ImageButton) findViewById(R.id.novoBtn);
        final TextView rsenha = (TextView) findViewById(R.id.senhaTxt);

        requestQueue = Volley.newRequestQueue(this);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        rsenha.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptEmail();
            }
        });

        novo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Usuario.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("usuario", usuario.getText().toString());
                intent.putExtra("senha", senha.getText().toString());
                getApplicationContext().startActivity(intent);
            }
        });
    }
    private void attemptLogin() {
        // Store values at the time of the login attempt.
        String email = usuario.getText().toString();
        String passwords = senha.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if ((TextUtils.isEmpty(email))||(!isEmailValid(email))) {
            Toast toast = new Toast(getApplicationContext());
            ImageView view = new ImageView(getApplicationContext());
            view.setImageResource(R.drawable.eemail);
            toast.setView(view);
            toast.show();
            focusView = usuario;
            cancel = true;
        } else if(TextUtils.isEmpty(passwords)){
            Toast toast = new Toast(getApplicationContext());
            ImageView view = new ImageView(getApplicationContext());
            view.setImageResource(R.drawable.esenha);
            toast.setView(view);
            toast.show();
            focusView = senha;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.i("resposta", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (!(jsonObject.names().get(0).equals("error"))) {

                            SharedPreferences prefs = getSharedPreferences("cfc", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("logado", jsonObject.getString("id"));
                            editor.putString("nome", jsonObject.getString("nome"));
                            editor.commit();
                            startActivity(new Intent(getApplicationContext(), Senha.class));

                        } else {

                            Toast toast = new Toast(getApplicationContext());
                            ImageView view = new ImageView(getApplicationContext());
                            if (jsonObject.getString("error").equals("Senha")) {
                                view.setImageResource(R.drawable.esenha);
                                senha.requestFocus();
                            }else{
                                view.setImageResource(R.drawable.eemail);
                                usuario.requestFocus();
                            }
                            toast.setView(view);
                            toast.show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Erro de conexão " + error, Toast.LENGTH_SHORT).show();
                }
            }){
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("func", "login");
                    hashMap.put("senha", senha.getText().toString());
                    hashMap.put("email", usuario.getText().toString());
                    return hashMap;
                }
            };
            requestQueue.add(request);
        }
    }

    private void attemptEmail() {
        // Store values at the time of the login attempt.
        String email = usuario.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if ((TextUtils.isEmpty(email))||(!isEmailValid(email))) {
            Toast toast = new Toast(getApplicationContext());
            ImageView view = new ImageView(getApplicationContext());
            view.setImageResource(R.drawable.eemail);
            toast.setView(view);
            toast.show();
            focusView = usuario;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {

            request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.names().get(0).equals("success")) {
                            Toast toast = new Toast(getApplicationContext());
                            ImageView view = new ImageView(getApplicationContext());
                            view.setImageResource(R.drawable.ok);
                            toast.setView(view);
                            toast.show();

                        } else {
                            Toast toast = new Toast(getApplicationContext());
                            ImageView view = new ImageView(getApplicationContext());
                            view.setImageResource(R.drawable.eemail);
                            toast.setView(view);
                            toast.show();
                            usuario.requestFocus();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Erro de conexão " + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("func", "email");
                    hashMap.put("email", usuario.getText().toString());
                    return hashMap;
                }
            };
            requestQueue.add(request);
        }
    }


    private boolean isEmailValid(String email) {

        return email.contains("@") && email.contains(".");
    }

}
