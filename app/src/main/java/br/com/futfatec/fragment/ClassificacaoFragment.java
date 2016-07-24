package br.com.futfatec.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import br.com.futfatec.R;
import br.com.futfatec.adapter.TabelaGrupoViewAdapter;
import br.com.futfatec.content.AppPreferencesData;
import br.com.futfatec.exception.HttpRestException;
import br.com.futfatec.model.classificacao.Tabela;
import br.com.futfatec.rest.AbstractRestService;
import br.com.futfatec.rest.classificacao.ClassificacaoRestService;


public class ClassificacaoFragment extends Fragment {
    private static final String TAG = "ClassificacaoFragment";

    private ClassificacaoRestService classificacaoRestService;
    private RecyclerView mTabelaRecyclerView;
    private RecyclerView.LayoutManager mTabelaLayoutManager;
    private TabelaGrupoViewAdapter mTabelaAdapter;
    private ProgressBar progressBar;
    private AppPreferencesData preferencesData;

    public ClassificacaoFragment() {
    }

    public static ClassificacaoFragment newInstance() {
        ClassificacaoFragment fragment = new ClassificacaoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classificacaoRestService = new ClassificacaoRestService(getActivity());
        preferencesData = new AppPreferencesData(getActivity());
        if (getArguments() != null) {
        }

    }

    private void refreshClassificacao(final View v) {
        progressBar.setVisibility(View.VISIBLE);
        classificacaoRestService.get(new AbstractRestService.OnResponse<Tabela>() {
            @Override
            public void success(Tabela tabela) {
                progressBar.setVisibility(View.GONE);
                initRecyclerView(tabela, v);
            }

            @Override
            public void error(HttpRestException restException) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), getString(R.string.error_retrieve_tabela), Toast.LENGTH_LONG).show();
            }
        }, preferencesData.getLeagueId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_classificacao, container, false);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        refreshClassificacao(v);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initRecyclerView(Tabela tabela, View v) {
        mTabelaRecyclerView = (RecyclerView) v.findViewById(R.id.rcvClassificacao);
        mTabelaRecyclerView.setHasFixedSize(true);
        mTabelaLayoutManager = new LinearLayoutManager(getActivity());
        mTabelaRecyclerView.setLayoutManager(mTabelaLayoutManager);
        mTabelaAdapter = new TabelaGrupoViewAdapter(tabela, getActivity());
        mTabelaRecyclerView.setAdapter(mTabelaAdapter);
    }

}
