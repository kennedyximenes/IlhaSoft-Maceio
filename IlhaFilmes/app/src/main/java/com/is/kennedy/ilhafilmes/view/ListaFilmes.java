package com.is.kennedy.ilhafilmes.view;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.is.kennedy.ilhafilmes.R;
import com.is.kennedy.ilhafilmes.model.Filme;
import com.is.kennedy.ilhafilmes.model.FilmeDB;
import com.is.kennedy.ilhafilmes.model.ListaFilmesAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListaFilmes extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayList<Filme> filmes;
    private ArrayAdapter<Filme> adapter;

    List<Filme> filmeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_filmes);

        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(this);

        setListViewAdapter();

        ListaColecao(FilmeDB.getFilmeDB(this));

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent it = new Intent(this, DetalheFilme.class);
        it.putExtra("nomeFilme", filmes.get(position).getTitle());
        startActivity(it);

    }


    private void setListViewAdapter(){

        filmes = new ArrayList<Filme>();
        adapter = new ListaFilmesAdapter(this, R.layout.item_listview, filmes);
        listView.setAdapter(adapter);
    }


    public void ListaColecao(FilmeDB db){

        filmeList = db.filmeDao().getAll();

        if( filmeList.size() < 1 ){

            AlertDialog.Builder alert = new AlertDialog.Builder(ListaFilmes.this);
            alert.setTitle("Aviso");
            alert.setMessage("Sua lista de filmes esta vazia");
            alert.setPositiveButton("Ok", null);
            alert.show();
            finish();

        }
        else{
            for( int i = 0; i < filmeList.size(); i++){
                Filme filme = new Filme();
                filme.setTitle(filmeList.get(i).getTitle());
                filme.setDirector(filmeList.get(i).getDirector());
                filme.setYear(filmeList.get(i).getYear());
                filme.setGenre(filmeList.get(i).getGenre());
                filme.setRuntime(filmeList.get(i).getRuntime());
                filme.setPlot(filmeList.get(i).getPlot());
                filme.setPoster(filmeList.get(i).getPoster());
                filmes.add(filme);
            }

            adapter.notifyDataSetChanged();

        }
    }


    @Override
    protected void onRestart() {

        for( int i = filmes.size()-1; i > -1; i-- )
            filmes.remove(i);

        ListaColecao(FilmeDB.getFilmeDB(this));
        super.onRestart();
    }

}
