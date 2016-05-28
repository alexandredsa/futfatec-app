package br.com.futfatec.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.futfatec.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RodadaFragment extends Fragment {


    public RodadaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rodada, container, false);
    }

    public static RodadaFragment newInstance() {

        Bundle args = new Bundle();

        RodadaFragment fragment = new RodadaFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
