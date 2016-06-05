package br.com.futfatec.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import br.com.futfatec.R;
import br.com.futfatec.adapter.RodadaViewAdapter;
import br.com.futfatec.content.AppPreferencesData;
import br.com.futfatec.exception.HttpRestException;
import br.com.futfatec.model.rodada.Rodada;
import br.com.futfatec.rest.AbstractRestService;
import br.com.futfatec.rest.rodada.RodadaRestService;

/**
 * A simple {@link Fragment} subclass.
 */
public class RodadaFragment extends Fragment {
    private static final String TAG = "RodadaFragment";

    private RodadaRestService rodadaRestService;
    private RecyclerView mRodadaRecyclerView;
    private RecyclerView.LayoutManager mRodadaLayoutManager;
    private RodadaViewAdapter mRodadaAdapter;
    private ProgressBar progressBar;
    private String tabelaId;
    private AppPreferencesData preferencesData;


    public RodadaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rodada, container, false);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        mRodadaRecyclerView = (RecyclerView) v.findViewById(R.id.rcvRodada);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshJogos();
    }

    public static RodadaFragment newInstance() {

        Bundle args = new Bundle();

        RodadaFragment fragment = new RodadaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rodadaRestService = new RodadaRestService(getActivity());
        preferencesData = new AppPreferencesData(getActivity());
        this.tabelaId = preferencesData.getTabelaId();
    }
    
    private void refreshJogos(){
        progressBar.setVisibility(View.VISIBLE);
        rodadaRestService.get(new AbstractRestService.OnResponse<List<Rodada>>() {
            @Override
            public void success(List<Rodada> jogos) {
                progressBar.setVisibility(View.GONE);
                initRecyclerView(jogos);
            }

            @Override
            public void error(HttpRestException restException) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), getString(R.string.error_retrieve_rodada), Toast.LENGTH_LONG).show();
            }
        }, tabelaId);
    }

    private void initRecyclerView(List<Rodada> jogos) {

        mRodadaRecyclerView.setHasFixedSize(true);
        mRodadaLayoutManager = new LinearLayoutManager(getActivity());
        mRodadaRecyclerView.setLayoutManager(mRodadaLayoutManager);
        mRodadaAdapter = new RodadaViewAdapter(jogos, getActivity());
        mRodadaRecyclerView.setAdapter(mRodadaAdapter);
    }
}
