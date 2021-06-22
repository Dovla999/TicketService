package model;

import java.util.UUID;

public class Location {

    private Double longitude;
    private Double latitude;
    private String street;
    private String city;
    private String postNumber;

    public Location(Double longitude, Double latitude, String street, String city, String postNumber) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.street = street;
        this.city = city;
        this.postNumber = postNumber;
    }

    public Location() {
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(String postNumber) {
        this.postNumber = postNumber;
    }
}
