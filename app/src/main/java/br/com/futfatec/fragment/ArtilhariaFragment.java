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
import br.com.futfatec.adapter.ArtilhariaViewAdapter;
import br.com.futfatec.content.AppPreferencesData;
import br.com.futfatec.exception.HttpRestException;
import br.com.futfatec.model.artilharia.Jogador;
import br.com.futfatec.rest.AbstractRestService;
import br.com.futfatec.rest.artilharia.ArtilhariaRestService;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtilhariaFragment extends Fragment {
    private static final String TAG = "ArtilhariaFragment";

    private ArtilhariaRestService artilhariaRestService;
    private RecyclerView mArtilhariaRecyclerView;
    private RecyclerView.LayoutManager mArtilhariaLayoutManager;
    private ArtilhariaViewAdapter mArtilhariaAdapter;
    private ProgressBar progressBar;
    private String tabelaId;
    private AppPreferencesData preferencesData;


    public ArtilhariaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_artilharia, container, false);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        refreshJogos(v);
        return v;
    }

    public static ArtilhariaFragment newInstance() {

        Bundle args = new Bundle();

        ArtilhariaFragment fragment = new ArtilhariaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        artilhariaRestService = new ArtilhariaRestService(getActivity());
        preferencesData = new AppPreferencesData(getActivity());
        this.tabelaId = preferencesData.getTabelaId();
    }

    private void refreshJogos(final View v){
        progressBar.setVisibility(View.VISIBLE);
        artilhariaRestService.get(new AbstractRestService.OnResponse<List<Jogador>>() {
            @Override
            public void success(List<Jogador> jogadores) {
                progressBar.setVisibility(View.GONE);
                initRecyclerView(jogadores, v);
            }

            @Override
            public void error(HttpRestException restException) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), getString(R.string.error_retrieve_artilharia), Toast.LENGTH_LONG).show();
            }
        }, tabelaId);
    }

    private void initRecyclerView(List<Jogador> jogadores, View v) {
        mArtilhariaRecyclerView = (RecyclerView) v.findViewById(R.id.rcvRanking);
        mArtilhariaRecyclerView.setHasFixedSize(true);
        mArtilhariaLayoutManager = new LinearLayoutManager(getActivity());
        mArtilhariaRecyclerView.setLayoutManager(mArtilhariaLayoutManager);
        mArtilhariaAdapter = new ArtilhariaViewAdapter(jogadores, getActivity());
        mArtilhariaRecyclerView.setAdapter(mArtilhariaAdapter);
    }

}
