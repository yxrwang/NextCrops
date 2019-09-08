package com.arvis.nextcrops.model;

public class Estimate {

    private Entity asc_oso;

    private Percentage clay;

    private Percentage ph;

    private Percentage organic_carbon;

    private Percentage electrical_conductivity;

    private Entity soil_texture_topsoil;

    private Entity soil_texture_subsoil;

    public Entity getAsc_oso() {
        return asc_oso;
    }

    public void setAsc_oso(Entity asc_oso) {
        this.asc_oso = asc_oso;
    }

    public Percentage getClay() {
        return clay;
    }

    public void setClay(Percentage clay) {
        this.clay = clay;
    }

    public Percentage getPh() {
        return ph;
    }

    public void setPh(Percentage ph) {
        this.ph = ph;
    }

    public Percentage getOrganic_carbon() {
        return organic_carbon;
    }

    public void setOrganic_carbon(Percentage organic_carbon) {
        this.organic_carbon = organic_carbon;
    }

    public Percentage getElectrical_conductivity() {
        return electrical_conductivity;
    }

    public void setElectrical_conductivity(Percentage electrical_conductivity) {
        this.electrical_conductivity = electrical_conductivity;
    }

    public Entity getSoil_texture_topsoil() {
        return soil_texture_topsoil;
    }

    public void setSoil_texture_topsoil(Entity soil_texture_topsoil) {
        this.soil_texture_topsoil = soil_texture_topsoil;
    }

    public Entity getSoil_texture_subsoil() {
        return soil_texture_subsoil;
    }

    public void setSoil_texture_subsoil(Entity soil_texture_subsoil) {
        this.soil_texture_subsoil = soil_texture_subsoil;
    }
}
