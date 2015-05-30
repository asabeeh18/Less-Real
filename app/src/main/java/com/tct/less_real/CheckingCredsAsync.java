package com.tct.less_real;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Ahmed on 2/17/2015.
 */
public class CheckingCredsAsync extends AsyncTask<String,Void,String>
{
    SharedPreferences pref;
    Menu ack;
    Context act;
    String user;
    CheckingCredsAsync(Context act,Menu ack)
    {
        this.ack=ack;
        this.act=act;
    }
    @Override
    protected String doInBackground(String... arg)
    {
        try
        {
            user = arg[0];
            String pass = arg[1];
            String data = URLEncoder.encode("username", "UTF-8")
                    + "=" + URLEncoder.encode(user, "UTF-8");
            data += "&" + URLEncoder.encode("psswd", "UTF-8")
                    + "=" + URLEncoder.encode(pass, "UTF-8");
            String ip = "http://opnz.freeiz.com/login.php";
            URL url = new URL(ip);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush(); //flush post requests
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();
        }
        catch(Exception e)
        {
            return e.toString();
        }
    }
    @Override
    protected void onPostExecute(String res)
    {
        MenuItem item = ack.findItem(R.id.login);
        if(res.equals("YES"))
        {
            Toast.makeText(act, "Login Successful!!",Toast.LENGTH_LONG).show();
            SharedPreferences pref=act.getSharedPreferences("cookie", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("user",user);
            editor.apply();
            Log.d("State","logged in: "+user);

        }
        else if(res.equals("NO"))
        {
            Log.d("State","Not logged in: "+user);
            Toast.makeText(act, "Wrong Username/Password! TRY AGAIN!",Toast.LENGTH_LONG).show();
        }
        else
            Log.d("State","LoginPostError::"+res);
    }
}
