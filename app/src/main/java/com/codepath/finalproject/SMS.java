package com.codepath.finalproject;

/**
 * Created by andreadeoli on 7/13/17.
 */

public class SMS {
    // Number from witch the sms was send
    private String number;
    // SMS text body
    private String body;
    private String contact;
    private String date;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
