package com.example.midtermfull;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class User{
    String name, pass, role, phoneNumber, age, status, userName;
    int ellipsis;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getEllipsis() {return ellipsis; }

    public void setEllipsis() {this.ellipsis = ellipsis; }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User(String userName, String pass, String name, String role, int ellipsis, String phoneNumber, String status, String age) {
        this.userName = userName;
        this.name = name;
        this.pass = pass;
        this.role = role;
        this.ellipsis = ellipsis;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.age = age;
    }
}
