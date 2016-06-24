package org.naturenet.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.naturenet.BuildConfig;
import org.naturenet.R;
import org.naturenet.data.model.Project;
import org.naturenet.data.model.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static String FIREBASE_ENDPOINT = BuildConfig.FIREBASE_ROOT_URL;
    final static int REQUEST_CODE_JOIN = 1;
    final static int REQUEST_CODE_LOGIN = 2;
    final static int REQUEST_CODE_ADD_OBSERVATION = 3;
    static String FRAGMENT_TAG_LAUNCH = "launch_fragment";
    static String FRAGMENT_TAG_EXPLORE = "explore_fragment";
    static String FRAGMENT_TAG_PROJECTS = "projects_fragment";
    static String FRAGMENT_TAG_DESIGNIDEAS = "designideas_fragment";
    static String FRAGMENT_TAG_COMMUNITIES = "communities_fragment";
    static String LOGIN = "login";
    static String GUEST = "guest";
    static String LAUNCH = "launch";
    static String JOIN = "join";
    static String IDS = "ids";
    static String NAMES = "names";
    static String SITES = "sites";
    static String NEW_USER = "new_user";
    static String SIGNED_USER = "signed_user";
    static String ID = "id";
    static String NAME = "name";
    static String OBSERVATION = "observation";
    static String PROJECT = "project";
    static String EMPTY = "";
    String[] affiliation_ids, affiliation_names;
    List<String> ids, names;
    Firebase fbRef;
    Users signed_user;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    View header;
    Button sign_in, join;
    TextView toolbar_title, display_name, affiliation;
    MenuItem logout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title = (TextView) findViewById(R.id.app_bar_main_tv);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);
        sign_in = (Button) header.findViewById(R.id.nav_b_sign_in);
        join = (Button) header.findViewById(R.id.nav_b_join);
        display_name = (TextView) header.findViewById(R.id.nav_tv_display_name);
        affiliation = (TextView) header.findViewById(R.id.nav_tv_affiliation);
        toolbar.setTitle(EMPTY);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        logout = navigationView.getMenu().findItem(R.id.nav_logout);
        logout.setVisible(false);
        this.invalidateOptionsMenu();
        display_name.setText(EMPTY);
        affiliation.setText(EMPTY);
        sign_in.setVisibility(View.VISIBLE);
        join.setVisibility(View.VISIBLE);
        display_name.setVisibility(View.GONE);
        affiliation.setVisibility(View.GONE);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginActivity();
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToJoinActivity();
            }
        });
        goToLaunchFragment();
    }
    @Override
    public void onBackPressed() {
//        if(getFragmentManager().findFragmentByTag(FRAGMENT_TAG_LAUNCH).isVisible()) {
//
//        } else if(getFragmentManager().findFragmentByTag(FRAGMENT_TAG_EXPLORE).isVisible()) {
//
//        } else if(getFragmentManager().findFragmentByTag(FRAGMENT_TAG_PROJECTS).isVisible()) {
//
//        } else if(getFragmentManager().findFragmentByTag(FRAGMENT_TAG_DESIGNIDEAS).isVisible()) {
//
//        } else if(getFragmentManager().findFragmentByTag(FRAGMENT_TAG_COMMUNITIES).isVisible()) {
//
//        } else
//            super.onBackPressed();
        if(getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Firebase fbRef = new Firebase(FIREBASE_ENDPOINT);
        int id = item.getItemId();
        switch(id) {
            case R.id.nav_explore:
                goToExploreFragment();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_projects:
                goToProjectsFragment();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_design_ideas:
                goToDesignIdeasFragment();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_communities:
                goToCommunitiesFragment();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_logout:
                fbRef.unauth();
                logout();
                break;
        }
        return true;
    }
    public void goToLaunchFragment() {
        toolbar_title.setText(R.string.launch_title);
        getFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container, new LaunchFragment(), FRAGMENT_TAG_LAUNCH).
                addToBackStack(null).
                commit();
    }
    public void goToExploreFragment() {
        toolbar_title.setText(R.string.explore_title);
        getFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container, new ExploreFragment(), FRAGMENT_TAG_EXPLORE).
                addToBackStack(null).
                commit();
    }
    public void goToProjectsFragment() {
        toolbar_title.setText(R.string.projects_title);
        getFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container, new ProjectsFragment(), FRAGMENT_TAG_PROJECTS).
                addToBackStack(null).
                commit();
    }
    public void goToDesignIdeasFragment() {
        toolbar_title.setText(R.string.design_ideas_title_design_ideas);
        getFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container, new IdeasFragment(), FRAGMENT_TAG_DESIGNIDEAS).
                addToBackStack(null).
                commit();
    }
    public void goToCommunitiesFragment() {
        toolbar_title.setText(R.string.communities_title);
        getFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container, new CommunitiesFragment(), FRAGMENT_TAG_COMMUNITIES).
                addToBackStack(null).
                commit();
    }
    public void logout() {
        signed_user = null;
        logout.setVisible(false);
        this.invalidateOptionsMenu();
        display_name.setText(EMPTY);
        affiliation.setText(EMPTY);
        sign_in.setVisibility(View.VISIBLE);
        join.setVisibility(View.VISIBLE);
        display_name.setVisibility(View.GONE);
        affiliation.setVisibility(View.GONE);
    }
    public void closeCurrent() {
        getFragmentManager().popBackStack();
    }
    public void goToJoinActivity() {
        ids = new ArrayList<String>();
        names = new ArrayList<String>();
        Firebase.setAndroidContext(this);
        fbRef = new Firebase(MainActivity.FIREBASE_ENDPOINT);
        fbRef.child(SITES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Map<String, String> map = postSnapshot.getValue(Map.class);
                    ids.add(map.get(ID));
                    names.add(map.get(NAME));
                }
                if (ids.size() != 0 && names.size() != 0) {
                    affiliation_ids = ids.toArray(new String[ids.size()]);
                    affiliation_names = names.toArray(new String[names.size()]);
                    Intent join = new Intent(getApplicationContext(), JoinActivity.class);
                    join.putExtra(IDS, affiliation_ids);
                    join.putExtra(NAMES, affiliation_names);
                    startActivityForResult(join, REQUEST_CODE_JOIN);
                    overridePendingTransition(R.anim.slide_up, R.anim.stay);
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.join_error_message_firebase_read) + firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void goToLoginActivity() {
        Intent login = new Intent(this, LoginActivity.class);
        startActivityForResult(login, REQUEST_CODE_LOGIN);
    }
    public void goToAddObservationActivity(String img) {
        Intent addObservation = new Intent(this, AddObservationActivity.class);
        addObservation.putExtra(OBSERVATION, img);
        startActivityForResult(addObservation, REQUEST_CODE_ADD_OBSERVATION);
        overridePendingTransition(R.anim.slide_up, R.anim.stay);
    }
    public void goToProjectActivity(Project p) {
        Intent project = new Intent(this, ProjectActivity.class);
        project.putExtra(PROJECT, p);
        startActivity(project);
        overridePendingTransition(R.anim.slide_up, R.anim.stay);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (REQUEST_CODE_JOIN): {
                if (resultCode == Activity.RESULT_OK) {
                    if (data.getExtras().getString(JOIN).equals(GUEST)) {
                        drawer.openDrawer(GravityCompat.START);
                    } else if (data.getExtras().getString(JOIN).equals(LAUNCH)) {
                        goToLaunchFragment();
                    } else if (data.getExtras().getString(JOIN).equals(LOGIN)) {
                        signed_user = (Users) data.getSerializableExtra(NEW_USER);
                        logout.setVisible(true);
                        this.supportInvalidateOptionsMenu();
                        display_name.setText(signed_user.getmDisplayName());
                        affiliation.setText(signed_user.getmAffiliation());
                        sign_in.setVisibility(View.GONE);
                        join.setVisibility(View.GONE);
                        display_name.setVisibility(View.VISIBLE);
                        affiliation.setVisibility(View.VISIBLE);
                        goToExploreFragment();
                        drawer.openDrawer(GravityCompat.START);
                    }
                }
                break;
            }
            case(REQUEST_CODE_LOGIN): {
                if (resultCode == Activity.RESULT_OK) {
                    if (data.getStringExtra(LOGIN).equals(JOIN)) {
                        goToJoinActivity();
                    } else if (data.getStringExtra(LOGIN).equals(GUEST)) {
                        drawer.openDrawer(GravityCompat.START);
                    } else if (data.getStringExtra(LOGIN).equals(LOGIN)) {
                        signed_user = (Users) data.getSerializableExtra(SIGNED_USER);
                        logout.setVisible(true);
                        this.supportInvalidateOptionsMenu();
                        display_name.setText(signed_user.getmDisplayName());
                        affiliation.setText(signed_user.getmAffiliation());
                        sign_in.setVisibility(View.GONE);
                        join.setVisibility(View.GONE);
                        display_name.setVisibility(View.VISIBLE);
                        affiliation.setVisibility(View.VISIBLE);
                        goToExploreFragment();
                        drawer.openDrawer(GravityCompat.START);
                    }
                }
                break;
            }
            case(REQUEST_CODE_ADD_OBSERVATION): {
                if(resultCode == Activity.RESULT_OK) {

                }
                break;
            }
        }
    }
}