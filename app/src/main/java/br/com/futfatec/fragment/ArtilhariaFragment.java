package br.com.futfatec.fragment;

import android.view.View;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.futfatec.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtilhariaFragment extends Fragment {


    public ArtilhariaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artilharia, container, false);
    }

    public static ArtilhariaFragment newInstance() {

        Bundle args = new Bundle();

        ArtilhariaFragment fragment = new ArtilhariaFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
