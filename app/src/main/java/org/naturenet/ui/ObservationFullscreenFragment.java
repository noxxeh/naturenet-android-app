package org.naturenet.ui;

import android.app.Fragment;
import android.content.Context;
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
        //Picasso.with(context).load("http://i.imgur.com/B7ldAEW.jpg").into(observation_image);

    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Picasso.with(getActivity()).load("http://i.imgur.com/B7ldAEW.jpg").placeholder(R.drawable.no_image)
                //.error(R.drawable.no_image).fit().centerInside().into(observation_image);

        if (getArguments() == null || getArguments().getString(ARG_OBSERVATION) == null) {
            Timber.e(new IllegalArgumentException(), "Tried to load ObservationFragment without an Observation argument");
            Toast.makeText(getActivity(), "No observation to display", Toast.LENGTH_SHORT).show();
            return;
        }

        o = (ObservationActivity) getActivity();
        mObservationId = getArguments().getString(ARG_OBSERVATION);

        FirebaseDatabase.getInstance().getReference(Observation.NODE_NAME).child(mObservationId).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange (DataSnapshot dataSnapshot){
                final Observation obs = dataSnapshot.getValue(Observation.class);

                Picasso.with(getActivity()).load(Strings.emptyToNull(obs.data.image)).placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image).fit().centerInside().into(observation_image);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Timber.w(databaseError.toException(), "Unable to read data for observation %s", mObservationId);
            }
        });
    }


}