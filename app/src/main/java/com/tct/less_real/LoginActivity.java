package com.tct.less_real;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class LoginActivity extends ActionBarActivity {
    private EditText username,password;
    private TextView tv;
    private Menu mn;
    SharedPreferences pref;
    public static int done=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        //Intent intent = getIntent();
    }
    public void checkCreds(View view)
    {
        String usr=username.getText().toString();
        String pass=password.getText().toString();
        new CheckingCredsAsync(this,mn).execute(usr,pass);
        //pref = getSharedPreferences("cookie", Context.MODE_PRIVATE);

        finish();
       // tv=(TextView)findViewById(R.id.chk);
        //this.tv.setText("DONE!");
       // finish();
        //this.tv.setText("***");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        mn=menu;
        getMenuInflater().inflate(R.menu.menu_main,menu);
        setTitle("Login");
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
