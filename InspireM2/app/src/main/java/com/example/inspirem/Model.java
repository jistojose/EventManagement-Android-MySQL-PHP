package com.example.inspirem;

import android.widget.ImageView;

public class Model {
    String id,name,username,email,phone,password,confirmpassword;

    public Model( String id ,String name,String username,String email,String phone,String password,String confirmpassword) {
        this.name = name;
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.confirmpassword = confirmpassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
