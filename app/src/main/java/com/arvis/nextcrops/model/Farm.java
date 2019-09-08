package com.arvis.nextcrops.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class Farm implements Parcelable {

    private String id;

    private String name;

    private double lat;

    private double lon;

    public Farm(String name, double lat, double lon){

        this.id = UUID.randomUUID().toString();

        this.name = name;

        this.lat = lat;

        this.lon = lon;
    }

    protected Farm(Parcel in) {
        id = in.readString();
        name = in.readString();
        lat = in.readDouble();
        lon = in.readDouble();
    }

    public static final Creator<Farm> CREATOR = new Creator<Farm>() {
        @Override
        public Farm createFromParcel(Parcel in) {
            return new Farm(in);
        }

        @Override
        public Farm[] newArray(int size) {
            return new Farm[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeDouble(lat);
        parcel.writeDouble(lon);
    }
}
