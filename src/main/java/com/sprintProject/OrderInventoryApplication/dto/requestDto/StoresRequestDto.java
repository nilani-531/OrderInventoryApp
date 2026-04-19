package com.sprintProject.OrderInventoryApplication.dto.requestDto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

public class StoresRequestDto {

    private int storeId;

    private String storeName;

    private String webAddress;

    private String physicalAddress;

    private Double latitude;

    private Double longitude;

    private byte[] logo;

    private String logoMimeType;

    private String logoFilename;

    private String logoCharset;

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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StoresRequestDto that = (StoresRequestDto) o;
        return storeId == that.storeId && Objects.equals(storeName, that.storeName) && Objects.equals(webAddress, that.webAddress) && Objects.equals(physicalAddress, that.physicalAddress) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude) && Objects.deepEquals(logo, that.logo) && Objects.equals(logoMimeType, that.logoMimeType) && Objects.equals(logoFilename, that.logoFilename) && Objects.equals(logoCharset, that.logoCharset) && Objects.equals(logoLastUpdated, that.logoLastUpdated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, storeName, webAddress, physicalAddress, latitude, longitude, Arrays.hashCode(logo), logoMimeType, logoFilename, logoCharset, logoLastUpdated);
    }


}