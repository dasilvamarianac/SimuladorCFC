package app.robots.simuladorcfc;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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

public class Simulacao  extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private ListView listView;
    private SimulacaoAdapter adapterListView;
    private ArrayList<QuestaoView> itens;
    private RequestQueue requestQueue;
    private static final String URLq = "http://signsonapp.com/php_cfc/cQuestao.php";
    private static final String URLs = "http://signsonapp.com/php_cfc/cSimulacao.php";;
    private StringRequest request;
    @Override
    protected void onCreate(Bundle instance) {


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        requestQueue = Volley.newRequestQueue(this);

        super.onCreate(instance);
        setContentView(R.layout.activity_simulacao);

        Button menu = (Button) findViewById(R.id.btnTeste);
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("teste", "entrooou");
            }
        });


        listView = (ListView) findViewById(R.id.simuladorListview);
        listView.setOnItemClickListener(this);

        createListView();

    }

    private void createListView()
    {
        SharedPreferences prefs = getSharedPreferences("cfc", MODE_PRIVATE);
        final String user = prefs.getString("logado", "x");

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

                        item = new QuestaoView(enunciado, alt1, alt2, alt3, alt4, correto, img);

                        itens.add(item);
                    }

                    adapterListView = new SimulacaoAdapter(getApplication(), itens);

                    listView.setAdapter(adapterListView);
                    listView.setCacheColorHint(Color.TRANSPARENT);

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
                hashMap.put("lista","1, 2, 5, 3, 4, 7");

                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {
        QuestaoView item = adapterListView.getItem(arg2);
        Log.i("teste", item.getAlt1());
    }
}