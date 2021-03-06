package app.robots.simuladorcfc;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ResultadoAdapter extends BaseAdapter {

    public LayoutInflater mInflater;
    public ArrayList<QuestaoView> itens;
    public String urlimg;
    public QuestaoView item;

    public ResultadoAdapter(Context context, ArrayList<QuestaoView> itens)
    {
        this.itens = itens;
        mInflater = LayoutInflater.from(context);

    }

    public int getCount()
    {
        return itens.size();
    }

    public QuestaoView getItem(int position)
    {
        return itens.get(position);
    }


    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent)
    {

        item = itens.get(position);

        view = mInflater.inflate(R.layout.resultado_view, null);

        ((TextView) view.findViewById(R.id.enunciadoTxt)).setText(item.getEnunciado());
        Button btnalt1 = ((Button) view.findViewById(R.id.alt1));
        Button btnalt2 = ((Button) view.findViewById(R.id.alt2));
        Button btnalt3 = ((Button) view.findViewById(R.id.alt3));
        Button btnalt4 = ((Button) view.findViewById(R.id.alt4));

        btnalt1.setText(item.getAlt1());
        btnalt2.setText(item.getAlt2());
        btnalt3.setText(item.getAlt3());
        btnalt4.setText(item.getAlt4());

        btnalt1.setBackgroundColor(Color.TRANSPARENT);
        btnalt2.setBackgroundColor(Color.TRANSPARENT);
        btnalt3.setBackgroundColor(Color.TRANSPARENT);
        btnalt4.setBackgroundColor(Color.TRANSPARENT);


        urlimg = item.getImg();

        if (urlimg.isEmpty()) {
            ImageView img = (ImageView) view.findViewById(R.id.placaImg);
            img.setMaxWidth(0);
        }else{
            ImageView img = (ImageView) view.findViewById(R.id.placaImg);
            Picasso.with(view.getContext()).load(urlimg).into(img);
        }

        switch (item.getEscolha()){
            case "1":
                btnalt1.setBackgroundColor(Color.RED);
                break;
            case "2":
                btnalt2.setBackgroundColor(Color.RED);
                break;
            case "3":
                btnalt3.setBackgroundColor(Color.RED);
                break;
            case "4":
                btnalt4.setBackgroundColor(Color.RED);
                break;
        }
        switch (item.getCorreto()){
            case "1":
                btnalt1.setBackgroundColor(Color.GREEN);
                break;
            case "2":
                btnalt2.setBackgroundColor(Color.GREEN);
                break;
            case "3":
                btnalt3.setBackgroundColor(Color.GREEN);
                break;
            case "4":
                btnalt4.setBackgroundColor(Color.GREEN);
                break;
        }

        return view;
    }
}