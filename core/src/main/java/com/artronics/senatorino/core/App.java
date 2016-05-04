package com.artronics.senatorino.core;

import com.artronics.senatorino.mrf24j40.transceiver.Mrf24j40Transceiver;

import java.io.IOException;

public class App
{
    public static void main(String[] args) throws IOException
    {
        Senatorino sen = new Senatorino(new Mrf24j40Transceiver());

    }
}
