package app.robots.simuladorcfc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SimulacaoAdapter extends BaseAdapter {

    public LayoutInflater mInflater;
    public ArrayList<QuestaoView> itens;
    public String urlimg;

    public SimulacaoAdapter(Context context, ArrayList<QuestaoView> itens)
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

        QuestaoView item = itens.get(position);

        view = mInflater.inflate(R.layout.questao_view, null);

        ((TextView) view.findViewById(R.id.enunciadoTxt)).setText(item.getEnunciado());
        ((Button) view.findViewById(R.id.alt1)).setText(item.getAlt1());
        ((Button) view.findViewById(R.id.alt2)).setText(item.getAlt2());
        ((Button) view.findViewById(R.id.alt3)).setText(item.getAlt3());
        ((Button) view.findViewById(R.id.alt4)).setText(item.getAlt4());



        urlimg = item.getImg();

        if (urlimg.isEmpty()) {
            ImageView img = (ImageView) view.findViewById(R.id.placaImg);
            img.
        }else{
            ImageView img = (ImageView) view.findViewById(R.id.placaImg);
            Picasso.with(view.getContext()).load(urlimg).into(img);
        }


        return view;
    }
}