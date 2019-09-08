package com.arvis.nextcrops.event;

import com.arvis.nextcrops.model.Estimate;

public class Soil {

    public final Estimate estimate;

    public final String id;

    public Soil(String id, Estimate estimate){

        this.id = id;
        this.estimate = estimate;
    }
}
