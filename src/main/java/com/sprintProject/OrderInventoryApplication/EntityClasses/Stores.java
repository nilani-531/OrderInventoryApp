package com.sprintProject.OrderInventoryApplication.EntityClasses;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
@Table(name = "stores")
public class Stores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private int storeId;

    @NotBlank
    @Column(name = "store_name",unique = true)
    private String storeName;

    @Column(name = "web_address")
    private String webAddress;

    @Column(name = "physical_address")
    private String physicalAddress;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_mime_type")
    private String logoMimeType;

    @Column(name = "logo_filename")
    private String logoFilename;

    @Column(name = "logo_charset")
    private String logoCharset;

    @Column(name = "logo_last_updated")
    private LocalDate logoLastUpdated;


    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoMimeType() {
        return logoMimeType;
    }

    public void setLogoMimeType(String logoMimeType) {
        this.logoMimeType = logoMimeType;
    }

    public String getLogoFilename() {
        return logoFilename;
    }

    public void setLogoFilename(String logoFilename) {
        this.logoFilename = logoFilename;
    }

    public String getLogoCharset() {
        return logoCharset;
    }

    public void setLogoCharset(String logoCharset) {
        this.logoCharset = logoCharset;
    }

    public LocalDate getLogoLastUpdated() {
        return logoLastUpdated;
    }

    public void setLogoLastUpdated(LocalDate logoLastUpdated) {
        this.logoLastUpdated = logoLastUpdated;
    }
}