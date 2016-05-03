package com.artronics.senatorino.mrf24j40.spi;

import com.artronics.senatorino.mrf24j40.registers.Registers;
import com.pi4j.io.spi.SpiDevice;

import java.io.IOException;

public class MRF24J40
{
    private SpiDevice spi;

    public MRF24J40(SpiDevice spi)
    {
        this.spi = spi;
    }

    public void write(Registers address, byte data) throws IOException
    {
        byte d[] = new byte[2];
//        byte add = address.ControlReg.writeAddress();
//        d[0] = add;
        d[1] = data;
        spi.write(d);
    }

    public int read(Registers address) throws IOException
    {
        byte d[] = new byte[2];
//        byte add = address.readAddress();
//        d[0] = add;
        d[1] = 0x00;

        byte[] write = spi.write(d);
        return write[1];
    }

}
