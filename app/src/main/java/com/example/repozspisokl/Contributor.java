package com.example.repozspisokl;

class Contributor {

    private String login;
    private int contributions;

    // И другие поля
    //String html_url;

    @Override
    public String toString() {
        return "               " + login + ";"  + contributions;
    }
}

