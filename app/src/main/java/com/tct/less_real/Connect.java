package com.tct.less_real;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed on 2/18/2015.
 */
public class Connect extends AsyncTask<String,Integer,String>
{
    public Quote getQuote(int pos) {
        return objList.get(pos);
    }

    static ArrayList<Quote> objList=new ArrayList<>();
    static ListView mainList;
    static LIster customAdapter;
    static boolean set=false;
    Context act;
    ActionBar bar;
    static ProgressBar pBar;

    public Connect() {

    }
    public Connect(ListView mainList,Context act,ActionBar bar)
    {
        pBar=(new MainActivity()).mProgress;
        this.act=act;
        this.bar=bar;
        //this.txt=txt;
        //this.img=img;
        //this.says=says;
        this.mainList=mainList;
    }
public static ArrayList<Quote> getList()
    {
        return objList;
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
     //   pBar.setProgress(0);
        if(values[0].intValue()==0)
            pBar.setVisibility(View.VISIBLE);
        else
            pBar.setVisibility(View.GONE);
    }
    private void synkingShip(String link) {
        Log.d("State", "Calling Http at: " + link);
        String res = getText(link);
        res = "{Quote:" + res + "}";
        try {
            objList = parseJSON(new JSONObject(res));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected String doInBackground(String... arg) {
       /* if(mainList.getAdapter()!=null)
            Log.d("Count", mainList.getAdapter().getCount()+"");
        */
        publishProgress(0);
        String link = arg[0];
        synkingShip(link);
        publishProgress(1);
        return "";
        /*
        Log.d("State", "Calling Http1");
        send.says = getText(says);
        Log.d("State", "Calling Http2");
        try {
            URL newurl = new URL(imgLInk);
            send.img = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            return send;
        } catch (Exception e) {
            Log.d("State", "Error!!!!..: "+e.toString());
            return send;
        }
        */
    }
    public void addToStaticList(ArrayList<Quote> objList)
    {
        Log.d("NULLS", "Custom Adapter null");
        Log.d("State", "Added in List");
        customAdapter = new LIster(objList, act);
        Log.d("State", "Constructor Lister");
        mainList.setAdapter(customAdapter);
        Log.d("State", "SET listner");
//        mainList.setOnScrollListener(new EndlessScrollListener(mainList, act, bar));
        Log.d("Resume", "Adapted");
    }
    public void addToList(ArrayList<Quote> objList)
    {
        Log.d("NULLS", "Custom Adapter null");
        Log.d("State", "Added in List");
        customAdapter = new LIster(objList, act);
        Log.d("State", "Constructor Lister");
        mainList.setAdapter(customAdapter);
        Log.d("State", "SET listner");
        mainList.setOnScrollListener(new EndlessScrollListener(mainList, act, bar));
        Log.d("Resume", "Adapted");
    }

    protected void onPostExecute(String res) {
        Log.d("State", "Downloaded");

		//ading it in 10 times


        if(customAdapter==null) {
            addToList(objList);
        }
        else
        {

            ((MainActivity)act).runOnUiThread(new Runnable() {
                public void run() {
                    customAdapter.notifyDataSetChanged();
                    int firstVisibleItem = mainList.getFirstVisiblePosition();
                    int oldCount = customAdapter.getCount();
                    View view = mainList.getChildAt(0);
                    int pos = (mainList == null ? 0 : mainList.getTop());
                    customAdapter.notifyDataSetChanged();
                    mainList.setSelectionFromTop(firstVisibleItem, 0);

                }
            }); // end of runOnUiThread

           // ((MainActivity)act).start+=10;
            Log.d("Counter",""+((MainActivity)act).start);

        }
        //  img.setImageBitmap(res.img);
        Log.d("State", "ALL Done !! STOp");

    }

    private ArrayList<Quote> parseJSON(JSONObject mySon)
    {

        String imgLInk;
        try
        {
            JSONArray qArray= mySon.getJSONArray("Quote");
            for(int i=0;i<qArray.length();i++) {
                Quote send = new Quote();
                JSONObject jQuote = qArray.getJSONObject(i);
                send.says = jQuote.getString("author");
                send.text = jQuote.getString("quote");
                send.anime = jQuote.getString("anime");

                imgLInk = jQuote.getString("image");
            //    Log.d("State", "Calling Http2");
                try {
                    URL newurl = new URL(imgLInk);
                    send.img = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());

                } catch (Exception e) {
                    e.printStackTrace();

                }
                objList.add(send);
            }
            //Log.d("JSON",jQuote.getString("author"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return objList;
    }


    protected String getText(String link) {
        try {
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter
                    (conn.getOutputStream());
            wr.write(" ");
            wr.flush();
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line=null;
            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();
        } catch (Exception e) {
            return e.toString();
        }
    }
}