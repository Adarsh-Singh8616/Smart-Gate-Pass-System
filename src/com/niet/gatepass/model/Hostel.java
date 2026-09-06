package com.niet.gatepass.model;

import java.math.BigDecimal;

public class Hostel {
    private int hostelId;
    private String hostelName;
    private int totalRooms;
    private BigDecimal hostelFee;
    private BigDecimal messFee;
    private String facilities;

    public Hostel() {}

    public int getHostelId() { return hostelId; }
    public void setHostelId(int hostelId) { this.hostelId = hostelId; }

    public String getHostelName() { return hostelName; }
    public void setHostelName(String hostelName) { this.hostelName = hostelName; }

    public int getTotalRooms() { return totalRooms; }
    public void setTotalRooms(int totalRooms) { this.totalRooms = totalRooms; }

    public BigDecimal getHostelFee() { return hostelFee; }
    public void setHostelFee(BigDecimal hostelFee) { this.hostelFee = hostelFee; }

    public BigDecimal getMessFee() { return messFee; }
    public void setMessFee(BigDecimal messFee) { this.messFee = messFee; }

    public String getFacilities() { return facilities; }
    public void setFacilities(String facilities) { this.facilities = facilities; }
}
