package com.example.myweather.Api;
import java.io.Serializable;

public final class Comment  implements Serializable
{
    public final long id;
    public final String name;
    public int temp;
    public Comment(long id, String name, int temp)
    {
        this.id = id;
        this.name = name;
        this.temp = temp;
    }
}