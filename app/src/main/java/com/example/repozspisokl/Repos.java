package com.example.repozspisokl;

import com.google.gson.annotations.SerializedName;

public class Repos {

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}