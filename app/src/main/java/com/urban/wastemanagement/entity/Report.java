package com.urban.wastemanagement.entity;

import android.location.Location;

import java.util.List;

public class Report {
    private String reportId;
    private String description;
    private String wasteType;
    private double lat;
    private double lng;
    private String address;

    public Report() {
    }

    public Report(String reportId, String description, String wasteType, double lat, double lng,
            String address) {
        this.reportId = reportId;
        this.description = description;
        this.wasteType = wasteType;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWasteType() {
        return wasteType;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
