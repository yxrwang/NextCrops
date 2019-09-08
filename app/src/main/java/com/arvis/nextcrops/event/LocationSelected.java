package com.arvis.nextcrops.event;

public class LocationSelected {

    public final double lat;

    public final double lon;

    public LocationSelected(double lat, double lon){

        this.lat = lat;

        this.lon = lon;
    }
}
