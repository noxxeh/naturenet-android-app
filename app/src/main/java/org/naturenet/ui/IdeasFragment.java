package org.naturenet.ui;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.naturenet.R;

public class IdeasFragment extends Fragment {

    public static final String FRAGMENT_TAG = "designideas_fragment";

    MainActivity main;
    TextView toolbar_title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ideas, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        main = ((MainActivity)  this.getActivity());
        toolbar_title = (TextView) main.findViewById(R.id.app_bar_main_tv);
        toolbar_title.setText(R.string.design_ideas_title_design_ideas);
    }

}