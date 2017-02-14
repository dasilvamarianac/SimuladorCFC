package app.robots.simuladorcfc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Questao extends AppCompatActivity {

    private ArrayList<QuestaoView> itens;
    private RequestQueue requestQueue;
    private static final String URLq = "http://signsonapp.com/php_cfc/cQuestao.php";
    private static final String URLs = "http://signsonapp.com/php_cfc/cSimulacao.php";;
    private StringRequest request;
    private int max = 15, min  =1 , quiz = 0, acertos = 0;
    private String lista;
    private Button btnalt1;
    private Button btnalt2;
    private Button btnalt3;
    private Button btnalt4;
    private String user;

    @Override
    protected void onCreate(Bundle instance) {


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        requestQueue = Volley.newRequestQueue(this);

        super.onCreate(instance);
        setContentView(R.layout.activity_questao);

        SharedPreferences prefs = getSharedPreferences("cfc", MODE_PRIVATE);
        user = prefs.getString("logado", "x");

        btnalt1 = ((Button) this.findViewById(R.id.alt1));
        btnalt2 = ((Button) this.findViewById(R.id.alt2));
        btnalt3 = ((Button) this.findViewById(R.id.alt3));
        btnalt4 = ((Button) this.findViewById(R.id.alt4));

        btnalt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionar("1", btnalt1);
            }
        });
        btnalt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionar("2", btnalt2);
            }
        });
        btnalt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionar("3", btnalt3);
            }
        });
        btnalt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionar("4", btnalt4);
            }
        });

        lista = Questoes(10);

        createList();



    }

    public int aleatoriar() {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public String Questoes(int n){
        String num;
        String lista = Integer.toString(aleatoriar());
        for (int i = 1; i < n; i++) {
            num = Integer.toString(aleatoriar());
            while (lista.indexOf(num) != -1){
                num = Integer.toString(aleatoriar());
            }
            lista = lista + ", " + num;
        }
        Log.i("lista", lista);
        return lista;
    }

    private void createList()
    {


        request = new StringRequest(Request.Method.POST, URLq, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.i("resposta", response);
                try {
                    String enunciado;
                    String alt1;
                    String alt2;
                    String alt3;
                    String alt4;
                    String img;
                    String correto;
                    QuestaoView item;

                    JSONArray array = new JSONArray(response);
                    itens = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject row = array.getJSONObject(i);

                        enunciado = row.getString("enunciado");
                        alt1 = row.getString("alternativa1");
                        alt2 = row.getString("alternativa2");
                        alt3 = row.getString("alternativa3");
                        alt4 = row.getString("alternativa4");
                        correto = row.getString("correta");
                        img = row.getString("imagem");

                        item = new QuestaoView(enunciado, alt1, alt2, alt3, alt4, correto, img, null);

                        itens.add(item);
                    }
                    Exibir(0);

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
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("func", "lista");
                hashMap.put("lista", lista);

                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    public void Exibir(int position)
    {

        QuestaoView item = itens.get(position);

        ((TextView) this.findViewById(R.id.enunciadoTxt)).setText(item.getEnunciado());

        btnalt1.setText(item.getAlt1());
        btnalt2.setText(item.getAlt2());
        btnalt3.setText(item.getAlt3());
        btnalt4.setText(item.getAlt4());



        String urlimg = item.getImg();

        if (urlimg.isEmpty()) {
            ImageView img = (ImageView) this.findViewById(R.id.placaImg);
            img.setMaxWidth(0);
        }else{
            ImageView img = (ImageView) this.findViewById(R.id.placaImg);
            Picasso.with(getApplicationContext()).load(urlimg).into(img);
        }
    }

    public void selecionar(String escolha, Button btn){

        btn.setBackgroundColor(Color.GRAY);

        itens.get(quiz).setEscolha(escolha);
        if(itens.get(quiz).getCorreto().equals(escolha)){
            acertos++;
        }
        quiz++;
        if (quiz < 10) {
            Exibir(quiz);
        }else{
            AddSimulado();
        }
        btn.setBackgroundColor(Color.TRANSPARENT);

    }

    private void AddSimulado() {
        request = new StringRequest(Request.Method.POST, URLs, new Response.Listener<String>() {

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

                            Intent intent = new Intent(getApplicationContext(), Resultado.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("acertos", Integer.toString(acertos));
                            intent.putExtra("itens", (ArrayList<QuestaoView>)itens);
                            startActivity(intent);

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
                    hashMap.put("id", user);
                    hashMap.put("acertos",Integer.toString(acertos));


                    return hashMap;
                }
            };
            requestQueue.add(request);


    }

}