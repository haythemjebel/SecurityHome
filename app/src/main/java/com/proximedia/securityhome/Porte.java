package com.proximedia.securityhome;

public class Porte {
    private String id;
    private String etat_porte;
    private int courant;
    private String image;

    public Porte(String id, String etat_porte, int courant, String image ) {
        this.id = id;
        this.etat_porte = etat_porte;
        this.courant = courant;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getEtat_porte() {
        return etat_porte;
    }

    public int getCourant() {
        return courant;
    }

    public String getImage() {
        return image;
    }


    public String toString() {
        return id + "-"+ etat_porte + "-" + courant + "-" + image;
    }

}
