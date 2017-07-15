package com.is.kennedy.ilhafilmes.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.is.kennedy.ilhafilmes.R;
import com.is.kennedy.ilhafilmes.model.Filme;
import com.is.kennedy.ilhafilmes.model.FilmeDB;
import com.squareup.picasso.Picasso;


public class DetalheFilme extends AppCompatActivity {

    String nomeFilme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_filme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent it = getIntent();
        if( it != null ){
            nomeFilme = it.getStringExtra("nomeFilme");
        }

        LocalizarFilme(FilmeDB.getFilmeDB(this));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalhe_filme, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()){

            case R.id.excluirFilme:

                ExcluirFilme(FilmeDB.getFilmeDB(this));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


    public void LocalizarFilme(FilmeDB db){

        Filme filme = db.filmeDao().findByName(nomeFilme);

        ImageView img = (ImageView)findViewById(R.id.ivPoster);
        Picasso.with(getApplicationContext()).load(filme.getPoster()).into(img);

        TextView tvTitulo  = (TextView)findViewById(R.id.tvTitulo);
        TextView tvDiretor = (TextView)findViewById(R.id.tvDiretor);
        TextView tvGenero  = (TextView)findViewById(R.id.tvGenero);
        TextView tvAno     = (TextView)findViewById(R.id.tvAno);
        TextView tvDuracao = (TextView)findViewById(R.id.tvDuracao);
        TextView tvAtores  = (TextView)findViewById(R.id.tvAtores);
        TextView tvSinopse = (TextView)findViewById(R.id.tvSinope);

        tvTitulo.setText(filme.getTitle());
        tvDiretor.setText(filme.getDirector());
        tvGenero.setText(filme.getGenre());
        tvAno.setText(filme.getYear());
        tvDuracao.setText(filme.getRuntime());
        tvAtores.setText(filme.getActors());
        tvSinopse.setText(filme.getPlot());

    }


    public void ExcluirFilme(FilmeDB db){

        final FilmeDB DB = db;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Deseja excluir " + nomeFilme + "?");

        alertDialogBuilder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Filme filme = DB.filmeDao().findByName(nomeFilme);
                DB.filmeDao().delete(filme);
                Toast.makeText(getApplicationContext(), "Exclusão realizada com sucesso!", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("Não",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
