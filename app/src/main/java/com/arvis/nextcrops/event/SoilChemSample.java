package com.arvis.nextcrops.event;

import com.arvis.nextcrops.model.Sample;

import java.util.List;

public class SoilChemSample {

    public final List<Sample> samples;

    public SoilChemSample(List<Sample> samples){

        this.samples = samples;
    }
}
