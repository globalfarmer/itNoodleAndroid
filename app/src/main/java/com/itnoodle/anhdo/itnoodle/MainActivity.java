package com.itnoodle.anhdo.itnoodle;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.itnoodle.anhdo.itnoodle.dummy.AnnounceContent;
import com.itnoodle.anhdo.itnoodle.dummy.ScoreboardContent;

public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        AnnounceFragment.OnListFragmentInteractionListener,
        ScoreboardFragment.OnListFragmentInteractionListener,
        Profile.OnFragmentInteractionListener {

    private android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    private AnnounceFragment announceFragment = new AnnounceFragment();
    private ScoreboardFragment scoreboardFragment = new ScoreboardFragment();
    private Profile profile = new Profile();

    private static final String LOG_TAG = "MAIN_ACTIVITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = this.getDelegate().findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = this.getDelegate().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = this.getDelegate().findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = this.getDelegate().findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_announce) {
            setTitle(getResources().getString(R.string.title_announce));
            fragmentManager.beginTransaction().replace(R.id.fragment, announceFragment).commit();
        } else if (id == R.id.nav_scoreboard) {
            setTitle(getResources().getString(R.string.title_scoreboard));
            fragmentManager.beginTransaction().replace(R.id.fragment, scoreboardFragment).commit();
        }
//        else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        }
        else if (id == R.id.nav_profile) {
            setTitle(getResources().getString(R.string.title_profile));
            fragmentManager.beginTransaction().replace(R.id.fragment, profile).commit();
        }

        DrawerLayout drawer = this.getDelegate().findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(AnnounceContent.AnnounceItem item) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.url));
        if(intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
//        Log.i(LOG_TAG, item.title);
    }

    @Override
    public void onListFragmentInteraction(ScoreboardContent.ScoreboardItem item) {
        if(!item.uploadTime.equals(ScoreboardContent.ScoreboardItem.SCOREBOARD_NOT_UPLOADED)) {
            Log.i(LOG_TAG, item.uploadTime);
            Log.i(LOG_TAG, item.url);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+item.url));
            if(intent.resolveActivity(getPackageManager()) != null)
                startActivity(intent);
        }
        else
        {
            Toast toast = Toast.makeText(MainActivity.this, item.name+" ("+item.code +") " + item.uploadTime, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.getView().setBackgroundColor(Color.YELLOW);
            toast.show();
        }
        Log.i(LOG_TAG, item.code);
    }
}
