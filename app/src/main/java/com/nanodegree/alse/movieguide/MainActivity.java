package com.nanodegree.alse.movieguide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        MoviedbFragment.OnClickItemListener{

    //variable to store the position of spinner for orientation change
    private int SavedPosition = -1;
    boolean mTwoPane;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        Log.v("InsideOnCreate", "DetailFrgament");

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Movie");
        if(findViewById(R.id.fragment_container)!=null){
            mTwoPane = true;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DetailFragment(),DETAILFRAGMENT_TAG).commit();
        }
        else
         mTwoPane=false;
        //Restore the spinner value [Orientation change]
        if (savedInstanceState != null) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            SavedPosition = sharedPref.getInt("Position", 0);
        }

    }
   //Save the spinner position for Orientation change
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Store the spinner position when activity is destroyed
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        int position = sharedPref.getInt("Position", 0);
        outState.putInt("position", position);
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //Add Spinner component to Action bar to provide the user choice change the category
        MenuItem item = menu.findItem(R.id.action_spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        //set the adapter to spinner to show the list of values
        if(spinner !=null){
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.pref_sort_entries,R.layout.spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);
        }
        //To restore the spinner value when orientation changes
        if(SavedPosition >= 0){
            spinner.setSelection(SavedPosition);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selection = getResources().getStringArray(R.array.pref_sort_entryValues)[position];

        //Store the preference to file to access it fragment
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.pref_sort_key), selection);

        //Store the position to access it in OnSaveInstance - handling Orientation change
        editor.putInt("Position", position);
        editor.commit();


        //Update the grid view when the user selects different option in spinner
        MoviedbFragment fragment = (MoviedbFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragment.updateMovieList();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClickListen(String jsonStr, int position) {
        if(mTwoPane){
            Bundle b = new Bundle();
            b.putString(DetailActivity_old.EXTRATEXT, jsonStr);
            b.putInt(DetailActivity_old.POSITION, position);
            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment,DETAILFRAGMENT_TAG);

        }
        else{
            Intent intent = new Intent(this, DetailActivity_old.class);
            intent.putExtra(DetailActivity_old.EXTRATEXT, jsonStr);
            //  }
            //Sending Jsonstr to detail view to retrive ralated string values

            intent.putExtra(DetailActivity_old.POSITION, position);
            startActivity(intent);

        }
    }
}
