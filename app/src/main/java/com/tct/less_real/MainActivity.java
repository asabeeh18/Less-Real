package com.tct.less_real;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;


public class MainActivity extends ActionBarActivity {

    public int start=-1;
    String url= computeURL();
    SharedPreferences pref;
    String user;
   // ActionBar actionBar;
    MenuItem mn;
    //Context act=this;

    protected static ProgressBar mProgress;
    ListView mainList;


    @Override
    protected void onStop() {
        super.onStop();
        Log.d("State", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("State", "onDestroy");
        Process.killProcess(Process.myPid());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("State", "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("State", "onResume");

        //LIster customAdapter = new LIster(objList, act);
        if(mainList!=null)
            Log.d("NULLS","MAIN LIST NOT NULL");
    else
            Log.d("NULLS","MAINLIST NULL NULL NULL");

    if(mainList.getAdapter()!=null)
        {
            Log.d("NULLS","MAIN Adapter LIST NOT NULL");

            new Connect(mainList,this,getActionBar()).execute(url);
        }
        else
            Log.d("NULLS","MAINLIST NULL NULL NULL");

        //mainList.setAdapter(customAdapter);

    }

/*    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("MainList",mainList.onSaveInstanceState());
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null) {
            mainList = savedInstanceState.getParcelable("MainList");
        }
        Log.d("State", "onCreate");
        mProgress = (ProgressBar) findViewById(R.id.pBar);
        //URL constants
        mainList = (ListView)findViewById(R.id.listView1);
        //ImageView img=(ImageView)findViewById(R.id.img);
        //Log.d("bar",""+getActionBar().toString());

        //getActionBar() for future
        new Connect(mainList,this,getActionBar()).execute(url);

        /*MARKED future scope

        pref = getSharedPreferences("cookie", Context.MODE_PRIVATE);
        if (pref.contains("user"))
        {
            user=pref.getString("user","");
            Log.d("State", "Found: " + user);
        }
        else
            Log.d("State","USer NOTFOUND: "+user);
       // new Connect(mainList,this,actionBar).execute(url);

        */
        /*MARKED future scope
        mainList.setLongClickable(true);
        mainList.setClickable(true);
        mainList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(act, "Added to favourites", Toast.LENGTH_LONG).show();
                Log.d("long clicked", "pos: " + pos);

                return true;
            }
        });
        */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        /*MArked Future Scope
        if(user==null)
        {
            mn= menu.findItem(R.id.login);
                    mn.setTitle("Login");
        }
        else
        {
            MenuItem mn= menu.findItem(R.id.login);
            mn.setTitle(user);
        }
        Log.d("State","changed:");
        */
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


    //MARKED future scope beyond this point
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.login) {
            if(user==null)
            {
                loginSeq();

                pref = getSharedPreferences("cookie", Context.MODE_PRIVATE);
                if (pref.contains("user")) {
                    user = pref.getString("user", "");
                    mn.setTitle(user);
                    finish();
                }
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void loginSeq()
    {
        //Change ActionBar after Login
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        /*
        if(code==1)
            findViewById(R.id.login).setVisibility(View.GONE);
        */
    }
}
