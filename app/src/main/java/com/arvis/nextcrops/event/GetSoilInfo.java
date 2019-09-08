package com.arvis.nextcrops.event;

public class GetSoilInfo {

    public final String id;

    public final double lat;

    public final double lon;

    public GetSoilInfo(String id, double lat, double lon){

        this.id = id;

        this.lat = lat;

        this.lon = lon;
    }
}
