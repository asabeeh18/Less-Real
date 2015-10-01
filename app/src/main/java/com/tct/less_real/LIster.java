package com.tct.less_real;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ahmed on 2/18/2015.
 */

public class LIster extends BaseAdapter {
    Context context;
    ArrayList<Quote> objList;
    Context act;
    public LIster() {

    }
    public LIster(ArrayList <Quote> objList,Context act)
    {
        this.act=act;
        //  this.context=context;
        //this.codeLearnLessons=codeLearnLessons;
        this.objList = objList;
        Log.d("State","Constructor:LIster");
    }
    public void setQuotes(ArrayList <Quote> quotes) {
        this.objList = quotes;
        //update the adapter to reflect the new set of movies
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return objList.size();
    }

    @Override
    public Quote getItem(int arg0) {
        // TODO Auto-generated method stub
        return objList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        if(arg1==null)
        {
            LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arg1 = inflater.inflate(R.layout.listitem, arg2,false);
        }


        TextView quote = (TextView)arg1.findViewById(R.id.quote);
        TextView says = (TextView)arg1.findViewById(R.id.says);
        TextView animeName = (TextView)arg1.findViewById(R.id.animeName);
      //  TextView animeName = (TextView)arg1.findViewById(R.id.animeName);
       // FlowTextView chapterDesc = (FlowTextView)arg1.findViewById(R.id.textView2);
        ImageView bM=(ImageView)arg1.findViewById(R.id.imageView1);
        Quote chapter = objList.get(arg0);
        quote.setOnTouchListener(null);
        says.setText(chapter.says);
        animeName.setText(chapter.anime);
        //animeName.setText(chapter.anime);
        quote.setText(chapter.text);
      //  chapterDesc.invalidate();
        bM.setImageBitmap(chapter.img);
      //  Log.d("State", "Got View Inflated");

        return arg1;
    }

}
