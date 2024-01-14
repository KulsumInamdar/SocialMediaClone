package com.example.socialmediaclone;

import java.util.ArrayList;

public class ProfileObject {

    private String name;
    private ArrayList<String> followers;
    private String email;

    ProfileObject(String name,ArrayList<String> followers,String email)
    {
        this.name = name;
        this.followers =  followers;
        this.email = email;
    }

    public  String getName()
    {
        return  name;
    }

    public String getEmail()
    {

        return  email;
    }

    public  ArrayList<String> getFollowers()
    {

        return followers;
    }
}
