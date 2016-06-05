package br.com.futfatec.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.com.futfatec.R;
import br.com.futfatec.exception.HttpRestException;
import br.com.futfatec.model.artilharia.Jogador;
import br.com.futfatec.model.rodada.Evento;
import br.com.futfatec.model.rodada.Rodada;
import br.com.futfatec.model.rodada.TipoEvento;
import br.com.futfatec.rest.AbstractRestService;
import br.com.futfatec.rest.artilharia.ArtilhariaRestService;
import br.com.futfatec.rest.rodada.RodadaRestService;

public class EventoActivity extends AppCompatActivity {
    public static final String KEY_TIME_A = "keyTimeA";
    public static final String KEY_TIME_B = "keyTimeB";
    public static final String KEY_RODADA_ID = "keyRodadaId";
    public static final String KEY_HORA_INICIO = "keyHoraInicio";

    private Spinner spnJogador, spnTipoEvento;
    private RadioGroup radioGroupTime;
    private RadioButton rdbTimeA, rdbTimeB;
    private View cdvTime, cdvJogador, btnSave;
    private List<Jogador> jogadoresTimeA = new ArrayList<>();
    private List<Jogador> jogadoresTimeB = new ArrayList<>();
    private ArrayAdapter<Jogador> jogadorDataAdapter;
    private ArrayAdapter<String> tipoEventoDataAdapter;

    private ArtilhariaRestService artilhariaRestService;
    private RodadaRestService rodadaRestService;
    private String rodadaId, horaInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);
        artilhariaRestService = new ArtilhariaRestService(this);
        rodadaRestService = new RodadaRestService(this);
        spnJogador = (Spinner) findViewById(R.id.spnJogador);
        spnTipoEvento = (Spinner) findViewById(R.id.spnTipoEvento);
        radioGroupTime = (RadioGroup) findViewById(R.id.radioGroupTime);
        rdbTimeA = (RadioButton) findViewById(R.id.rdbTimeA);
        rdbTimeB = (RadioButton) findViewById(R.id.rdbTimeB);
        cdvTime = findViewById(R.id.cdvTime);
        cdvJogador = findViewById(R.id.cdvJogador);
        btnSave = findViewById(R.id.btnSave);

        cdvTime.setVisibility(View.GONE);
        cdvJogador.setVisibility(View.GONE);
        btnSave.setVisibility(View.GONE);

        horaInicio = getIntent().getStringExtra(KEY_HORA_INICIO);
        rodadaId = getIntent().getStringExtra(KEY_RODADA_ID);

//        INICIALIZANDO RadioGroup
        String timeA = getIntent().getStringExtra(KEY_TIME_A);
        String timeB = getIntent().getStringExtra(KEY_TIME_B);
        rdbTimeA.setText(timeA);
        rdbTimeB.setText(timeB);
//        INICIALIZANDO EVENTOS DROPDOWNLIST
        tipoEventoDataAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, TipoEvento.namesToArray());
        spnTipoEvento.setAdapter(tipoEventoDataAdapter);

//        BUSCANDO DADOS DE ELENCOS
        refreshElencos(timeA, timeB);


        spnTipoEvento.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                cdvTime.setVisibility(View.VISIBLE);
                return false;
            }
        });
        radioGroupTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                jogadorDataAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, rdbTimeA.isChecked() ? jogadoresTimeA : jogadoresTimeB);
                spnJogador.setAdapter(jogadorDataAdapter);
                cdvJogador.setVisibility(View.VISIBLE);
            }
        });

        spnJogador.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                btnSave.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }

    private void refreshElencos(String timeA, String timeB) {
        artilhariaRestService.getElenco(new AbstractRestService.OnResponse<List<Jogador>>() {
            @Override
            public void success(List<Jogador> jogadores) {
                jogadoresTimeA = jogadores;
            }

            @Override
            public void error(HttpRestException restException) {

            }
        }, timeA);

        artilhariaRestService.getElenco(new AbstractRestService.OnResponse<List<Jogador>>() {
            @Override
            public void success(List<Jogador> jogadores) {
                jogadoresTimeB = jogadores;
            }

            @Override
            public void error(HttpRestException restException) {
            }
        }, timeB);
    }

    public void salvarEvento(View view) {
        Evento evento = new Evento();
        TipoEvento tipoEvento = TipoEvento.findByName((String) spnTipoEvento.getSelectedItem());
        Jogador jogador = (Jogador) spnJogador.getSelectedItem();
        evento.setJogador(jogador);
        evento.setTipo(tipoEvento);

        rodadaRestService.postEvento(new AbstractRestService.OnResponse<Rodada>() {
            @Override
            public void success(Rodada rodada) {
                finish();
            }

            @Override
            public void error(HttpRestException restException) {
                finish();
            }
        }, rodadaId, horaInicio, evento);

    }
}
