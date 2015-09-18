package com.tct.less_real;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ahmed on 2/18/2015.
 */
public class Quote implements Parcelable {
    public String text;
    public String says;
    public String anime;
    public Bitmap img;

    public Quote() {

    }
    public Quote(String text,String says) {
        this.text=text;
        this.says=says;
    }
    protected Quote(Parcel in) {
        text = in.readString();
        says = in.readString();
        anime = in.readString();
        img = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(says);
        dest.writeString(anime);
        dest.writeValue(img);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Quote> CREATOR = new Parcelable.Creator<Quote>() {
        @Override
        public Quote createFromParcel(Parcel in) {
            return new Quote(in);
        }

        @Override
        public Quote[] newArray(int size) {
            return new Quote[size];
        }
    };
}