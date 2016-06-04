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
import br.com.futfatec.exception.HttpRestException;
import br.com.futfatec.model.rodada.Partida;
import br.com.futfatec.rest.AbstractRestService;
import br.com.futfatec.rest.rodada.RodadaRestService;

public class PartidaActivity extends AppCompatActivity {
    public static final String KEY_RODADA_ID = "rodadaId";
    public static final String KEY_HORA_INICIO_PARTIDA = "horaInicioPartida";


    private TextView txtTimeA, txtTimeB, txtGolsTimeA, txtGolsTimeB;
    private RodadaRestService rodadaRestService;
    private RecyclerView mPartidaRecyclerView;
    private RecyclerView.LayoutManager mPartidaLayoutManager;
    private PartidaViewAdapter mPartidaAdapter;
    private ProgressBar progressBar;
    private View layoutPartida;

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
        Intent i = getIntent();
        String rodadaId = i.getStringExtra(KEY_RODADA_ID);
        String horaInicio = i.getStringExtra(KEY_HORA_INICIO_PARTIDA);
        refreshPartida(rodadaId, horaInicio);

    }

    private void refreshPartida(String rodadaId, String horaInicio) {
        progressBar.setVisibility(View.VISIBLE);
        layoutPartida.setVisibility(View.GONE);
        rodadaRestService = new RodadaRestService(this);
        rodadaRestService.getPartida(new AbstractRestService.OnResponse<Partida>() {

            @Override
            public void success(Partida partida) {
                progressBar.setVisibility(View.GONE);
                layoutPartida.setVisibility(View.VISIBLE);
                fillTextViews(partida);
                initRecyclerView(partida);
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
}
