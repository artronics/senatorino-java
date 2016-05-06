package com.artronics.senatorino.core.register;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

public class Register
{
    private String name;
    private int address;
    List<RegisterBit> bits;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAddress()
    {
        return address;
    }

    @JsonSetter(value = "address")
    public void setStrAddress(String address)
    {
        this.address = Integer.decode(address);
    }

    public void setAddress(int address)
    {
        this.address = address;
    }

    public List<RegisterBit> getBits()
    {
        return bits;
    }

    public void setBits(List<RegisterBit> bits)
    {
        this.bits = bits;
    }

    @Override
    public String toString()
    {
        return name + "<" + Integer.toHexString(address) + ">";
    }
}