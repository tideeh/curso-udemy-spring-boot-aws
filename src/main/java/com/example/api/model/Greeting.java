package com.example.api.model;

import java.util.Date;

public class Greeting {
    
    private final long id;
    private final String text;
    private Date date;


    public Greeting(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public Greeting(long id, String text, Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    public long getId() {
        return this.id;
    }

    public String getText() {
        return this.text;
    }


    public Date getDate() {
        return this.date;
    }


}
