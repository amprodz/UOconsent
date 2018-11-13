package ca.uottawa.aymen.uoconsent.model;

import android.graphics.Bitmap;

public class Person {

    String name,email;
    String picture,signature;
    public boolean expanded = false;
    public boolean parent = false;

    // flag when item swiped
    public boolean swiped = false;

    public Person(String name,String email, String picture, String signature){
        this.name=name;
        this.email=email;
        this.picture=picture;
        this.signature=signature;
    }

    public String getPicture() {
        return picture;
    }

    public String getSignature() {
        return signature;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
