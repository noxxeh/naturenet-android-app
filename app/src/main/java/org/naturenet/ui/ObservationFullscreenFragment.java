package org.naturenet.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.naturenet.R;
import org.naturenet.data.model.Comment;
import org.naturenet.data.model.Observation;
import org.naturenet.data.model.Site;
import org.naturenet.data.model.Users;
import org.naturenet.util.NatureNetUtils;

import timber.log.Timber;

public class ObservationFullscreenFragment extends Fragment{

    public static final String FRAGMENT_TAG = "observation_fragment_fullscreen";
    private static final String ARG_OBSERVATION = "ARG_OBSERVATION";

    ObservationActivity o;
    ImageView observation_image;
    private String mObservationId;

    public static ObservationFullscreenFragment newInstance(String observationID) {
        ObservationFullscreenFragment full = new ObservationFullscreenFragment();
        Bundle args = new Bundle();
        args.putString(ARG_OBSERVATION, observationID);
        full.setArguments(args);
        return full;
    }

    //@Overide
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_observation_fullscreen, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        observation_image = (ImageView) view.findViewById(R.id.selected_observation_icon);

    }


}