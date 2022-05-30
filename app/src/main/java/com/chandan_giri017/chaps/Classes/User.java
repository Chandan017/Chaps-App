package com.chandan_giri017.chaps.Classes;

public class User {

    private String name , phoneNumber , profileImage , uid , about;

    public User(){

    }

    public User(String name, String phoneNumber, String profileImage, String uid, String about) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.uid = uid;
        this.about = about;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
