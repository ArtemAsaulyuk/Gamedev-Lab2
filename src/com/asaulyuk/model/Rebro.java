package com.asaulyuk.model;

import org.apache.commons.graph.Edge;

public class Rebro implements Edge {

    Placement placement;

    Boolean valid = true;

    String address;

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Boolean getValid() {
        return valid;
    }

    public Rebro(String address, Placement placement) {
        this.address= address;
        this.placement = placement;
    }

    public String getAddress() {
        return address;
    }
}
