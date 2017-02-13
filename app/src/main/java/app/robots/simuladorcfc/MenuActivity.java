package app.robots.simuladorcfc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

public class MenuActivity extends Activity {

    private PopupWindow pwindo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }


    public boolean initiatePopupWindow(final Context c, View v) {
        try {

            int w = v.getDisplay().getWidth();
            int h = v.getDisplay().getHeight();

            LayoutInflater inflater = (LayoutInflater) c
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.activity_menu,
                    (ViewGroup) v.findViewById(R.id.popup));
            pwindo = new PopupWindow(layout, w, h, true);
            pwindo.setAnimationStyle(R.style.popwin_anim_style);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

            FloatingActionButton Teste = (FloatingActionButton) layout.findViewById(R.id.testeFb);
            Teste.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    pwindo.dismiss();
                    Intent intent = new Intent(c, Simulacao.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    c.startActivity(intent);
                }
            });

            FloatingActionButton Esta = (FloatingActionButton) layout.findViewById(R.id.estaFb);
            Esta.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    pwindo.dismiss();
                    Intent intent = new Intent(c,Resultado.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    c.startActivity(intent);

                }
            });
            FloatingActionButton Senha = (FloatingActionButton) layout.findViewById(R.id.senhaFb);
            Senha.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    pwindo.dismiss();
                    Intent intent = new Intent(c,Senha.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    c.startActivity(intent);

                }
            });
            FloatingActionButton Logout = (FloatingActionButton) layout.findViewById(R.id.sairFb);
            Logout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    SharedPreferences prefs = c.getSharedPreferences("cfc", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("logado", "x");
                    editor.commit();

                    Intent intent = new Intent(c, Login.class);;
                    c.startActivity(intent);

                }
            });

            Button close = (Button) layout.findViewById(R.id.voltarBtn);
            close.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    pwindo.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return(true);
    }

}

