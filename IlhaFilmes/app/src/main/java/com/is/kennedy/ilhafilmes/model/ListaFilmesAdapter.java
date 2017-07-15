package com.is.kennedy.ilhafilmes.model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import com.is.kennedy.ilhafilmes.R;

import java.util.List;

/**
 * Created by kennedy ximenes on 14/07/2017.
 */

public class ListaFilmesAdapter extends ArrayAdapter<Filme> {

    private Activity activity;

    public ListaFilmesAdapter(Activity activity, int resource, List<Filme> filmes) {
        super(activity, resource, filmes);
        this.activity = activity;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_listview, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Filme filme = getItem(position);
        holder.titulo.setText(filme.getTitle());
        holder.diretor.setText(filme.getDirector());
        holder.genero.setText(filme.getGenre());
        holder.duracao.setText(filme.getRuntime());
        Picasso.with(activity).load(filme.getPoster()).into(holder.image);

        return convertView;
    }

    public static class ViewHolder{

        private TextView titulo;
        private TextView diretor;
        private TextView genero;
        private TextView duracao;
        private ImageView image;

        public ViewHolder(View v){

            titulo  = (TextView) v.findViewById(R.id.tvTitulo);
            diretor = (TextView) v.findViewById(R.id.tvDiretor);
            genero  = (TextView) v.findViewById(R.id.tvGenero);
            duracao = (TextView) v.findViewById(R.id.tvDuracao);
            image   = (ImageView)v.findViewById(R.id.poster);

        }
    }
}
