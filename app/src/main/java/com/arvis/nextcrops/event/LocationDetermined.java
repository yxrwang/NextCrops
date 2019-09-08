package com.arvis.nextcrops.event;

import android.location.Location;

public class LocationDetermined {

    public final Location location;

    public LocationDetermined(Location location){

        this.location = location;
    }
}
