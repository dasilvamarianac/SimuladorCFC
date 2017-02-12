package app.robots.simuladorcfc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Senha extends AppCompatActivity {

    private String idUser;
    private EditText senha;
    private EditText nsenha;
    private EditText csenha;

    private RequestQueue requestQueue;
    private static final String URL = "http://signsonapp.com/php_cfc/cUsuario.php";
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senha);
        SharedPreferences prefs = getSharedPreferences("cfc", MODE_PRIVATE);

        idUser = prefs.getString("logado","");

        senha = (EditText) findViewById(R.id.senhaEdt);
        nsenha = (EditText) findViewById(R.id.nsenhaEdt);
        csenha = (EditText) findViewById(R.id.csenhaEdt);

        ImageButton cancelar = (ImageButton) findViewById(R.id.cancelarBtn);
        ImageButton gravar = (ImageButton) findViewById(R.id.gravarBtn);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        gravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptCreate();

            }
        });
    }
    private void attemptCreate() {

        String ssenha = senha.getText().toString();
        String snsenha = nsenha.getText().toString();
        String scsenha = csenha.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(ssenha)){
            Toast toast = new Toast(getApplicationContext());
            ImageView view = new ImageView(getApplicationContext());
            view.setImageResource(R.drawable.esenha);
            toast.setView(view);
            toast.show();
            focusView = senha;
            cancel = true;
        } else if((TextUtils.isEmpty(snsenha)) || (!isPasswordValid(snsenha))){
            Toast toast = new Toast(getApplicationContext());
            ImageView view = new ImageView(getApplicationContext());
            view.setImageResource(R.drawable.esenha);
            toast.setView(view);
            toast.show();
            focusView = nsenha;
            cancel = true;
        }else if(!snsenha.equals(scsenha)){
            Toast toast = new Toast(getApplicationContext());
            ImageView view = new ImageView(getApplicationContext());
            view.setImageResource(R.drawable.esenha);
            toast.setView(view);
            toast.show();
            focusView = csenha;
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
                        if(jsonObject.names().get(0).equals("success")){
                            Toast toast = new Toast(getApplicationContext());
                            ImageView view = new ImageView(getApplicationContext());
                            view.setImageResource(R.drawable.ok);
                            toast.setView(view);
                            toast.show();
                            finish();
                        }else {
                            Toast toast = new Toast(getApplicationContext());
                            ImageView view = new ImageView(getApplicationContext());
                            view.setImageResource(R.drawable.esenha);
                            toast.setView(view);
                            toast.show();
                            senha.requestFocus();
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
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> hashMap = new HashMap<String, String>();
                    hashMap.put("func", "editar");
                    hashMap.put("id",idUser);
                    hashMap.put("senha", senha.getText().toString());
                    hashMap.put("newsenha", nsenha.getText().toString());
                    return hashMap;
                }
            };
            requestQueue.add(request);
        }

    }

    private boolean isPasswordValid(String password) {

        return password.length() >= 8;
    }
}