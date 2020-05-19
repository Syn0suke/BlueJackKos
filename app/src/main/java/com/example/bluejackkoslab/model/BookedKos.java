package com.example.bluejackkoslab.model;

public class BookedKos {
    private String BookingId, UserId, kosName, kosPrice, kosFacility, kosAddress, kosLongitude, kosLatitude, BookingDate;

    public BookedKos(String bookingId, String userId, String kosName, String kosPrice, String kosFacility, String kosAddress, String kosLongitude, String kosLatitude, String bookingDate) {
        BookingId = bookingId;
        UserId = userId;
        this.kosName = kosName;
        this.kosPrice = kosPrice;
        this.kosFacility = kosFacility;
        this.kosAddress = kosAddress;
        this.kosLongitude = kosLongitude;
        this.kosLatitude = kosLatitude;
        BookingDate = bookingDate;
    }

    public String getBookingId() {
        return BookingId;
    }

    public void setBookingId(String bookingId) {
        BookingId = bookingId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getKosName() {
        return kosName;
    }

    public void setKosName(String kosName) {
        this.kosName = kosName;
    }

    public String getKosPrice() {
        return kosPrice;
    }

    public void setKosPrice(String kosPrice) {
        this.kosPrice = kosPrice;
    }

    public String getKosFacility() {
        return kosFacility;
    }

    public void setKosFacility(String kosFacility) {
        this.kosFacility = kosFacility;
    }

    public String getKosAddress() {
        return kosAddress;
    }

    public void setKosAddress(String kosAddress) {
        this.kosAddress = kosAddress;
    }

    public String getKosLongitude() {
        return kosLongitude;
    }

    public void setKosLongitude(String kosLongitude) {
        this.kosLongitude = kosLongitude;
    }

    public String getKosLatitude() {
        return kosLatitude;
    }

    public void setKosLatitude(String kosLatitude) {
        this.kosLatitude = kosLatitude;
    }

    public String getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(String bookingDate) {
        BookingDate = bookingDate;
    }
}
