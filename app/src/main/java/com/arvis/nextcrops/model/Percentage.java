package com.arvis.nextcrops.model;

public class Percentage {

    private String description;

    private double topsoil;

    private double topsoil_stddev;

    private double subsoil;

    private double subsoil_stddev;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTopsoil() {
        return topsoil;
    }

    public void setTopsoil(double topsoil) {
        this.topsoil = topsoil;
    }

    public double getTopsoil_stddev() {
        return topsoil_stddev;
    }

    public void setTopsoil_stddev(double topsoil_stddev) {
        this.topsoil_stddev = topsoil_stddev;
    }

    public double getSubsoil() {
        return subsoil;
    }

    public void setSubsoil(double subsoil) {
        this.subsoil = subsoil;
    }

    public double getSubsoil_stddev() {
        return subsoil_stddev;
    }

    public void setSubsoil_stddev(double subsoil_stddev) {
        this.subsoil_stddev = subsoil_stddev;
    }
}
