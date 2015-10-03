package com.tct.less_real;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Process;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public static int start=-1;
    String url= computeURL();
    SharedPreferences pref;
    String user;
   // ActionBar actionBar;
    MenuItem mn;
    Connect connectify;
    Context act;
    static int firstVisibleItem=0;
    protected static ProgressBar mProgress;
    ListView mainList;

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        firstVisibleItem=mainList.getFirstVisiblePosition();
        super.onPause();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  /*      if(savedInstanceState!=null)
        {
            ArrayList<Quote> qu=savedInstanceState.getParcelableArrayList("MainList");
            connectify.addToList(qu);
            Log.d("Resume", "Applied");
        }
    */
        Log.d("State", "onCreate");
        mProgress = (ProgressBar) findViewById(R.id.pBar);
        //URL constants
        mainList = (ListView)findViewById(R.id.listView1);
        //ImageView img=(ImageView)findViewById(R.id.img);
        //Log.d("bar",""+getActionBar().toString());

        //getActionBar() for future
        connectify= new Connect(mainList,this,getActionBar());
        connectify.execute(url);
        //END LOADING



        //Prep for Data Storage
        act=getApplicationContext();
        pref=getSharedPreferences("data", Context.MODE_PRIVATE);

        //Favourites
        mainList.setLongClickable(true);
        mainList.setClickable(true);
        mainList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                       int pos, long id) {
            Quote quote=connectify.getQuote(pos);
            SharedPreferences.Editor editor = pref.edit();
            int suffix=pref.getInt("Number", 0);

            editor.putString("Quote"+suffix,quote.text);
            editor.putString("Character" + suffix, quote.says);
            editor.putInt("Number", suffix + 1);
            editor.apply();
            Toast.makeText(act,"Added to favourites", Toast.LENGTH_SHORT).show();
            Log.d("long clicked", "pos: " + pos);
            return true;
        }
    });
    }
    public void clearData()
    {
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public synchronized final String computeURL()
    {
        String order="desc";
        String order_by="timestamp";
        int num=1;
        start+=1;
        return "http://www.less-real.com/api/v1/quotes?from="+start+"&num="+num+"&o="+order_by+"&o_d="+order;
    }
/*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Resume", "Saving");
        outState.putParcelableArrayList("MainList", connectify.getList());
        Log.d("Resume", "Saved");

    }
*/
    //MARKED future scope beyond this point
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    public void favourites(MenuItem item){
        Intent intent=new Intent(this,Stored.class);
        startActivity(intent);
    }
}
