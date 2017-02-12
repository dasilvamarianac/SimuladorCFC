package app.robots.simuladorcfc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class Usuario extends AppCompatActivity {

    private String type;
    private String idUser;
    private EditText usuario;
    private EditText senha;
    private EditText nome;
    private ImageView imge, imgs;
    private TextView status;
    private RequestQueue requestQueue;
    private static final String URL = "http://signsonapp.com/php_cfc/cUsuario.php";
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        SharedPreferences prefs = getSharedPreferences("cfc", MODE_PRIVATE);
        idUser = prefs.getString("logado","");

        usuario = (EditText) findViewById(R.id.emailEdt);
        senha = (EditText) findViewById(R.id.senhaEdt);
        nome = (EditText) findViewById(R.id.nomeEdt);

        usuario.setText(getIntent().getStringExtra("usuario"));
        senha.setText(getIntent().getStringExtra("senha"));


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
        String email = usuario.getText().toString();
        String passwords = senha.getText().toString();
        String nomeu = nome.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(nomeu)) {
            focusView = nome;
            cancel = true;
        }else if ((TextUtils.isEmpty(email))||(!isEmailValid(email))) {
            Toast toast = new Toast(getApplicationContext());
            ImageView view = new ImageView(getApplicationContext());
            view.setImageResource(R.drawable.eemail);
            toast.setView(view);
            toast.show();
            focusView = usuario;
            cancel = true;
        } else if((TextUtils.isEmpty(passwords))||(!isPasswordValid(passwords))){
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
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if(jsonObject.names().get(0).equals("success")){
                            Toast toast = new Toast(getApplicationContext());
                            ImageView view = new ImageView(getApplicationContext());
                            view.setImageResource(R.drawable.ok);
                            toast.setView(view);
                            toast.show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }else {
                            Toast toast = new Toast(getApplicationContext());
                            ImageView view = new ImageView(getApplicationContext());
                            view.setImageResource(R.drawable.euser);
                            toast.setView(view);
                            toast.show();
                            finish();
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
                    hashMap.put("func", "incluir");
                    hashMap.put("nome",nome.getText().toString());
                    hashMap.put("email",usuario.getText().toString());
                    hashMap.put("senha",senha.getText().toString());

                    return hashMap;
                }
            };
            requestQueue.add(request);
        }

    }

    private boolean isEmailValid(String email) {

        return email.contains("@") && email.contains(".");
    }

    private boolean isPasswordValid(String password) {

        return password.length() >= 8;
    }
}
