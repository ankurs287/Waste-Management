package com.urban.wastemanagement.entity;

import java.util.Date;

public class Report {
    private String reportId;
    private String reportedBy;
    private Date reportedOn;
    private boolean resolved;
    private String description;
    private String wasteType;
    private String odour;
    private boolean critical;
    private double lat;
    private double lng;
    private String address;

    public Report() {
    }

    public Report(String reportId, String reportedBy, boolean resolved, String description,
            String wasteType, String odour, boolean critical, double lat, double lng,
            String address) {
        this.reportId = reportId;
        this.reportedBy = reportedBy;
        this.resolved = resolved;
        this.description = description;
        this.wasteType = wasteType;
        this.odour = odour;
        this.critical = critical;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.reportedOn = new Date(System.currentTimeMillis());
    }

    public String getOdour() {
        return odour;
    }

    public void setOdour(String odour) {
        this.odour = odour;
    }

    public boolean isCritical() {
        return critical;
    }

    public void setCritical(boolean critical) {
        this.critical = critical;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
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
