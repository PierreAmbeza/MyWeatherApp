package com.example.myweather.Api;
import java.io.Serializable;

public final class Post  implements Serializable
{
    public final long id;
    public final String name;
    public int temp;
    public Post(long id, String name, int temp)
    {
        this.id = id;
        this.name = name;
        this.temp = temp;
    }
}