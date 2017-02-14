package app.robots.simuladorcfc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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

public class Resultado extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private ListView listView;
    private ResultadoAdapter adapterListView;
    private ArrayList<QuestaoView> itens;
    private RequestQueue requestQueue;
    private static final String URLq = "http://signsonapp.com/php_cfc/cQuestao.php";
    private StringRequest request;

    @Override
    protected void onCreate(Bundle instance) {



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        requestQueue = Volley.newRequestQueue(this);

        super.onCreate(instance);
        setContentView(R.layout.activity_resultado);

        listView = (ListView) findViewById(R.id.simuladorListview);
        listView.setOnItemClickListener(this);

        TextView total = (TextView) findViewById(R.id.totalTxt);

        Intent intent = getIntent();
        itens =  (ArrayList<QuestaoView>)getIntent().getSerializableExtra("itens");
        total.setText("Total: " + intent.getStringExtra("acertos"));

        createListView();

    }

    private void createListView()
    {
        SharedPreferences prefs = getSharedPreferences("cfc", MODE_PRIVATE);
        final String user = prefs.getString("logado", "x");

        adapterListView = new ResultadoAdapter(getApplication(), itens);

        listView.setAdapter(adapterListView);
        listView.setCacheColorHint(Color.TRANSPARENT);
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
    {
        QuestaoView item = adapterListView.getItem(arg2);
        Log.i("teste", item.getAlt1());
    }
}