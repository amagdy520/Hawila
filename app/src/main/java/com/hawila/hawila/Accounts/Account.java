package com.hawila.hawila.Accounts;

/**
 * Created by Ahmed Magdy on 9/15/2017.
 */

public class Account {
    private String Email;
    private String Name;
    private String Mobile;

    public Account() {
    }

    public Account(String email, String name, String mobile) {
        Email = email;
        Name = name;
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

}
