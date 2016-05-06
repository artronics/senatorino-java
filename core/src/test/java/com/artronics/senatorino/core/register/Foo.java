package com.artronics.senatorino.core.register;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Foo
{
    int hex;
    String sHex;

    public String getsHex()
    {
        return sHex;
    }

    public void setsHex(String sHex)
    {
        this.sHex = sHex;
    }

    public int getHex()
    {
        return hex;
    }

    @JsonSetter(value = "hex")
    public void setHex(String hex)
    {
        this.hex = 234;
    }

    public void setHex(int hex)
    {
        this.hex = hex;
    }
}
