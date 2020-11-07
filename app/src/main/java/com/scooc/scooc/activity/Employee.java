package com.scooc.scooc.activity;

/**
 * Created by Belal on 9/30/2017.
 */

public class Employee {
    int id;
    String source, dest,pickuptime, startdate,enddate,multintent;

    public Employee(int id, String source, String dest, String pickuptime,String startdate,String enddate,String multintent ) {
        this.id = id;
        this.source = source;
        this.dest = dest;
        this.pickuptime = pickuptime;
        this.startdate = startdate;
        this.enddate = enddate;
        this.multintent = multintent;
    }

    public int getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getDest() {
        return dest;
    }
    public String getPickuptime() {
        return pickuptime;
    }

    public String getStartdate() {
        return startdate;
    }

    public String getEnddate() {
        return enddate;
    }
    public String getMultintent() {
        return multintent;
    }

}
