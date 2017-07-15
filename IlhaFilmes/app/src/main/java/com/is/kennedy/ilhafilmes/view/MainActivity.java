package com.is.kennedy.ilhafilmes.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.is.kennedy.ilhafilmes.R;
import com.is.kennedy.ilhafilmes.model.Filme;
import com.is.kennedy.ilhafilmes.model.FilmeDB;
import com.is.kennedy.ilhafilmes.presenter.GetJsonFromRetrofit;
import com.is.kennedy.ilhafilmes.presenter.RetrofitObjectAPI;
import com.squareup.picasso.Picasso;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private String tipoRede = "";

    private final static String url = "http://www.omdbapi.com/";
    private final static String API_KEY = "b20d7b1f";

    private EditText etTit;
    private TextView msg;
    private String titulo = "";
    private String linkImagem = "";
    private String ano = "";
    private String genero = "";
    private String duracao = "";
    private String diretor = "";
    private String atores = "";
    private String sinopse = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etTit = (EditText)findViewById(R.id.etNomeFilme);
        msg   = (TextView)findViewById(R.id.tvMensagem);

        if( wifiOk() || redeOk() ){
            msg.setText("Status: " + tipoRede);
        }else{
            msg.setText("ERRO: Sem internet. Verifique sua conexão!");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()){

            case R.id.pesquisarFilme:

                View view = this.getCurrentFocus();

                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                if( wifiOk() || redeOk() ){
                    if( etTit.getText().toString().equals(""))
                        Dialogo("Erro", "Título do Filme não pode ser vazio!");
                    else
                        Pesquisar();
                }else{
                    Dialogo("Erro", "A pesquisa não pode ser feita sem conexão com internet!");
                }

                return true;

            case R.id.salvarFilme:

                if( titulo.equals("")){
                    Dialogo("Aviso", "Antes de salvar, você deve localizar um filme!");
                }else{
                    SalvarFilme(FilmeDB.getFilmeDB(this));
                }

                return true;

            case R.id.listarFilmes:

                Intent it = new Intent(this, ListaFilmes.class);
                startActivity(it);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void Pesquisar(){

        RetrofitObjectAPI service = GetJsonFromRetrofit.getClient().create(RetrofitObjectAPI.class);

        String filme = etTit.getText().toString();
        Call<Filme> call = service.getFilmeDetails(filme, API_KEY);

        call.enqueue(new Callback<Filme>(){

            @Override
            public void onResponse(Response<Filme> response, Retrofit retrofit) {

                try{
                    titulo = response.body().getTitle();

                    if( titulo == null){
                        Dialogo("Aviso", "Filme não localizado" );
                    }else{
                        linkImagem = response.body().getPoster();

                        int imagem = linkImagem.indexOf("http");
                        if( imagem == -1 )
                            linkImagem = "http://www.51allout.co.uk/wp-content/uploads/2012/02/Image-not-found.gif";

                        TextView tvTitulo  = (TextView)findViewById(R.id.tvTitulo);
                        TextView tvDiretor = (TextView)findViewById(R.id.tvDiretor);
                        TextView tvGenero  = (TextView)findViewById(R.id.tvGenero);
                        TextView tvAno     = (TextView)findViewById(R.id.tvAno);
                        TextView tvDuracao = (TextView)findViewById(R.id.tvDuracao);
                        TextView tvAtores  = (TextView)findViewById(R.id.tvAtores);
                        TextView tvSinopse = (TextView)findViewById(R.id.tvSinope);

                        diretor  = response.body().getDirector();
                        genero   = response.body().getGenre();
                        ano      = response.body().getYear();
                        duracao  = response.body().getRuntime();
                        atores   = response.body().getActors();
                        sinopse  = response.body().getPlot();

                        Filme filme = new Filme();

                        filme.setTitle(titulo);
                        filme.setDirector(diretor);
                        filme.setGenre(genero);
                        filme.setYear(ano);
                        filme.setRuntime(duracao);
                        filme.setActors(atores);
                        filme.setPlot(sinopse);

                        ImageView img = (ImageView)findViewById(R.id.ivPoster);
                        Picasso.with(getApplicationContext()).load(linkImagem).into(img);

                        tvTitulo.setText(titulo);
                        tvDiretor.setText(diretor);
                        tvGenero.setText(genero);
                        tvAno.setText(ano);
                        tvDuracao.setText(duracao);
                        tvAtores.setText(atores);
                        tvSinopse.setText(sinopse);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.i("Falhou: ", t.toString());
            }
        });

    }

    public void SalvarFilme(FilmeDB db){

        try{

            Filme filme = new Filme();

            filme.setTitle(titulo);
            filme.setPoster(linkImagem);
            filme.setYear(ano);
            filme.setGenre(genero);
            filme.setRuntime(duracao);
            filme.setDirector(diretor);
            filme.setActors(atores);
            filme.setPlot(sinopse);
            addFilme(db, filme);

            Dialogo(titulo, "Filme armazenado com sucesso!" );

        }catch (Exception e ){

            Dialogo("Erro", "Erro ao tentar salvar o filme");

        }
    }


    private Filme addFilme(final FilmeDB db, Filme filme) {
        db.filmeDao().insertAll(filme);
        return filme;
    }


    @Override
    protected void onDestroy() {
        FilmeDB.destroyInstance();
        super.onDestroy();
    }


    private boolean redeOk(){
        ConnectivityManager check = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] info = check.getAllNetworkInfo();

        if(info[0].getState().toString().equals("CONNECTED")){
            tipoRede = " 3G/4G";
            return true;
        }else{
            tipoRede = " Sem conexão com internet!";
            return false;
        }
    }


    private boolean wifiOk(){

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            tipoRede = " WiFi";
            return true;
        }else{
            tipoRede = " Sem conexão com internet!";
            return false;
        }

    }

    public void Dialogo( String tit, String msg){

        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle(tit);
        alert.setMessage(msg);
        alert.setPositiveButton("Ok", null);
        alert.show();

    }

}
