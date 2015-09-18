package com.tct.less_real;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

public class Stored extends ActionBarActivity {
    ListView mainList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        ArrayList<Quote> quote=new ArrayList<>();;
        SharedPreferences pref=getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        int suffix=pref.getInt("Number", 0);
        for(int i=0;i<suffix;i++)
        {
            String text=pref.getString("Quote"+i,"No Data");
            String says=pref.getString("Character"+i,"No Data");

            quote.add(new Quote(text,says));
        }
        mainList = (ListView)findViewById(R.id.listView1);
        Connect connectify= new Connect(mainList,this,getActionBar());
        connectify.addToStaticList(quote);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stored, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
