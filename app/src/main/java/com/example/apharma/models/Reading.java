package com.example.apharma.models;

import java.util.Date;

public class Reading {

    private int id;
    private int readingValue;

    private Date timeStamp;

    public Reading()
    {
    }

    @Override public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Reading reading = (Reading) o;
        return id == reading.id && readingValue == reading.readingValue
                && timeStamp.equals(reading.timeStamp);
    }

    public Reading(int readingValue, Date timeStamp)
    {
        this.readingValue = readingValue;
        this.timeStamp = timeStamp;
    }

    @Override public String toString()
    {
        return "Reading{" + "id=" + id + ", readingValue=" + readingValue
                + ", timeStamp=" + timeStamp + '}';
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getReadingValue()
    {
        return readingValue;
    }

    public void setReadingValue(int readingValue)
    {
        this.readingValue = readingValue;
    }

    public Date getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp)
    {
        this.timeStamp = timeStamp;
    }
}
