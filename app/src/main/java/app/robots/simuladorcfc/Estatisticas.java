package app.robots.simuladorcfc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Estatisticas extends AppCompatActivity {
    private MenuActivity menui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);

        ImageButton menu = (ImageButton) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                menui = new MenuActivity();
                menui.initiatePopupWindow(Estatisticas.this, v);
            }
        });
    }
}
