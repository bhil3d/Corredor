package com.weberson.corredor.Fragmento;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weberson.corredor.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DireitoFragment extends Fragment {


    public DireitoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_direito, container, false);
    }

}