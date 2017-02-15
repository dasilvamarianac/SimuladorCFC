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
import android.widget.ImageButton;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

import static android.R.attr.data;

public class Estatisticas extends AppCompatActivity {
    private MenuActivity menui;
    private RequestQueue requestQueue;
    private static final String URL = "http://signsonapp.com/php_cfc/cSimulacao.php";;
    private StringRequest request;
    List<PointValue> values = new ArrayList<PointValue>();
    List<PointValue> media = new ArrayList<PointValue>();
    private String user;
    private String cont;
    private String mediau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        requestQueue = Volley.newRequestQueue(this);

        SharedPreferences prefs = getSharedPreferences("cfc", MODE_PRIVATE);
        user = prefs.getString("logado", "x");

        ImageButton menu = (ImageButton) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                menui = new MenuActivity();
                menui.initiatePopupWindow(Estatisticas.this, v);
            }
        });

        //generateInitialLineData();
        createEstat();


    }

    /**
     * Generates initial data for line chart. At the begining all Y values are equals 0. That will change when user
     * will select value on column chart.
     */



    public void draw() {

        String decimalPattern = "#.##";
        DecimalFormat decimalFormat = new DecimalFormat(decimalPattern);

        LineChartView lineChartView = (LineChartView) findViewById(R.id.chart);


        List<PointValue> media = new ArrayList<PointValue>();

        /*PointValue tempPointValue;
        for (int i = 0; i <= 10; i++) {
            tempPointValue = new PointValue(i,i);
            tempPointValue.setLabel(Integer.toString(i));
            values.add(tempPointValue);

        }

        for (int i = 0; i <= 10; i++) {
            tempPointValue = new PointValue(i,6);
            tempPointValue.setLabel(Integer.toString(i));
            media.add(tempPointValue);
        }*/

        int con = Integer.parseInt(cont);

        for (float i = 0; i < Integer.parseInt(cont); i++) {
            float mediadec = Float.parseFloat(mediau);
            media.add(new PointValue(i+1,mediadec));

        }

        Line line = new Line(values)
                .setColor(Color.BLACK)
                .setCubic(false)
                .setHasPoints(true);

        Line medial = new Line(media)
                .setColor(Color.YELLOW)
                .setCubic(false)
                .setHasPoints(true);

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);
        lines.add(medial);

        LineChartData data = new LineChartData();
        data.setLines(lines);


        List<AxisValue> axisValuesForY = new ArrayList<>();
        AxisValue tempAxisValue;


        for (float i = 0; i < 10; i ++){
            tempAxisValue = new AxisValue(i);
            tempAxisValue.setLabel(""+i);
            axisValuesForY.add(tempAxisValue);
        }

        Axis yAxis = new Axis(axisValuesForY);
        data.setAxisYLeft(yAxis);


        lineChartView.setLineChartData(data);

    }

    private void createList()
    {


        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.i("resposta", response);
                try {
                    String id;
                    String acertos;

                    JSONArray array = new JSONArray(response);

                    for (int i = 0; i < array.length(); i++) {

                        JSONObject row =   array.getJSONObject(i);


                        id = row.getString("idSimulacao");
                        acertos = row.getString("acertos");


                        values.add(new PointValue(i+1,Integer.parseInt(acertos)));



                    };
                    draw();

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
                hashMap.put("id", user);

                return hashMap;
            }
        };
        requestQueue.add(request);
    }

    private void createEstat()
    {


        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.i("resposta", response);
                try {


                    JSONObject row = new JSONObject(response);

                     cont = row.getString("contador");
                     mediau = row.getString("media");

                     TextView m = (TextView) findViewById(R.id.mediaTxt);
                     m.setText("Media: " + mediau);

                     TextView c = (TextView) findViewById(R.id.tentativasTxt);
                     c.setText("Tentativas: " +  cont);

                    createList();


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
                hashMap.put("func", "estatistica");
                hashMap.put("id", user);

                return hashMap;
            }
        };
        requestQueue.add(request);
    }

}
