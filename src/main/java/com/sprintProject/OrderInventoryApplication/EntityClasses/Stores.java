package com.sprintProject.OrderInventoryApplication.EntityClasses;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "Stores")
public class Stores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int store_id;
    @NotNull
    private String store_name;
    private String web_address;
    private String physical_address;
    private double latitude;
    private double longitude;
    @Lob
    private byte[] logo;
    private String logo_mime_type;
    private String logo_filename;
    private String logo_charset;
    private LocalDate logo_last_updated;

    public int getStoreId() {
        return store_id;
    }
    public void setStoreId(int store_id) {
        this.store_id = store_id;
    }
    public String getStoreName() {
        return store_name;
    }
    public void setStoreName(String store_name) {
        this.store_name = store_name;
    }
    public String getWebAddress() {
        return web_address;
    }
    public void setWebAddress(String web_address) {
        this.web_address = web_address;
    }
    public String getPhysicalAddress() {
        return physical_address;
    }
    public void setPhysicalAddress(String physical_address) {
        this.physical_address = physical_address;
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
        return logo_mime_type;
    }
    public void setLogoMimeType(String logo_mime_type) {
        this.logo_mime_type = logo_mime_type;
    }
    public String getLogoFilename() {
        return logo_filename;
    }
    public void setLogoFilename(String logo_filename) {
        this.logo_filename = logo_filename;
    }
    public String getLogoCharset() {
        return logo_charset;
    }
    public void setLogoCharset(String logo_charset) {
        this.logo_charset = logo_charset;
    }
    public LocalDate getLogoLastUpdated() {
        return logo_last_updated;
    }
    public void setLogoLastUpdated(LocalDate logo_last_updated) {
        this.logo_last_updated = logo_last_updated;
    }
}
