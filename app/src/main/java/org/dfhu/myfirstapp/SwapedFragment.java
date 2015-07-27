package org.dfhu.myfirstapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SwapedFragment extends Fragment {

    public static SwapedFragment newInstance()
    {
        return new SwapedFragment();
    }

    public SwapedFragment () {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_swaped, container, false);
    }
}
