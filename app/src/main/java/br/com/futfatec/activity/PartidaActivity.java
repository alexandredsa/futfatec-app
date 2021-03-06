package br.com.futfatec.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import br.com.futfatec.R;
import br.com.futfatec.adapter.PartidaViewAdapter;
import br.com.futfatec.content.AppPreferencesData;
import br.com.futfatec.exception.HttpRestException;
import br.com.futfatec.model.classificacao.Tabela;
import br.com.futfatec.model.classificacao.Time;
import br.com.futfatec.model.rodada.Etapa;
import br.com.futfatec.model.rodada.Partida;
import br.com.futfatec.model.rodada.Rodada;
import br.com.futfatec.model.rodada.Status;
import br.com.futfatec.rest.AbstractRestService;
import br.com.futfatec.rest.classificacao.ClassificacaoRestService;
import br.com.futfatec.rest.rodada.RodadaRestService;

public class PartidaActivity extends AppCompatActivity {
    public static final String KEY_RODADA_ID = "rodadaId";
    public static final String KEY_ETAPA = "etapa";
    public static final String KEY_HORA_INICIO_PARTIDA = "horaInicioPartida";


    private TextView txtTimeA, txtTimeB, txtGolsTimeA, txtGolsTimeB;
    private RecyclerView mPartidaRecyclerView;
    private RecyclerView.LayoutManager mPartidaLayoutManager;
    private PartidaViewAdapter mPartidaAdapter;
    private ProgressBar progressBar;
    private View layoutPartida, btnAddEvento, btnFinalizar;
    private String rodadaId, etapa;
    private String horaInicio;

    private AppPreferencesData preferencesData;

    private ClassificacaoRestService classificacaoRestService;
    private RodadaRestService rodadaRestService;
    private Partida partida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);
        layoutPartida = findViewById(R.id.layoutPartida);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mPartidaRecyclerView = (RecyclerView) findViewById(R.id.rcvEvento);
        txtTimeA = (TextView) findViewById(R.id.txtTimeA);
        txtTimeB = (TextView) findViewById(R.id.txtTimeB);
        txtGolsTimeA = (TextView) findViewById(R.id.txtGolsTimeA);
        txtGolsTimeB = (TextView) findViewById(R.id.txtGolsTimeB);
        btnAddEvento = findViewById(R.id.btnAddEvento);
        btnFinalizar = findViewById(R.id.btnEncerrarJogo);
        preferencesData = new AppPreferencesData(this);
        rodadaRestService = new RodadaRestService(this);
        classificacaoRestService = new ClassificacaoRestService(this);

        getSupportActionBar().hide();

        if(!preferencesData.isAdmin()){
            btnFinalizar.setVisibility(View.GONE);
            btnAddEvento.setVisibility(View.GONE);
        }

        Intent i = getIntent();
        rodadaId = i.getStringExtra(KEY_RODADA_ID);
        etapa = i.getStringExtra(KEY_ETAPA);
        horaInicio = i.getStringExtra(KEY_HORA_INICIO_PARTIDA);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshPartida(rodadaId, horaInicio);
    }

    private void refreshPartida(String rodadaId, String horaInicio) {
        progressBar.setVisibility(View.VISIBLE);
        layoutPartida.setVisibility(View.GONE);
        rodadaRestService.getPartida(new AbstractRestService.OnResponse<Partida>() {

            @Override
            public void success(Partida partida) {
                progressBar.setVisibility(View.GONE);
                layoutPartida.setVisibility(View.VISIBLE);

                if (partida.getStatus() == Status.FINALIZADO) {
                    btnAddEvento.setVisibility(View.GONE);
                    btnFinalizar.setVisibility(View.GONE);
                }

                fillTextViews(partida);
                initRecyclerView(partida);

                PartidaActivity.this.partida = partida;
            }

            @Override
            public void error(HttpRestException restException) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), getString(R.string.error_retrieve_partida), Toast.LENGTH_LONG).show();
            }
        }, rodadaId, horaInicio);
    }

    private void initRecyclerView(Partida partida) {
        mPartidaRecyclerView.setHasFixedSize(true);
        mPartidaLayoutManager = new LinearLayoutManager(this);
        mPartidaRecyclerView.setLayoutManager(mPartidaLayoutManager);
        mPartidaAdapter = new PartidaViewAdapter(partida, this);
        mPartidaRecyclerView.setAdapter(mPartidaAdapter);
    }

    private void fillTextViews(Partida partida) {
        txtTimeA.setText(partida.getTimeA().getNome());
        txtTimeB.setText(partida.getTimeB().getNome());
        txtGolsTimeA.setText(String.valueOf(partida.getTimeA().getGols()));
        txtGolsTimeB.setText(String.valueOf(partida.getTimeB().getGols()));
    }

    public void novoEvento(View view) {
        Intent i = new Intent(this, EventoActivity.class);
        i.putExtra(EventoActivity.KEY_TIME_A, txtTimeA.getText().toString());
        i.putExtra(EventoActivity.KEY_TIME_B, txtTimeB.getText().toString());
        i.putExtra(EventoActivity.KEY_RODADA_ID, rodadaId);
        i.putExtra(EventoActivity.KEY_HORA_INICIO, horaInicio);
        startActivity(i);
    }

    public void encerrarJogo(View view) {
        Time timeA = preferencesData.retrieveTime(txtTimeA.getText().toString());
        final Time timeB = preferencesData.retrieveTime(txtTimeB.getText().toString());
        int golsTimeA = Integer.parseInt(txtGolsTimeA.getText().toString());
        int golsTimeB = Integer.parseInt(txtGolsTimeB.getText().toString());
        timeA.calcularNovaPontuacao(golsTimeA, golsTimeB);
        timeB.calcularNovaPontuacao(golsTimeB, golsTimeA);

        final String idTabela = preferencesData.getTabelaId();


        partida.getTimeA().setGols(golsTimeA);
        partida.getTimeB().setGols(golsTimeB);
        partida.setStatus(Status.FINALIZADO);

        rodadaRestService.postPartida(new AbstractRestService.OnResponse<Rodada>() {
            @Override
            public void success(Rodada rodada) {
            }

            @Override
            public void error(HttpRestException restException) {
                Toast.makeText(getApplicationContext(), getString(R.string.error_save_game), Toast.LENGTH_LONG).show();
            }
        }, this.rodadaId, this.partida);

        if (!(this.etapa.equalsIgnoreCase(Etapa.GRUPO.getDescricao()))) {
            finish();
            return;
        }


        classificacaoRestService.postTime(new AbstractRestService.OnResponse<Tabela>() {
            @Override
            public void success(Tabela tabela) {
                classificacaoRestService.postTime(new AbstractRestService.OnResponse<Tabela>() {
                    @Override
                    public void success(Tabela tabela) {
                        finish();
                    }

                    @Override
                    public void error(HttpRestException restException) {
                        Toast.makeText(getApplicationContext(), getString(R.string.error_save_game), Toast.LENGTH_LONG).show();
                    }
                }, idTabela, timeB);
            }

            @Override
            public void error(HttpRestException restException) {
            }
        }, idTabela, timeA);


    }
}
